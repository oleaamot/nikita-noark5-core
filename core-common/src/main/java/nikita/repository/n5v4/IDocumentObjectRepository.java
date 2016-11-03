package nikita.repository.n5v4;

import nikita.model.noark5.v4.DocumentObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IDocumentObjectRepository extends PagingAndSortingRepository<DocumentObject, Long> {


    // -- All SAVE operations
    @Override
    DocumentObject save(DocumentObject documentObject);

    // -- All READ operations
    @Override
    List<DocumentObject> findAll();

    @Override
    List<DocumentObject> findAll(Sort sort);

    @Override
    Page<DocumentObject> findAll(Pageable pageable);

    // id
    DocumentObject findById(Long id);


    // systemId
    DocumentObject findBySystemId(String systemId);

    // createdDate
    List<DocumentObject> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy);
    List<DocumentObject> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort);
    List<DocumentObject> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy);

    Page<DocumentObject> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable);
    Page<DocumentObject> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable);

    // createdBy
    List<DocumentObject> findByCreatedByAndOwnedBy(String createdBy, String ownedBy);
    List<DocumentObject> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy);
    List<DocumentObject> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy);
    List<DocumentObject> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort);
    List<DocumentObject> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);
    List<DocumentObject> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);

    Page<DocumentObject> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
    Page<DocumentObject> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
    Page<DocumentObject> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);

    // deleted
    List<DocumentObject> findByDeletedAndOwnedBy(String deleted, String ownedBy);
    List<DocumentObject> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort);
    Page<DocumentObject> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable);

    // ownedBy
    //List<DocumentObject> findByOwnedBy();
    List<DocumentObject> findByOwnedBy(String ownedBy);
    List<DocumentObject> findByOwnedBy(String ownedBy, Sort sort);
    Page<DocumentObject> findByOwnedBy(String ownedBy, Pageable pageable);

}
