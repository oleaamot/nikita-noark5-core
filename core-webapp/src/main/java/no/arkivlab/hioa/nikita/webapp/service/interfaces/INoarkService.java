package no.arkivlab.hioa.nikita.webapp.service.interfaces;

import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

/**
 * Created by tsodring on 4/6/17.
 * Common description of how most Noark services will be implemented
 */
public interface INoarkService {
    INoarkSystemIdEntity updateNoarkEntity(String systemId, INoarkSystemIdEntity updatedEntity);

    INoarkSystemIdEntity createNewNoarkEntity(INoarkSystemIdEntity entity);
}
