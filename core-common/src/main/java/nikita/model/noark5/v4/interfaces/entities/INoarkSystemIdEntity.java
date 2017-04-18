package nikita.model.noark5.v4.interfaces.entities;

/**
 * Initially this interface was only to define get/put for systemId and became integral throughout the code. As nikita
 * evolved, we saw the need for eTag / JPA version support for entities. This became the obvious place to add support
 * for version. Then we needed support for an entity to tell its base name i.e. fonds will say baseNameType is arkiv
 * and this is the obvious place to add it. This is because we INoarkSystemIdEntity is used in so many places,
 * especially the JPA stuff.
 * <p>
 * Also the interface standard this core implements is also evolving so it was difficult to see breath and depth when
 * we started. Not all entities supported systemId, but apparently that will be in place in the next version.
 * <p>
 * Probably the best thing to do would be to refactor the name of this to generic Noark entity or similar.
 * <p>
 * There is a INoarkGeneralEntity that combines this and a few more properties. So perhaps INoarkGeneralEntity and
 * INoarkSystemIdEntity should be combined. I don't have time to do that at the moment though.
 */
public interface INoarkSystemIdEntity  {
    String getSystemId();

    void setSystemId(String systemId);

    Long getVersion();

    void setVersion(Long versionNumber);

    String getBaseTypeName();

    // There is no setBaseTypeName(String baseTypeName); as this is hardwired

}
