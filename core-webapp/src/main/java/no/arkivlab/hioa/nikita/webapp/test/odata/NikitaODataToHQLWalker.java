package no.arkivlab.hioa.nikita.webapp.test.odata;

import org.hibernate.query.Query;

import static nikita.config.Constants.DM_OWNED_BY;

/**
 * Extending NikitaODataWalker to handle events so we can convert OData filter
 * command to SQL.
 */

public class NikitaODataToHQLWalker
        extends NikitaODataWalker
        implements IODataWalker {

    private Query query;
    private String hqlStatement;

    public void processTop() {
        query.setFirstResult(10);
    }

    public void processSkip() {
        query.setMaxResults(10);
    }

    /**
     * processResource
     * <p>
     * When dealing with the following example URL:
     * <p>
     * [contextPath][api]/arkivstruktur/arkiv?$filter=startsWith(tittel,'hello')
     * <p>
     * The 'arkiv' entity is identified as a resource and picked out and is
     * identified as the 'from' part. We always add to the where clause to
     * filter out rows that actually belong to the user first and then can add
     * extra filtering as the walker progresses.
     * <p>
     * Note this will cause some problems when dealing with ownership of objects
     * via groups. Probably have to some lookup or something. But we are
     * currently just dealing with getting OData2HQL to work.
     *
     * @param entity       The entity/table you wish to search
     * @param loggedInUser The name of the user whose tuples you want to
     *                     retrieve
     */

    @Override
    public void processResource(String entity, String loggedInUser) {
        hqlStatement = "HQL Statement :";
        hqlStatement += "select from " + getNameObject(entity) + " where ";
        hqlStatement += DM_OWNED_BY + " equals " + loggedInUser + " and ";
    }

    public String getHqlStatment() {
        return hqlStatement;
    }
}
