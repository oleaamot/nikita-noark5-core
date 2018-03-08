package no.arkivlab.hioa.nikita.webapp.run;

import no.arkivlab.hioa.nikita.webapp.test.odata.NikitaODataToSQLWalker;
import no.arkivlab.hioa.nikita.webapp.test.odata.ODataLexer;
import no.arkivlab.hioa.nikita.webapp.test.odata.ODataParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.InputStream;
public class TestODataApp {


    public static void main(String[] args) throws Exception {

        System.out.println("Starting OData parser test");
        System.out.println("Picks first line from odata_samples.txt in " +
                "resources folder.");
        try {

            AfterApplicationStartup afterApplicationStartup =
                    new AfterApplicationStartup(null);
            afterApplicationStartup.populateTranslatedNames();
            TestODataApp app = new TestODataApp();

            ODataLexer lexer = new ODataLexer(
                    CharStreams.fromStream(app.getInputStreamForParseFile(
                            "odata" + File.separator +
                                    "odata_samples.txt")));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ODataParser parser = new ODataParser(tokens);
            ParseTree tree = parser.odataURL();
            ParseTreeWalker walker = new ParseTreeWalker();

            // Make the SQL Statement
            NikitaODataToSQLWalker sqlWalker = new NikitaODataToSQLWalker();
            walker.walk(sqlWalker, tree);
            System.out.println(sqlWalker.getSqlStatement());

        } catch (RecognitionException e) {
            throw new IllegalStateException("Recognition exception");
        }
    }

    /**
     * Get an input stream from a file on the classpath (resources folder)
     *
     * @param fileName the name of the file you wish to retrieve
     * @return the inputstream to the file
     */
    private InputStream getInputStreamForParseFile(String fileName) {

        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream(fileName);
        return in;
    }
}
