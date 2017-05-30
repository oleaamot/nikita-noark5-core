package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.Class;
import nikita.repository.n5v4.IClassRepository;
import nikita.util.exceptions.NoarkEntityEditWhenClosedException;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IClassService;
import no.arkivlab.hioa.nikita.webapp.util.NoarkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Date;
import java.util.List;

import static nikita.config.Constants.INFO_CANNOT_ASSOCIATE_WITH_CLOSED_OBJECT;
import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;

@Service
@Transactional
public class ClassService implements IClassService {

    private static final Logger logger = LoggerFactory.getLogger(ClassService.class);
    private IClassRepository classRepository;
    private EntityManager entityManager;
    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    private Integer maxPageSize = new Integer(10);

    public ClassService(IClassRepository classRepository, EntityManager entityManager) {
        this.classRepository = classRepository;
        this.entityManager = entityManager;
    }

    // All CREATE operations
    public Class save(Class klass){
        NoarkUtils.NoarkEntity.Create.setNoarkEntityValues(klass);
        NoarkUtils.NoarkEntity.Create.setFinaliseEntityValues(klass);
        return classRepository.save(klass);
    }

    public  Class createClassAssociatedWithClass(String classSystemId, Class klass) {
        Class persistedClass = null;
        Class parentKlass = classRepository.findBySystemIdOrderBySystemId(classSystemId);
        if (parentKlass == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " Class, using classSystemId " + classSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        } else if (parentKlass.getFinalisedDate() != null) {
            String info = INFO_CANNOT_ASSOCIATE_WITH_CLOSED_OBJECT + ". Class with classSystemId " + classSystemId +
                    "has been finalised. Cannot associate a new class object with a finalised class object";
            logger.info(info);
            throw new NoarkEntityEditWhenClosedException(info);
        } else {
            klass.setReferenceParentClass(parentKlass);
            persistedClass = this.save(klass);
        }
        return persistedClass;
    }

    // All READ operations
    public List<Class> findAll() {
        return classRepository.findAll();
    }

    public List<Class> findAll(Sort sort) {
        return classRepository.findAll(sort);
    }

    public Page<Class> findAll(Pageable pageable) {
        return classRepository.findAll(pageable);
    }

    // id
    public Class findById(Long id) {
        return classRepository.findById(id);
    }

    // systemId
    public Class findBySystemIdOrderBySystemId(String systemId) {
        return getClassOrThrow(systemId);
    }

    // title
    public List<Class> findByTitleAndOwnedBy(String title, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByTitleAndOwnedBy(title,  ownedBy);
    }

    public List<Class> findByTitleContainingAndOwnedBy(String title, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByTitleContainingAndOwnedBy(title, ownedBy);
    }

