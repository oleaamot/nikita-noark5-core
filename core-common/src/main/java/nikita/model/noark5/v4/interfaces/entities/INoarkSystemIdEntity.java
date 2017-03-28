package nikita.model.noark5.v4.interfaces.entities;

// Including eTag / version support here. Anything that has a systemId will also have a version /ETAG (@Version) value
// Consider refactoring INoarkSystemIdEntity to INoarkSystemIdAndVersionEntity
public interface INoarkSystemIdEntity  {
    String getSystemId();

    void setSystemId(String systemId);

    Long getVersion();

    void setVersion(Long versionNumber);
}
