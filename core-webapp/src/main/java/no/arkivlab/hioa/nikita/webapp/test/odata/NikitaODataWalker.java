package no.arkivlab.hioa.nikita.webapp.test.odata;

import nikita.util.CommonUtils;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Extending ODataBaseListener to capture the required events for processing
 * OData syntax so that it can be converted to SQL or HQL.
 * <p>
 * The code here might be a little ugly in terms of interfaces, perhaps an
 * abstract class should be used, throws on method calls that should only be
 * called by sub-classes etc. But this code is a compromise of sorts as I learn
 * about parsing and how to walk through parse trees. Right now I don't know
 * what the best practice is, but I have to solve a particular a problem and
 * will discover a best practice as I work on that problem.
 * <p>
 * This class captures the enter/exit*Command and calls a
 * 'process enter/exit*Command' that is implemented in a sub class. The reason
 * the sub-class itself does not just implement the enter/exit*Command is that
 * there may be instances where we have to do some checks on siblings or
 * parents and then it's handy to have the ability to do that common for all
 * before calling all sub-classes. If we find that we don't need this, then
 * we will change the code to ignore this extra level of complexity.
 * <p>
 * We have to support OData2HQL, but perhaps someone out there might find an
 * implementation of OData2SQL useful. We will also support OData2
 * ElasticSearch query.
 * <p>
 * Note, as we start this, we do not know how deep the rabbit hole is or how
 * much OData we will support. So DO NOT RELY on copying this and using it in
 * a production environment!
 */

