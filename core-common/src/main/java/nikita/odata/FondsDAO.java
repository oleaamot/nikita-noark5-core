package nikita.odata;

import nikita.model.noark5.v4.Fonds;
import nikita.odata.interfaces.IFondsDAO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class FondsDAO implements IFondsDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Fonds> searchFonds(List<SearchCriteria> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Fonds> query = builder.createQuery(Fonds.class);
        Root r = query.from(Fonds.class);

        Predicate predicate = (Predicate) builder.conjunction();

        for (SearchCriteria param : params) {
            if (param.getOperation().equalsIgnoreCase(">")) {
                predicate = builder.and(predicate,
                        builder.greaterThanOrEqualTo(r.get(param.getKey()),
                                param.getValue().toString()));
            } else if (param.getOperation().equalsIgnoreCase("<")) {
                predicate = builder.and(predicate,
                        builder.lessThanOrEqualTo(r.get(param.getKey()),
                                param.getValue().toString()));
            } else if (param.getOperation().equalsIgnoreCase(":")) {
                if (r.get(param.getKey()).getJavaType() == String.class) {
                    predicate = builder.and(predicate,
                            builder.like(r.get(param.getKey()),
                                    "%" + param.getValue() + "%"));
                } else {
                    predicate = builder.and(predicate,
                            builder.equal(r.get(param.getKey()), param.getValue()));
                }
            }
        }
        query.where(predicate);

        List<Fonds> result = entityManager.createQuery(query).getResultList();
        return result;
    }

    @Override
    public void save(Fonds fonds) {
        entityManager.persist(fonds);
    }
}
