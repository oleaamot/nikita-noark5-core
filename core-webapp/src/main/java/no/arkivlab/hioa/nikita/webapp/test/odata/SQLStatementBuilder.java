package no.arkivlab.hioa.nikita.webapp.test.odata;

import java.util.ArrayList;

/**
 * SQLStatementBuilder
 * <p>
 * Handle the process of building an SQL statement. The potential statement
 * is derived for a number of parts. The select part is straight forward. The
 * where part is an ArrayList of various clauses that are currently joined
 * together with an 'and'. We need a better way to handle this. But this is
 * experimental, prototyping the solution as we go along.
 */

public class SQLStatementBuilder {

    // private ArrayList <String> selectList;
    private String select;
    private ArrayList<String> whereList;
    private ArrayList<String> orderByList;
    private String limit;

    public SQLStatementBuilder() {
        select = "";
        whereList = new ArrayList<>();
        orderByList = new ArrayList<>();
        limit = "";
    }

    public void addSelect(String entity, String ownerColumn, String
            loggedInUser) {
        select = "select * from " + entity + " where " + ownerColumn + " ='" +
                loggedInUser + "' and";
    }

    public void addWhere(String where) {
        whereList.add(where);
    }

    public void addOrderby(String attribute, String sortOrder) {
        orderByList.add(attribute + " " + sortOrder);
    }

    public void addLimitby_skip(Integer skip) {
        limit += skip.toString();
    }

    public void addLimitby_top(Integer top) {
        if (limit.length() > 0) {
            limit += ", " + top.toString();
        }
    }

    public String buildSQLStatement() {

        // take care of the select part
        String sqlStatement = select;

        boolean firstWhere = false;
        // take care of the where part
        // Coding with 'and'. Will figure out how to handle this properly later
        for (String where : whereList) {
            if (!firstWhere) {
                firstWhere = true;
                sqlStatement += " and ";
            }
            sqlStatement += where;
        }

        sqlStatement += " ";

        // take care of the orderBy part
        boolean firstOrderBy = false;
        for (String orderBy : orderByList) {
            if (!firstOrderBy) {
                firstOrderBy = true;
                sqlStatement += ", ";
            }
            sqlStatement += orderBy;
        }

        sqlStatement += " ";

        // take care of the limit part
        sqlStatement += limit;

        return sqlStatement;
    }
}