public abstract class NikitaODataWalker
        extends ODataBaseListener
        implements IODataWalker {

    /**
     * This is the part that picks up the start of the OData command of a
     * OData URL. Given the following example:
     * <p>
     * [contextPath][api]/arkivstruktur/arkiv?$filter=contains(tittel, 'hello')
     * <p>
     * the ODataCommand part is $filter=contains(tittel, 'hello')
     * <p>
     * It can also be $top, $skip etc.
     * <p>
     * We don't do any processing on this set of tokens. This method can
     * probably be removed. TODO: Take a look at this!
     *
     * @param ctx ODataParser.OdataCommandContext
     */

    @Override
    public void enterOdataCommand(ODataParser.OdataCommandContext ctx) {
        super.enterOdataCommand(ctx);
        System.out.println("Entering enterOdataCommand. Found [" +
                ctx.getText() + "]");
        // processEnterOdataCommand(ctx);
    }

    /**
     * enterResource
     * <p>
     * Used by sub-classes to identify the entity that is being retrieved in a
     * OData URL. In the following example:
     * <p>
     * [contextPath][api]/arkivstruktur/arkiv?$filter=contains(tittel, 'hello')
     * <p>
     * the resource is arkiv. We are searching the arkiv table.
     * <p>
     * Note, there is a "hacked-in" hardcoded value of admin user for ownedBy.
     * If the security context is active, then the code should pick up the
     * current logged-in user. Note that this will give us problems when
     * trying to retrieve stuff based on group ownership rather than user
     * ownership, but we are still exploring OData syntax processing so the
     * topic of group ownership has to come later.
     *
     * @param ctx ODataParser.ResourceContext ctx
     */
    @Override
    public void enterResource(ODataParser.ResourceContext ctx) {
        super.enterResource(ctx);
        System.out.println("Entering enterResource. Found [" +
                ctx.getText() + "]");
        // This is temporary while developing / testing outside of a spring
        // context
        String loggedInUser = "admin";
        if (SecurityContextHolder.getContext() != null &&
                SecurityContextHolder.getContext().
                        getAuthentication() != null) {
            loggedInUser = SecurityContextHolder.getContext().
                    getAuthentication().getName();
        }

        String entity = ctx.getText();
        processResource(entity, loggedInUser);
    }

    /**
     * enterContains
     * <p>
     * Used by sub-classes to convert a OData 'contains' to a SQL style LIKE.
     * In the following example:
     * <p>
     * [contextPath][api]/arkivstruktur/arkiv?$filter=contains(tittel, 'hello')
     * <p>
     * The contains(tittel, 'hello') is parsed and the attribute (in this case
     * 'tittel') and value (in this case) 'hello' are identified and passed
     * to the calling sub-class that will decide how it should be handled
     * according to their syntax.
     *
     * @param ctx ODataParser.ContainsContext
     */
    @Override
    public void enterContains(ODataParser.ContainsContext ctx) {
        super.enterContains(ctx);
        System.out.println("Entering enterContains. Found [" +
                ctx.getText() + "]");
        String attribute = ctx.getChild(
                ODataParser.AttributeContext.class, 0).getText();
        String value = ctx.getChild(
                ODataParser.ValueContext.class, 0).getText();

        processContains(attribute, value);
    }

    /**
     * enterStartsWith
     * <p>
     * Used by sub-classes to convert a OData 'startsWith' to a SQL style LIKE.
     * In the following example:
     * <p>
     * [contextPath][api]/arkivstruktur/arkiv?$filter=startsWith(tittel,'hello')
     * <p>
     * The startsWith(tittel, 'hello') is parsed and the attribute (in this case
     * 'tittel') and value (in this case) 'hello' are identified and passed
     * to the calling sub-class that will decide how it should be handled
     * according to their syntax.
     *
     * @param ctx ODataParser.StartsWithContext
     */
    @Override
    public void enterStartsWith(ODataParser.StartsWithContext ctx) {
        super.enterStartsWith(ctx);
        System.out.println("Entering enterStartsWith. Found [" +
                ctx.getText() + "]");
        String attribute = ctx.getChild(
                ODataParser.AttributeContext.class, 0).getText();
        String value = ctx.getChild(
                ODataParser.ValueContext.class, 0).getText();

        processStartsWith(attribute, value);
    }

    @Override
    public void enterComparatorCommand(
            ODataParser.ComparatorCommandContext ctx) {
        System.out.println("Entering filter. Found [" +
                ctx.getText() + "]");
        String attribute = ctx.getChild(
                ODataParser.AttributeContext.class, 0).getText();
        String comparator = ctx.getChild(
                ODataParser.ComparatorContext.class, 0).getText();
        String value = ctx.getChild(
                ODataParser.ValueContext.class, 0).getText();

        processComparatorCommand(attribute, comparator, value);
    }

    @Override
    public void enterTop(ODataParser.TopContext ctx) {
        super.enterTop(ctx);
        System.out.println("Entering enterTop. Found [" +
                ctx.getText() + "]");
        String topAsString = ctx.getChild(
                ODataParser.NumberContext.class, 0).getText();

        Integer top = Integer.parseInt(topAsString);
        // TODO: Check it's a number, throw exception otherwise
        processTopCommand(top);
    }

    @Override
    public void enterSkip(ODataParser.SkipContext ctx) {
        super.enterSkip(ctx);

        String skipAsString = ctx.getChild(
                ODataParser.NumberContext.class, 0).getText();

        Integer skip = Integer.parseInt(skipAsString);
        // TODO: Check it's a number, throw exception otherwise
        processSkipCommand(skip);
    }

    @Override
    public void enterOrderby(ODataParser.OrderbyContext ctx) {
        super.enterOrderby(ctx);

        String attribute = ctx.getChild(
                ODataParser.AttributeContext.class, 0).getText();
        String sortOrder = ctx.getChild(
                ODataParser.SortOrderContext.class, 0).getText();

        processOrderByCommand(attribute, sortOrder);
    }


    /**
     * getNameDatabase
     * <p>
     * Helper mechanism to convert Norwegian entity / attribute names to
     * English as English is used in classes and tables. Interacting with the
     * core is done in Norwegian but things have then to be translated to
     * English naming conventions.
     * <p>
     * Note, this will return the name of the database column
     * <p>
     * If we don't have a corresponding object, we choose just to return the
     * original object. This should force a database query error and expose
     * any missing objects. This strategy is OK in development, but later we
     * need a better way of handling it.
     *
     * @param norwegianName The name in Norwegian
     * @return the English version of the Norwegian name if it exists, otherwise
     * the original Norwegian name.
     */
    protected String getNameDatabase(String norwegianName) {
        String englishName = CommonUtils.WebUtils
                .getEnglishNameDatabase(norwegianName);

        if (englishName == null)
            return norwegianName;
        else
            return englishName;
    }

    /**
     * getNameObject
     * <p>
     * Helper mechanism to convert Norwegian entity / attribute names to
     * English as English is used in classes and tables. Interacting with the
     * core is done in Norwegian but things have then to be translated to
     * English naming conventions.
     * <p>
     * Note, this will return the name of the class variable
     * <p>
     * If we don't have a corresponding object, we choose just to return the
     * original object. This should force a database query error and expose
     * any missing objects. This strategy is OK in development, but later we
     * need a better way of handling it.
     *
     * @param norwegianName The name in Norwegian
     * @return the English version of the Norwegian name if it exists, otherwise
     * the original Norwegian name.
     */
    protected String getNameObject(String norwegianName) {
        String englishName = CommonUtils.WebUtils
                .getEnglishNameObject(norwegianName);

        if (englishName == null)
            return norwegianName;
        else
            return englishName;
    }
}
