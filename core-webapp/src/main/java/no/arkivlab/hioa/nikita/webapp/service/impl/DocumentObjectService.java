package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.DocumentObject;
import nikita.repository.n5v4.IDocumentObjectRepository;
import no.arkivlab.hioa.nikita.webapp.config.WebappProperties;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IDocumentObjectService;
import no.arkivlab.hioa.nikita.webapp.util.NoarkUtils;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.StorageException;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.StorageFileNotFoundException;
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

@Service
@Transactional
@EnableConfigurationProperties(WebappProperties.class)
public class DocumentObjectService implements IDocumentObjectService {

    private final Path rootLocation;
    private final String checksumAlgorithm;
    private final Logger logger = LoggerFactory.getLogger(DocumentObjectService.class);
    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    Integer maxPageSize = new Integer(10);
    private IDocumentObjectRepository documentObjectRepository;
    private EntityManager entityManager;


    @Autowired
    public DocumentObjectService(IDocumentObjectRepository documentObjectRepository,
                                 EntityManager entityManager,
                                 WebappProperties webappProperties) {
        this.documentObjectRepository = documentObjectRepository;
        this.entityManager = entityManager;
        this.checksumAlgorithm = webappProperties.getChecksumProperties().getChecksumAlgorithm();
        this.rootLocation = Paths.get(webappProperties.getStorageProperties().getLocation());
    }

    // All CREATE operations

    public DocumentObject save(DocumentObject documentObject){
        NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(documentObject);
        NoarkUtils.NoarkEntity.Create.setCreateEntityValues(documentObject);
        NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(documentObject);
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
        try {

            MessageDigest md = MessageDigest.getInstance(checksumAlgorithm);
            String filename = UUID.randomUUID().toString();

            Path directory = Paths.get(rootLocation + File.separator);
            Path file = Paths.get(rootLocation + File.separator + filename);

            // Check if the document storage directory exists, if not try to create it
            // This should have been done with init!!
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

            // Create a DigestInputStream to be read with the checksumAlgorithm identified in the properties file
            DigestInputStream digestInputStream = new DigestInputStream(inputStream, md);
            FileOutputStream outputStream = new FileOutputStream(path.toFile());

            // TODO: Should 8096 be configurable via properties or hardcoded
            // We expect large-ish files ...
            byte[] byteArray = new byte[8096];
            int bytesCount = 0;
            Long bytesTotal = new Long(0);

            // Copy from input stream to output stream
            while ((bytesCount = digestInputStream.read(byteArray, 0, byteArray.length)) != -1) {
                outputStream.write(byteArray, 0, bytesCount);
                bytesTotal += bytesCount;
            }

            documentObject.setReferenceDocumentFile(file.toString());

            // Tidy up and close outputStream
            outputStream.flush();
            outputStream.close();

            if (bytesTotal == 0L) {
                Files.delete(file);
                logger.warn("The file (" + file.getFileName() + ") has 0 length content and should have been deleted");
                throw new StorageException("The file (" + file.getFileName() + ") has 0 length content. Rejecting " +
                        "upload! This file is being associated with " + documentObject);
            }

            // Get the digest
            byte[] digest = digestInputStream.getMessageDigest().digest();

            // Finished with inputStream now as well
            digestInputStream.close();

            // Convert digest to HEX
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            documentObject.setChecksum(sb.toString());
            documentObject.setChecksumAlgorithm(checksumAlgorithm);

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
    public DocumentObject findBySystemId(String systemId) {
        return documentObjectRepository.findBySystemId(systemId);
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
}
