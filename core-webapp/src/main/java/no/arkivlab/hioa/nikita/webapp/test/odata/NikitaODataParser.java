package no.arkivlab.hioa.nikita.webapp.test.odata;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class NikitaODataParser extends ODataBaseListener {

    private String sqlStatment;

    @Override
    public void enterOdataCommand(ODataParser.OdataCommandContext ctx) {
        super.enterOdataCommand(ctx);

        System.out.println("Entering enterOdataCommand. Found [" + ctx.getText
                () + "]");
    }

    @Override
    public void enterResource(ODataParser.ResourceContext ctx) {
        super.enterResource(ctx);
        sqlStatment = new String("SQL Statement : ");
        sqlStatment += "select * from " + ctx.getText() + " where ";
    }

    @Override
    public void enterAttribute(ODataParser.AttributeContext ctx) {
        super.enterAttribute(ctx);
        if (ctx.getParent() != null) {
            ParserRuleContext ruleContext = ctx.getParent();
            Token token = ruleContext.getStart();
            String command = token.getText();

            if (command.equalsIgnoreCase("contains")) {
                sqlStatment += ctx.getText() + " LIKE %";
            } else if (command.equalsIgnoreCase("startsWith")) {
                sqlStatment += ctx.getText() + " ";
            }

            System.out.println("Entering enterAttribute. Found [" + ctx.getText
                    () + "]");
        } else {
            System.out.println("In enterAttibute but parent null. Throw " +
                    "excpetion!!!");
        }
    }

    @Override
    public void enterValue(ODataParser.ValueContext ctx) {
        super.enterValue(ctx);
        if (ctx.getParent() != null) {
            String parent = ctx.getParent().getText();
            ParserRuleContext ruleContext = ctx.getParent();
            Token token = ruleContext.getStart();
            String command = token.getText();
            if (command.equalsIgnoreCase("contains")) {
                sqlStatment += ctx.getText() + "%";
            } else if (command.equalsIgnoreCase("startsWith")) {
                sqlStatment += ctx.getText() + "%";
            }

            System.out.println("Entering enterValue. Found [" + ctx.getText
                    () + "]");
        } else {
            System.out.println("In enterAttibute but parent null. Throw " +
                    "excpetion!!!");
        }


    }

    public String getSqlStatment() {
        return sqlStatment;
    }

    public void setSqlStatment(String sqlStatment) {
        this.sqlStatment = sqlStatment;
    }
}
