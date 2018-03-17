package nikita.webapp.service.impl;

import nikita.common.model.noark5.v4.Class;
import nikita.common.model.noark5.v4.ClassificationSystem;
import nikita.common.repository.n5v4.IClassificationSystemRepository;
import nikita.common.util.exceptions.NoarkEntityNotFoundException;
import nikita.webapp.service.interfaces.IClassService;
import nikita.webapp.service.interfaces.IClassificationSystemService;
import nikita.webapp.util.NoarkUtils;
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
import java.util.List;

import static nikita.common.config.Constants.INFO_CANNOT_FIND_OBJECT;

@Service
@Transactional
public class ClassificationSystemService implements IClassificationSystemService {

    private static final Logger logger = LoggerFactory.getLogger(ClassificationSystemService.class);

    private IClassService classService;
    private IClassificationSystemRepository classificationSystemRepository;
    private EntityManager entityManager;

    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    private Integer maxPageSize = new Integer(10);

    public ClassificationSystemService(IClassService classService,
                                       IClassificationSystemRepository classificationSystemRepository,
                                       EntityManager entityManager) {
        this.classService = classService;
        this.classificationSystemRepository = classificationSystemRepository;
        this.entityManager = entityManager;
    }

    // All CREATE operations
    @Override
    public ClassificationSystem createNewClassificationSystem(ClassificationSystem classificationSystem){
        NoarkUtils.NoarkEntity.Create.setNoarkEntityValues(classificationSystem);
        NoarkUtils.NoarkEntity.Create.setFinaliseEntityValues(classificationSystem);
        return classificationSystemRepository.save(classificationSystem);
    }

    @Override
    public Class createClassAssociatedWithClassificationSystem(String classificationSystemSystemId, Class klass) {
        Class persistedClass = null;
        ClassificationSystem classificationSystem = classificationSystemRepository.findBySystemId(classificationSystemSystemId);
        if (classificationSystem == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " ClassificationSystem, using classificationSystemSystemId " + classificationSystemSystemId;
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else {
            klass.setReferenceClassificationSystem(classificationSystem);
            persistedClass = classService.save(klass);
        }
        return persistedClass;
    }

    // All READ operations
    public List<ClassificationSystem> findAll() {
        return classificationSystemRepository.findAll();
    }

    public List<ClassificationSystem> findAll(Sort sort) {
        return classificationSystemRepository.findAll(sort);
    }

    public Page<ClassificationSystem> findAll(Pageable pageable) {
        return classificationSystemRepository.findAll(pageable);
    }

    // id
    public ClassificationSystem findById(Long id) {
        return classificationSystemRepository.findById(id);
    }

    // systemId
    public ClassificationSystem findBySystemId(String systemId) {
        return getClassificationSystemOrThrow(systemId);
    }

    // ownedBy
    public List<ClassificationSystem> findByOwnedBy(String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return classificationSystemRepository.findByOwnedBy(ownedBy);
    }

    public List<ClassificationSystem> findByOwnedBy(String ownedBy, Sort sort) {return classificationSystemRepository.findByOwnedBy(ownedBy, sort);}

    public Page<ClassificationSystem> findByOwnedBy(String ownedBy, Pageable pageable) {return classificationSystemRepository.findByOwnedBy(ownedBy, pageable);}

    // All UPDATE operations
    public ClassificationSystem update(ClassificationSystem classificationSystem){

        return classificationSystemRepository.save(classificationSystem);
    }

    // All READ operations
    @Override
    public List<ClassificationSystem> findClassificationSystemByOwnerPaginated(Integer top, Integer skip) {
        if (top == null || top > maxPageSize) {
            top = maxPageSize;
        }
        if (skip == null) {
            skip = 0;
        }

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClassificationSystem> criteriaQuery = criteriaBuilder.createQuery(ClassificationSystem.class);
        Root<ClassificationSystem> from = criteriaQuery.from(ClassificationSystem.class);
        CriteriaQuery<ClassificationSystem> select = criteriaQuery.select(from);

        criteriaQuery.where(criteriaBuilder.equal(from.get("ownedBy"), loggedInUser));
        TypedQuery<ClassificationSystem> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(skip);
        typedQuery.setMaxResults(top);
        return typedQuery.getResultList();
    }

    // All UPDATE operations
    @Override
    public ClassificationSystem handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull ClassificationSystem incomingClassificationSystem) {
        ClassificationSystem existingClassificationSystem = getClassificationSystemOrThrow(systemId);
        // Copy all the values you are allowed to copy ....
        if (null != incomingClassificationSystem.getDescription()) {
            existingClassificationSystem.setDescription(incomingClassificationSystem.getDescription());
        }
        if (null != incomingClassificationSystem.getTitle()) {
            existingClassificationSystem.setTitle(incomingClassificationSystem.getTitle());
        }        existingClassificationSystem.setVersion(version);
        classificationSystemRepository.save(existingClassificationSystem);
        return existingClassificationSystem;
    }

    // All DELETE operations
    @Override
    public void deleteEntity(@NotNull String classificationSystemSystemId) {
        ClassificationSystem classificationSystem = getClassificationSystemOrThrow(classificationSystemSystemId);
        classificationSystemRepository.delete(classificationSystem);
    }

    // All HELPER operations
    /**
     * Internal helper method. Rather than having a find and try catch in multiple methods, we have it here once.
     * If you call this, be aware that you will only ever get a valid ClassificationSystem back. If there is no valid
     * ClassificationSystem, an exception is thrown
     *
     * @param classificationSystemSystemId
     * @return
     */
    protected ClassificationSystem getClassificationSystemOrThrow(@NotNull String classificationSystemSystemId) {
        ClassificationSystem classificationSystem = classificationSystemRepository.findBySystemId(classificationSystemSystemId);
        if (classificationSystem == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " ClassificationSystem, using systemId " + classificationSystemSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return classificationSystem;
    }
}
