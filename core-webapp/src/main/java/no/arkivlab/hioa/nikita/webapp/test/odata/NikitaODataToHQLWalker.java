package no.arkivlab.hioa.nikita.webapp.test.odata;


import org.hibernate.Session;
import org.hibernate.query.Query;

import static nikita.config.Constants.DM_OWNED_BY;

/**
 * Extending NikitaODataWalker to handle events so we can convert OData filter
 * command to SQL.
 */

public class NikitaODataToHQLWalker
        extends NikitaODataWalker
        implements IODataWalker {

    HQLStatementBuilder hqlStatement;

    public NikitaODataToHQLWalker() {
        this.hqlStatement = new HQLStatementBuilder();
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
        hqlStatement.addSelect(getNameObject(entity),
                DM_OWNED_BY, loggedInUser);
    }

    /**
     * processComparatorCommand
     * <p>
     * Convert a general Odata attribute comparator value command to HQL. In
     * the following example:
     * <p>
     * [contextPath][api]/arkivstruktur/arkiv?$filter=tittel eq 'hello'
     * <p>
     * tittel eq 'hello' becomes the following HQL:
     * <p>
     * <p>
     * This is achieved by looking at the children and picking out the
     * attribute (in this case 'tittel'), the comparator (in this case eq) and
     * value (in this case 'hello').
     *
     * @param attribute  The attribute/column you wish to filter on
     * @param comparator The type of comparison you want to undertake e.g eq,
     *                   lt etc
     * @param value      The value you wish to filter on
     */
    @Override
    public void processComparatorCommand(String attribute, String comparator,
                                         String value) {

    }

    @Override
    public void processContains(String attribute, String value) {
        hqlStatement.addWhere(getNameDatabase(attribute) +
                " LIKE '%" + value + "%'");

    }

    @Override
    public void processStartsWith(String attribute, String value) {

    }

    @Override
    public void processSkipCommand(Integer skip) {
        hqlStatement.addLimitby_skip(skip);
    }

    @Override
    public void processTopCommand(Integer top) {
        hqlStatement.addLimitby_top(top);
    }

    @Override
    public void processOrderByCommand(String attribute, String sortOrder) {

    }

    public Query getHqlStatment(Session session) {
        return hqlStatement.buildHQLStatement(session);
    }
}