    public List<Class> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByTitleIgnoreCaseContainingAndOwnedBy(title, ownedBy);
    }

    public List<Class> findByTitleAndOwnedBy(String title, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByTitleAndOwnedBy(title, ownedBy, sort);
    }

    public List<Class> findByTitleContainingAndOwnedBy(String title, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByTitleContainingAndOwnedBy(title, ownedBy, sort);
    }

    public List<Class> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByTitleIgnoreCaseContainingAndOwnedBy(title, ownedBy, sort);
    }

    public Page<Class> findByTitleAndOwnedBy(String title, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByTitleAndOwnedBy(title, ownedBy, pageable);
    }

    public Page<Class> findByTitleContainingAndOwnedBy(String title, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByTitleContainingAndOwnedBy(title, ownedBy, pageable);
    }

    public Page<Class> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByTitleIgnoreCaseContainingAndOwnedBy(title, ownedBy, pageable);
    }

    // description
    public List<Class> findByDescriptionAndOwnedBy(String description, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByDescriptionAndOwnedBy(description, ownedBy);
    }

    public List<Class> findByDescriptionContainingAndOwnedBy(String description, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByDescriptionContainingAndOwnedBy(description, ownedBy);
    }

    public List<Class> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByDescriptionIgnoreCaseContainingAndOwnedBy(description, ownedBy);
    }

    public List<Class> findByDescriptionAndOwnedBy(String description, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByDescriptionAndOwnedBy(description, ownedBy, sort);
    }

    public List<Class> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByDescriptionContainingAndOwnedBy(description, ownedBy, sort);
    }

    public List<Class> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByDescriptionIgnoreCaseContainingAndOwnedBy(description, ownedBy, sort);
    }

    public Page<Class> findByDescriptionAndOwnedBy(String description, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByDescriptionAndOwnedBy(description, ownedBy, pageable);
    }

    public Page<Class> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByDescriptionContainingAndOwnedBy(description, ownedBy, pageable);
    }

    public Page<Class> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByDescriptionIgnoreCaseContainingAndOwnedBy(description, ownedBy, pageable);
    }

    // createdDate
    public List<Class> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy);
    }

    public List<Class> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy, sort);
    }

    public List<Class> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByCreatedDateBetweenAndOwnedBy(start, end, ownedBy);
    }

    public Page<Class> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy, pageable);
    }

    public Page<Class> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByCreatedDateBetweenAndOwnedBy(start, end, ownedBy, pageable);
    }

    // createdBy
    public List<Class> findByCreatedByAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy);
    }

    public List<Class> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy);
    }

    public List<Class> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy);
    }

    public List<Class> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy, sort);
    }

    public List<Class> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy, sort);
    }

    public List<Class> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy, sort);
    }

    public Page<Class> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy, pageable);
    }

    public Page<Class> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy, pageable);
    }

    public Page<Class> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy, pageable);
    }

    // finalisedDate
    public List<Class> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByFinalisedDateAndOwnedBy(finalisedDate, ownedBy);
    }

    public List<Class> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByFinalisedDateAndOwnedBy(finalisedDate, ownedBy);
    }

    public List<Class> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByFinalisedDateBetweenAndOwnedBy(start, end, ownedBy);
    }

    public Page<Class> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByFinalisedDateAndOwnedBy(finalisedDate, ownedBy, pageable);
    }

    public Page<Class> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByFinalisedDateBetweenAndOwnedBy(start, end, ownedBy, pageable);
    }

    // finalisedBy
    public List<Class> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByFinalisedByAndOwnedBy(finalisedBy, ownedBy);
    }

    public List<Class> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByFinalisedByContainingAndOwnedBy(finalisedBy, ownedBy);
    }

    public List<Class> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByFinalisedByIgnoreCaseContainingAndOwnedBy(finalisedBy, ownedBy);
    }

    public List<Class> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByFinalisedByAndOwnedBy(finalisedBy, ownedBy, sort);
    }

    public List<Class> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByFinalisedByContainingAndOwnedBy(finalisedBy, ownedBy, sort);
    }

    public List<Class> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByFinalisedByIgnoreCaseContainingAndOwnedBy(finalisedBy, ownedBy, sort);
    }

    public Page<Class> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByFinalisedByAndOwnedBy(finalisedBy, ownedBy, pageable);
    }

    public Page<Class> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByFinalisedByContainingAndOwnedBy(finalisedBy, ownedBy, pageable);
    }

    public Page<Class> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByFinalisedByIgnoreCaseContainingAndOwnedBy(finalisedBy, ownedBy, pageable);
    }

    // deleted
    public List<Class> findByDeletedAndOwnedBy(String deleted, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByDeletedAndOwnedBy(deleted, ownedBy);
    }

    public List<Class> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByDeletedAndOwnedBy(deleted, ownedBy, sort);
    }

    public Page<Class> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByDeletedAndOwnedBy(deleted, ownedBy, pageable);
    }

    // ownedBy
    public List<Class> findByOwnedBy(String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByOwnedBy(ownedBy);
    }

    public List<Class> findByOwnedBy(String ownedBy, Sort sort) {return classRepository.findByOwnedBy(ownedBy, sort);}

    public Page<Class> findByOwnedBy(String ownedBy, Pageable pageable) {return classRepository.findByOwnedBy(ownedBy, pageable);}

    // All UPDATE operations
    public Class update(Class klass){

        return classRepository.save(klass);
    }

    public Class updateClassSetFinalized(Long id){
        Class klass = classRepository.findById(id);

        if (klass == null) {
            // TODO throw Object not find
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        klass.setFinalisedDate(new Date());
        klass.setFinalisedBy(username);

        return classRepository.save(klass);
    }

    public Class updateClassSetTitle(Long id, String newTitle){

        Class klass = classRepository.findById(id);
        return classRepository.save(klass);
    }

    // All READ operations
    @Override
    public List<Class> findClassByOwnerPaginated(Integer top, Integer skip) {
        if (top == null || top > maxPageSize) {
            top = maxPageSize;
        }
        if (skip == null) {
            skip = 0;
        }

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Class> criteriaQuery = criteriaBuilder.createQuery(Class.class);
        Root<Class> from = criteriaQuery.from(Class.class);
        CriteriaQuery<Class> select = criteriaQuery.select(from);

        criteriaQuery.where(criteriaBuilder.equal(from.get("ownedBy"), loggedInUser));
        TypedQuery<Class> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(skip);
        typedQuery.setMaxResults(maxPageSize);
        return typedQuery.getResultList();
    }

    // All UPDATE operations
    @Override
    public Class handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull Class incomingClass) {
        Class existingClass = getClassOrThrow(systemId);
        // Copy all the values you are allowed to copy ....
        if (null != incomingClass.getDescription()) {
            existingClass.setDescription(incomingClass.getDescription());
        }
        if (null != incomingClass.getTitle()) {
            existingClass.setTitle(incomingClass.getTitle());
        }
        classRepository.save(existingClass);
        return existingClass;
    }

    // All DELETE operations
    @Override
    public void deleteEntity(@NotNull String classSystemId) {
        Class klass = getClassOrThrow(classSystemId);
        classRepository.delete(klass);
    }

    // All HELPER operations
    /**
     * Internal helper method. Rather than having a find and try catch in multiple methods, we have it here once.
     * If you call this, be aware that you will only ever get a valid Class back. If there is no valid
     * Class, an exception is thrown
     *
     * @param classSystemId
     * @return
     */
    protected Class getClassOrThrow(@NotNull String classSystemId) {
        Class klass = classRepository.findBySystemIdOrderBySystemId(classSystemId);
        if (null == klass) {
            String info = INFO_CANNOT_FIND_OBJECT + " Class, using systemId " + classSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return klass;
    }
}
