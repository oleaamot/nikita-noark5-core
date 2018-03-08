package no.arkivlab.hioa.nikita.webapp.test.odata;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

/**
 * HQLStatementBuilder
 * <p>
 * Handle the process of building an HQL statement. The potential statement
 * is derived for a number of parts. The select part is straight forward. The
 * where part is an ArrayList of various clauses that are currently joined
 * together with an 'and'. We need a better way to handle this. But this is
 * experimental, prototyping the solution as we go along.
 * <p>
 * Note. When implementing paging, make sure there is a sort order. Remember
 * the fetch order is unpredictable.
 */

public class HQLStatementBuilder {

    private String select;
    private ArrayList<String> whereList;
    private Map<String, String> orderByMap;
    private Integer limitHowMany;
    private Integer limitOffset;

    public HQLStatementBuilder() {
        select = "";
        whereList = new ArrayList<>();
        orderByMap = new HashMap<>();
    }

    public void addSelect(String entity, String ownerColumn, String
            loggedInUser) {
        select = "FROM " + entity + " where " + ownerColumn + " ='" +
                loggedInUser + "'";
    }

    public void addWhere(String where) {
        whereList.add(where);
    }

    public void addOrderby(String attribute, String sortOrder) {
        orderByMap.put(attribute, sortOrder);
    }

    public void addLimitby_skip(Integer skip) {
        limitOffset = skip;
    }

    public void addLimitby_top(Integer top) {
        limitHowMany = top;
    }

    public Query buildHQLStatement(Session session) {


        // take care of the select part
        String hqlStatement = select;

        boolean firstWhere = false;
        // take care of the where part
        // Coding with 'and'. Will figure out how to handle this properly later
        for (String where : whereList) {
            if (!firstWhere) {
                firstWhere = true;
                hqlStatement += " and ";
            }
            hqlStatement += where;
        }

        hqlStatement += " ";

        Query query = session.createQuery(hqlStatement);

        // take care of the orderBy part
        boolean firstOrderBy = true;
        for (Map.Entry entry : orderByMap.entrySet()) {
            if (!firstOrderBy) {
                firstOrderBy = false;
                hqlStatement += ", ";
            } else {
                hqlStatement += " order by ";
            }
            hqlStatement += entry.getKey() + " " + entry.getValue();
        }

        query.setFirstResult(limitOffset);
        query.setMaxResults(limitHowMany);

        String queryString = query.getQueryString();
        out.println("HQL Query string is " + queryString);
        return query;
    }
}
