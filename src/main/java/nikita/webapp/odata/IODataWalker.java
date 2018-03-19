package nikita.webapp.odata;

public interface IODataWalker {

    void processResource(String entity, String loggedInUser);

    void processContains(String attribute, String value);

    void processStartsWith(String attribute, String value);

    void processSkipCommand(Integer skip);

    void processTopCommand(Integer top);

    void processOrderByCommand(String attribute, String sortOrder);

    void processComparatorCommand(String attribute, String comparator,
                                  String value);
}
