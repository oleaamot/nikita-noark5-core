package nikita.webapp.hateoas.interfaces;

import nikita.common.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.webapp.security.IAuthorisation;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface IHateoasHandler {

    void addLinks(IHateoasNoarkObject hateoasNoarkObject,
                  IAuthorisation authorisation);

    void addLinksOnCreate(IHateoasNoarkObject hateoasNoarkObject,
                          IAuthorisation authorisation);

    void addLinksOnTemplate(IHateoasNoarkObject hateoasNoarkObject,
                            IAuthorisation authorisationt);

    void addLinksOnRead(IHateoasNoarkObject hateoasNoarkObject,
                        IAuthorisation authorisation);

    void addLinksOnUpdate(IHateoasNoarkObject hateoasNoarkObject,
                          IAuthorisation authorisation);

    void addLinksOnDelete(IHateoasNoarkObject hateoasNoarkObject,
                          IAuthorisation authorisation);

    void addLinksOnNew(IHateoasNoarkObject hateoasNoarkObject,
                       IAuthorisation authorisation);

    void addSelfLink(INikitaEntity entity,
                     IHateoasNoarkObject hateoasNoarkObject);

    void addEntityLinks(INikitaEntity entity,
                        IHateoasNoarkObject hateoasNoarkObject);

    void addEntityLinksOnCreate(INikitaEntity entity,
                                IHateoasNoarkObject hateoasNoarkObject);

    void addEntityLinksOnTemplate(INikitaEntity entity,
                                  IHateoasNoarkObject hateoasNoarkObject);

    void addEntityLinksOnRead(INikitaEntity entity,
                              IHateoasNoarkObject hateoasNoarkObject);

    void addEntityLinksOnNew(INikitaEntity entity,
                             IHateoasNoarkObject hateoasNoarkObject);

    void addDocumentMedium(INikitaEntity entity,
                           IHateoasNoarkObject hateoasNoarkObject);
}
