package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.DocumentObject;
import nikita.repository.n5v4.IDocumentObjectRepository;
import nikita.util.exceptions.NikitaMalformedInputDataException;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import nikita.util.exceptions.StorageException;
import nikita.util.exceptions.StorageFileNotFoundException;
import no.arkivlab.hioa.nikita.webapp.config.WebappProperties;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IDocumentObjectService;
import no.arkivlab.hioa.nikita.webapp.util.NoarkUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;
import static nikita.config.N5ResourceMappings.DOCUMENT_OBJECT_FILE_NAME;

@Service
@Transactional
@EnableConfigurationProperties(WebappProperties.class)
public class DocumentObjectService implements IDocumentObjectService {

    private final Path rootLocation;
    private final String defaultChecksumAlgorithm;
    private final Logger logger = LoggerFactory.getLogger(DocumentObjectService.class);
    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    private Integer maxPageSize = new Integer(10);
    private IDocumentObjectRepository documentObjectRepository;
    private EntityManager entityManager;


    @Autowired
    public DocumentObjectService(IDocumentObjectRepository documentObjectRepository,
                                 EntityManager entityManager,
                                 WebappProperties webappProperties) {
        this.documentObjectRepository = documentObjectRepository;
        this.entityManager = entityManager;
        this.defaultChecksumAlgorithm = webappProperties.getChecksumProperties().getChecksumAlgorithm();
        this.rootLocation = Paths.get(webappProperties.getStorageProperties().getLocation());
    }

    // All CREATE operations

    public DocumentObject save(DocumentObject documentObject){
        NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(documentObject);
        NoarkUtils.NoarkEntity.Create.setCreateEntityValues(documentObject);
        NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(documentObject);

	// Make sure checksum algoritm is one we understand.  So far
	// we only understand one algorithm, the default.
	String currentChecksumAlgorithm = documentObject.getChecksumAlgorithm();
	if (null != currentChecksumAlgorithm &&
	    !this.defaultChecksumAlgorithm.equals(currentChecksumAlgorithm)) {
	    // TODO figure out proper exception.
	    throw new NikitaMalformedInputDataException("The checksum algorithm " + documentObject.getChecksumAlgorithm() + " is not supported");
	}
        return documentObjectRepository.save(documentObject);
    }

