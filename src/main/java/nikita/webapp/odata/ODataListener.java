// Generated from /home/tsodring/git/nikita-noark5-core/core-webapp/src/main/resources/odata/OData.g4 by ANTLR 4.7
package nikita.webapp.odata;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ODataParser}.
 */
public interface ODataListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link ODataParser#odataURL}.
     *
     * @param ctx the parse tree
     */
    void enterOdataURL(ODataParser.OdataURLContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#odataURL}.
     *
     * @param ctx the parse tree
     */
    void exitOdataURL(ODataParser.OdataURLContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#scheme}.
     *
     * @param ctx the parse tree
     */
    void enterScheme(ODataParser.SchemeContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#scheme}.
     *
     * @param ctx the parse tree
     */
    void exitScheme(ODataParser.SchemeContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#host}.
     *
     * @param ctx the parse tree
     */
    void enterHost(ODataParser.HostContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#host}.
     *
     * @param ctx the parse tree
     */
    void exitHost(ODataParser.HostContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#slash}.
     *
     * @param ctx the parse tree
     */
    void enterSlash(ODataParser.SlashContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#slash}.
     *
     * @param ctx the parse tree
     */
    void exitSlash(ODataParser.SlashContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#contextPath}.
     *
     * @param ctx the parse tree
     */
    void enterContextPath(ODataParser.ContextPathContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#contextPath}.
     *
     * @param ctx the parse tree
     */
    void exitContextPath(ODataParser.ContextPathContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#api}.
     *
     * @param ctx the parse tree
     */
    void enterApi(ODataParser.ApiContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#api}.
     *
     * @param ctx the parse tree
     */
    void exitApi(ODataParser.ApiContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#functionality}.
     *
     * @param ctx the parse tree
     */
    void enterFunctionality(ODataParser.FunctionalityContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#functionality}.
     *
     * @param ctx the parse tree
     */
    void exitFunctionality(ODataParser.FunctionalityContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#resource}.
     *
     * @param ctx the parse tree
     */
    void enterResource(ODataParser.ResourceContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#resource}.
     *
     * @param ctx the parse tree
     */
    void exitResource(ODataParser.ResourceContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#port}.
     *
     * @param ctx the parse tree
     */
    void enterPort(ODataParser.PortContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#port}.
     *
     * @param ctx the parse tree
     */
    void exitPort(ODataParser.PortContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#fromContextPath}.
     *
     * @param ctx the parse tree
     */
    void enterFromContextPath(ODataParser.FromContextPathContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#fromContextPath}.
     *
     * @param ctx the parse tree
     */
    void exitFromContextPath(ODataParser.FromContextPathContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#odataCommand}.
     *
     * @param ctx the parse tree
     */
    void enterOdataCommand(ODataParser.OdataCommandContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#odataCommand}.
     *
     * @param ctx the parse tree
     */
    void exitOdataCommand(ODataParser.OdataCommandContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#filter}.
     *
     * @param ctx the parse tree
     */
    void enterFilter(ODataParser.FilterContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#filter}.
     *
     * @param ctx the parse tree
     */
    void exitFilter(ODataParser.FilterContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#search}.
     *
     * @param ctx the parse tree
     */
    void enterSearch(ODataParser.SearchContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#search}.
     *
     * @param ctx the parse tree
     */
    void exitSearch(ODataParser.SearchContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#top}.
     *
     * @param ctx the parse tree
     */
    void enterTop(ODataParser.TopContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#top}.
     *
     * @param ctx the parse tree
     */
    void exitTop(ODataParser.TopContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#skip}.
     *
     * @param ctx the parse tree
     */
    void enterSkip(ODataParser.SkipContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#skip}.
     *
     * @param ctx the parse tree
     */
    void exitSkip(ODataParser.SkipContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#orderby}.
     *
     * @param ctx the parse tree
     */
    void enterOrderby(ODataParser.OrderbyContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#orderby}.
     *
     * @param ctx the parse tree
     */
    void exitOrderby(ODataParser.OrderbyContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#searchCommand}.
     *
     * @param ctx the parse tree
     */
    void enterSearchCommand(ODataParser.SearchCommandContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#searchCommand}.
     *
     * @param ctx the parse tree
     */
    void exitSearchCommand(ODataParser.SearchCommandContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#filterCommand}.
     *
     * @param ctx the parse tree
     */
    void enterFilterCommand(ODataParser.FilterCommandContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#filterCommand}.
     *
     * @param ctx the parse tree
     */
    void exitFilterCommand(ODataParser.FilterCommandContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#command}.
     *
     * @param ctx the parse tree
     */
    void enterCommand(ODataParser.CommandContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#command}.
     *
     * @param ctx the parse tree
     */
    void exitCommand(ODataParser.CommandContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#comparatorCommand}.
     *
     * @param ctx the parse tree
     */
    void enterComparatorCommand(ODataParser.ComparatorCommandContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#comparatorCommand}.
     *
     * @param ctx the parse tree
     */
    void exitComparatorCommand(ODataParser.ComparatorCommandContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#contains}.
     *
     * @param ctx the parse tree
     */
    void enterContains(ODataParser.ContainsContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#contains}.
     *
     * @param ctx the parse tree
     */
    void exitContains(ODataParser.ContainsContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#startsWith}.
     *
     * @param ctx the parse tree
     */
    void enterStartsWith(ODataParser.StartsWithContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#startsWith}.
     *
     * @param ctx the parse tree
     */
    void exitStartsWith(ODataParser.StartsWithContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#attribute}.
     *
     * @param ctx the parse tree
     */
    void enterAttribute(ODataParser.AttributeContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#attribute}.
     *
     * @param ctx the parse tree
     */
    void exitAttribute(ODataParser.AttributeContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#value}.
     *
     * @param ctx the parse tree
     */
    void enterValue(ODataParser.ValueContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#value}.
     *
     * @param ctx the parse tree
     */
    void exitValue(ODataParser.ValueContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#sortOrder}.
     *
     * @param ctx the parse tree
     */
    void enterSortOrder(ODataParser.SortOrderContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#sortOrder}.
     *
     * @param ctx the parse tree
     */
    void exitSortOrder(ODataParser.SortOrderContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#comparator}.
     *
     * @param ctx the parse tree
     */
    void enterComparator(ODataParser.ComparatorContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#comparator}.
     *
     * @param ctx the parse tree
     */
    void exitComparator(ODataParser.ComparatorContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#operator}.
     *
     * @param ctx the parse tree
     */
    void enterOperator(ODataParser.OperatorContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#operator}.
     *
     * @param ctx the parse tree
     */
    void exitOperator(ODataParser.OperatorContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#leftCurlyBracket}.
     *
     * @param ctx the parse tree
     */
    void enterLeftCurlyBracket(ODataParser.LeftCurlyBracketContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#leftCurlyBracket}.
     *
     * @param ctx the parse tree
     */
    void exitLeftCurlyBracket(ODataParser.LeftCurlyBracketContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#rightCurlyBracket}.
     *
     * @param ctx the parse tree
     */
    void enterRightCurlyBracket(ODataParser.RightCurlyBracketContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#rightCurlyBracket}.
     *
     * @param ctx the parse tree
     */
    void exitRightCurlyBracket(ODataParser.RightCurlyBracketContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#and}.
     *
     * @param ctx the parse tree
     */
    void enterAnd(ODataParser.AndContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#and}.
     *
     * @param ctx the parse tree
     */
    void exitAnd(ODataParser.AndContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#or}.
     *
     * @param ctx the parse tree
     */
    void enterOr(ODataParser.OrContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#or}.
     *
     * @param ctx the parse tree
     */
    void exitOr(ODataParser.OrContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#eq}.
     *
     * @param ctx the parse tree
     */
    void enterEq(ODataParser.EqContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#eq}.
     *
     * @param ctx the parse tree
     */
    void exitEq(ODataParser.EqContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#gt}.
     *
     * @param ctx the parse tree
     */
    void enterGt(ODataParser.GtContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#gt}.
     *
     * @param ctx the parse tree
     */
    void exitGt(ODataParser.GtContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#lt}.
     *
     * @param ctx the parse tree
     */
    void enterLt(ODataParser.LtContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#lt}.
     *
     * @param ctx the parse tree
     */
    void exitLt(ODataParser.LtContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#ge}.
     *
     * @param ctx the parse tree
     */
    void enterGe(ODataParser.GeContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#ge}.
     *
     * @param ctx the parse tree
     */
    void exitGe(ODataParser.GeContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#le}.
     *
     * @param ctx the parse tree
     */
    void enterLe(ODataParser.LeContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#le}.
     *
     * @param ctx the parse tree
     */
    void exitLe(ODataParser.LeContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#string}.
     *
     * @param ctx the parse tree
     */
    void enterString(ODataParser.StringContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#string}.
     *
     * @param ctx the parse tree
     */
    void exitString(ODataParser.StringContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#number}.
     *
     * @param ctx the parse tree
     */
    void enterNumber(ODataParser.NumberContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#number}.
     *
     * @param ctx the parse tree
     */
    void exitNumber(ODataParser.NumberContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#asc}.
     *
     * @param ctx the parse tree
     */
    void enterAsc(ODataParser.AscContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#asc}.
     *
     * @param ctx the parse tree
     */
    void exitAsc(ODataParser.AscContext ctx);

    /**
     * Enter a parse tree produced by {@link ODataParser#desc}.
     *
     * @param ctx the parse tree
     */
    void enterDesc(ODataParser.DescContext ctx);

    /**
     * Exit a parse tree produced by {@link ODataParser#desc}.
     *
     * @param ctx the parse tree
     */
    void exitDesc(ODataParser.DescContext ctx);
}
