package nikita.webapp.service.impl;

import nikita.common.model.noark5.v4.Class;
import nikita.common.repository.n5v4.IClassRepository;
import nikita.common.util.exceptions.NoarkEntityEditWhenClosedException;
import nikita.common.util.exceptions.NoarkEntityNotFoundException;
import nikita.webapp.service.interfaces.IClassService;
import nikita.webapp.util.NoarkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static nikita.common.config.Constants.INFO_CANNOT_ASSOCIATE_WITH_CLOSED_OBJECT;
import static nikita.common.config.Constants.INFO_CANNOT_FIND_OBJECT;

@Service
@Transactional
public class ClassService implements IClassService {

    private static final Logger logger = LoggerFactory.getLogger(ClassService.class);
    private IClassRepository classRepository;
    private EntityManager entityManager;
    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    private Integer maxPageSize = 10;

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
        Class parentKlass = classRepository.findBySystemId(classSystemId);
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

    // id
    public Optional<Class> findById(Long id) {
        return classRepository.findById(id);
    }

    // systemId
    public Class findBySystemId(String systemId) {
        return getClassOrThrow(systemId);
    }
    // ownedBy
    public List<Class> findByOwnedBy(String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classRepository.findByOwnedBy(ownedBy);
    }

    // All UPDATE operations
    public Class update(Class klass){
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
        typedQuery.setMaxResults(top);
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
     * @param classSystemId systemId of the class object you are looking for
     * @return the newly found class object or null if it does not exist
     */
    protected Class getClassOrThrow(@NotNull String classSystemId) {
        Class klass = classRepository.findBySystemId(classSystemId);
        if (null == klass) {
            String info = INFO_CANNOT_FIND_OBJECT + " Class, using systemId " + classSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return klass;
    }
}
