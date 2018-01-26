package no.arkivlab.hioa.nikita.webapp.service.interfaces;

import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;

/**
 * Created by tsodring on 4/6/17.
 * Common description of how most Noark services will be implemented
 *
 * Seems to be this class is redundant and can be removed ...
 */
public interface INoarkService {
    INikitaEntity updateNoarkEntity(String systemId, Long version, INikitaEntity updatedEntity);
    INikitaEntity createNewNoarkEntity(INikitaEntity entity);

    INikitaEntity findBySystemId(String systemId);
}
