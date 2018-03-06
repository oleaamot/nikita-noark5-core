package no.arkivlab.hioa.nikita.webapp.test.odata;

public interface IODataWalker {

    void processEnterAttribute(ODataParser.AttributeContext ctx);

    void processResource(String entity, String loggedInUser);

    void processEnterValue(ODataParser.ValueContext ctx);

    void processContains(String attribute, String value);

    void processStartsWith(String attribute, String value);

    void processFilterCommand(String attribute, String comparator, String
            value);

    void processSkipCommand(Integer skip);

    void processTopCommand(Integer top);

    void processOrderByCommand(String attribute, String sortOrder);
}