    @Override
    /**
     *
     * Store an incoming file associated with a DocumentObject. When writing the Incoming filestream, calculate
     * the checksum at the same time and update the DocumentObject with referenceToFile, size (bytes), checksum
     * and checksum algorithm
     *
     * inputStream.read calculates the checksum while reading the input file as it is a DigestInputStream
     */
    public void storeAndCalculateChecksum(InputStream inputStream, DocumentObject documentObject) {
	String checksumAlgorithm = documentObject.getChecksumAlgorithm();
	if (null == checksumAlgorithm) {
	    checksumAlgorithm = defaultChecksumAlgorithm;
            documentObject.setChecksumAlgorithm(checksumAlgorithm);
	}
	if (null != documentObject.getReferenceDocumentFile()) {
	    throw new StorageException("There is already a file associated with " + documentObject);
	}
        try {

            MessageDigest md = MessageDigest.getInstance(checksumAlgorithm);
            String filename = UUID.randomUUID().toString();

            Path directory = Paths.get(rootLocation + File.separator);
            Path file = Paths.get(rootLocation + File.separator + filename);

            // Check if the document storage directory exists, if not try to create it
            // This should have been done with init!!
            // TODO perhaps better to raise an error if somehow init failed to create it?
            if (!Files.exists(directory)) {
                Files.createDirectory(directory);
            }

            // Check if we actually can create the file
            Path path = Files.createFile(file);

            // Check if we can write something to the file
            if (!Files.isWritable(file)) {
                throw new StorageException("The file (" + file.getFileName() + ") is not writable server-side. This" +
                        " file is being associated with " + documentObject);
            }

            // Create a DigestInputStream to be read with the
            // checksumAlgorithm identified in the properties file
            DigestInputStream digestInputStream = new DigestInputStream(inputStream, md);
            FileOutputStream outputStream = new FileOutputStream(path.toFile());

            long bytesTotal = -1;
            try { // Try close without exceptions if copy() threw an exception.
                bytesTotal = IOUtils.copyLarge(digestInputStream, outputStream);
                
                // Tidy up and close outputStream
                outputStream.flush();
                outputStream.close();

                // Finished with inputStream now as well
                digestInputStream.close();
            } finally {
                try { // Try close without exceptions if copy() threw an exception.
                    digestInputStream.close();
                } catch(IOException e) {
                    // swallow any error to expose exceptions from IOUtil.copy()
                }
                try { // same for outputStream
                    outputStream.close();
                } catch(IOException e) {
                    // empty
                }
            }

            if (bytesTotal == 0L) {
                Files.delete(file);
                logger.warn("The file (" + file.getFileName() + ") has 0 length content and should have been deleted");
                throw new StorageException("The file (" + file.getFileName() + ") has 0 length content. Rejecting " +
                        "upload! This file is being associated with " + documentObject);
            }

            if (!documentObject.getFileSize().equals(bytesTotal)) {
                Files.delete(file);
                String logmsg = "The uploaded file (" + file.getFileName() + ") length " +
                    bytesTotal + " did not match the dokumentobjekt filstoerrelse " +
                    documentObject.getFileSize() + " and was deleted.";
                    logger.warn(logmsg);
                String msg = logmsg +
		    " Rejecting upload! This file is being associated with " +
                    documentObject;
                throw new StorageException(msg);
            }

            // Get the digest
            byte[] digest = digestInputStream.getMessageDigest().digest();

            // Convert digest to HEX
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            // TODO figure what the spec will say about existing
            // values in documentObject.  For now, only set the values
            // if they are blank, and reject the file if the checksum
            // did not match.
            String olddigest = documentObject.getChecksum();
            String newdigest = sb.toString();
            if (null == olddigest) {
                documentObject.setChecksum(newdigest);
            } else if (!olddigest.equals(newdigest)) {
                Files.delete(file);
                String msg = "The file (" + file.getFileName() + ") checksum " +
                    newdigest +
                    " do not match the already stored checksum.  Rejecting " +
                    "upload! This file is being associated with " +
                    documentObject;
                throw new StorageException(msg);
            }
            documentObject.setReferenceDocumentFile(file.toString());

        } catch (IOException e) {
            logger.error("When associating an uploaded file with " + documentObject + " an exception occurred." +
                    "Exception is " + e);
            throw new StorageException("Failed to store file to be associated with " + documentObject + " " + e.toString());
        } catch (NoSuchAlgorithmException e) {
            logger.error("When associating an uploaded file with " + documentObject + " an exception occurred." +
                    "Exception is " + e);
            throw new StorageException("Internal error, could not load checksum algorithm (" + checksumAlgorithm
                    + ") when attempting to store a file associated with "
                    + documentObject);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(DocumentObject documentObject) {
        String filename = documentObject.getReferenceDocumentFile();
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename);
        }
    }

