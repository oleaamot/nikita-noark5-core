package nikita.webapp.web.controller.hateoas.odata;

import nikita.common.model.noark5.v4.NoarkEntity;
import nikita.webapp.odata.NikitaODataToHQLWalker;
import nikita.webapp.odata.ODataLexer;
import nikita.webapp.odata.ODataParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.List;

import static org.apache.commons.lang3.CharEncoding.UTF_8;


@RestController
@RequestMapping(value = "odata")
public class OdataTest {


    private EntityManager entityManager;

    public OdataTest(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "arkivstruktur/{\\w*}"
//            value = "{\\w*}"
    )
    public ResponseEntity<String> testOdata(
            final UriComponentsBuilder uriBuilder, HttpServletRequest
            request, final HttpServletResponse response
    )
            throws Exception {


        String uqueryString = request.getQueryString();
        String decoded = URLDecoder.decode(uqueryString, UTF_8);

        StringBuffer originalRequest = request.getRequestURL();
        originalRequest.append("?" + decoded);
        CharStream stream = CharStreams.fromString(originalRequest.toString());

        ODataLexer lexer = new ODataLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ODataParser parser = new ODataParser(tokens);
        ParseTree tree = parser.odataURL();
        ParseTreeWalker walker = new ParseTreeWalker();

        // Make the HQL Statement
        NikitaODataToHQLWalker hqlWalker = new NikitaODataToHQLWalker();
        walker.walk(hqlWalker, tree);

        Session session = entityManager.unwrap(org.hibernate.Session.class);
        Query query = hqlWalker.getHqlStatment(session);
        String queryString = query.getQueryString();
        System.out.println(queryString);
        List<NoarkEntity> list = query.getResultList();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(list.toString());
    }
}