    @Override
    public void init() {
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectory(rootLocation);
            }
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage");
        }
    }

    // All READ operations
    public List<DocumentObject> findAll() {
        return documentObjectRepository.findAll();
    }

    public List<DocumentObject> findAll(Sort sort) {
        return documentObjectRepository.findAll(sort);
    }

    public Page<DocumentObject> findAll(Pageable pageable) {
        return documentObjectRepository.findAll(pageable);
    }

    // id
    public DocumentObject findById(Long id) {
        return documentObjectRepository.findById(id);
    }

    // systemId
    public DocumentObject findBySystemIdOrderBySystemId(String systemId) {
        return getDocumentObjectOrThrow(systemId);
    }

    // createdDate
    public List<DocumentObject> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy);
    }

    public List<DocumentObject> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy, sort);
    }

    public List<DocumentObject> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedDateBetweenAndOwnedBy(start, end, ownedBy);
    }

    public Page<DocumentObject> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy, pageable);
    }

    public Page<DocumentObject> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedDateBetweenAndOwnedBy(start, end, ownedBy, pageable);
    }

    // createdBy
    public List<DocumentObject> findByCreatedByAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy);
    }

    public List<DocumentObject> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy);
    }

    public List<DocumentObject> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy);
    }

    public List<DocumentObject> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy, sort);
    }

    public List<DocumentObject> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy, sort);
    }

    public List<DocumentObject> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy, sort);
    }

    public Page<DocumentObject> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy, pageable);
    }

    public Page<DocumentObject> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy, pageable);
    }

    public Page<DocumentObject> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy, pageable);
    }

    // deleted
    public List<DocumentObject> findByDeletedAndOwnedBy(String deleted, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByDeletedAndOwnedBy(deleted, ownedBy);
    }

    public List<DocumentObject> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByDeletedAndOwnedBy(deleted, ownedBy, sort);
    }

    public Page<DocumentObject> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByDeletedAndOwnedBy(deleted, ownedBy, pageable);
    }

    // ownedBy
    public List<DocumentObject> findByOwnedBy(String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByOwnedBy(ownedBy);
    }

    public List<DocumentObject> findByOwnedBy(String ownedBy, Sort sort) {return documentObjectRepository.findByOwnedBy(ownedBy, sort);}

    public Page<DocumentObject> findByOwnedBy(String ownedBy, Pageable pageable) {return documentObjectRepository.findByOwnedBy(ownedBy, pageable);}

    // All UPDATE operations
    public DocumentObject update(DocumentObject documentObject){
        return documentObjectRepository.save(documentObject);
    }

    // All READ operations


    @Override
    public List<DocumentObject> findDocumentObjectByAnyColumn(String column, String value) {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentObject> criteriaQuery = criteriaBuilder.createQuery(DocumentObject.class);
        Root<DocumentObject> from = criteriaQuery.from(DocumentObject.class);
        CriteriaQuery<DocumentObject> select = criteriaQuery.select(from);

        if (column.equalsIgnoreCase(DOCUMENT_OBJECT_FILE_NAME)) {
            column = "originalFilename";
        }

        criteriaQuery.where(criteriaBuilder.equal(from.get("ownedBy"), loggedInUser));
        criteriaQuery.where(criteriaBuilder.equal(from.get(column), value));
        TypedQuery<DocumentObject> typedQuery = entityManager.createQuery(select);
        return typedQuery.getResultList();
    }

    @Override
    public List<DocumentObject> findDocumentObjectByOwnerPaginated(Integer top, Integer skip) {

        if (top == null || top > maxPageSize) {
            top = maxPageSize;
        }
        if (skip == null) {
            skip = 0;
        }

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentObject> criteriaQuery = criteriaBuilder.createQuery(DocumentObject.class);
        Root<DocumentObject> from = criteriaQuery.from(DocumentObject.class);
        CriteriaQuery<DocumentObject> select = criteriaQuery.select(from);

        criteriaQuery.where(criteriaBuilder.equal(from.get("ownedBy"), loggedInUser));
        TypedQuery<DocumentObject> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(skip);
        typedQuery.setMaxResults(maxPageSize);
        return typedQuery.getResultList();
    }


    // All UPDATE operations
    @Override
    public DocumentObject handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull DocumentObject incomingDocumentObject) {
        DocumentObject existingDocumentObject = getDocumentObjectOrThrow(systemId);
        // Here copy all the values you are allowed to copy ....
        if (null != incomingDocumentObject.getFormat()) {
            existingDocumentObject.setFormat(incomingDocumentObject.getFormat());
        }
        if (null != incomingDocumentObject.getFormatDetails()) {
            existingDocumentObject.setFormatDetails(incomingDocumentObject.getFormatDetails());
        }
        if (null != incomingDocumentObject.getOriginalFilename()) {
            existingDocumentObject.setOriginalFilename(incomingDocumentObject.getOriginalFilename());
        }
        if (null != incomingDocumentObject.getVariantFormat()) {
            existingDocumentObject.setVariantFormat(incomingDocumentObject.getVariantFormat());
        }
        if (null != incomingDocumentObject.getVersionNumber()) {
            existingDocumentObject.setVersionNumber(incomingDocumentObject.getVersionNumber());
        }
        existingDocumentObject.setVersion(version);
        documentObjectRepository.save(existingDocumentObject);
        return existingDocumentObject;
    }

    // All DELETE operations
    @Override
    public void deleteEntity(@NotNull String documentObjectSystemId) {
        DocumentObject documentObject = getDocumentObjectOrThrow(documentObjectSystemId);
        documentObjectRepository.delete(documentObject);
    }

    // All HELPER operations
    /**
     * Internal helper method. Rather than having a find and try catch in multiple methods, we have it here once.
     * If you call this, be aware that you will only ever get a valid DocumentObject back. If there is no valid
     * DocumentObject, an exception is thrown
     *
     * @param documentObjectSystemId
     * @return
     */
    protected DocumentObject getDocumentObjectOrThrow(@NotNull String documentObjectSystemId) {
        DocumentObject documentObject = documentObjectRepository.findBySystemIdOrderBySystemId(documentObjectSystemId);
        if (documentObject == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " DocumentObject, using systemId " + documentObjectSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return documentObject;
    }
}
