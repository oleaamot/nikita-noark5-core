package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.CorrespondencePart;
import nikita.model.noark5.v4.Series;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.repository.n5v4.ICorrespondencePartRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.secondary.ICorrespondencePartService;
import no.arkivlab.hioa.nikita.webapp.util.NoarkUtils;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityEditWhenClosedException;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkInvalidStructureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.STATUS_CLOSED;
import static nikita.config.N5ResourceMappings.STATUS_OPEN;

@Service
@Transactional
public class CorrespondencePartService implements ICorrespondencePartService {

    private static final Logger logger = LoggerFactory.getLogger(CorrespondencePartService.class);
    // TODO: Trying to pick up property from yaml file, but not working ...
    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    Integer maxPageSize = new Integer(10);
    private ICorrespondencePartRepository correspondencePartRepository;

    public CorrespondencePartService(ICorrespondencePartRepository correspondencePartRepository) {
        this.correspondencePartRepository = correspondencePartRepository;
    }

    @Override
    public INoarkSystemIdEntity updateNoarkEntity(String systemId, Long version, INoarkSystemIdEntity noarkSystemIdEntity) {
        CorrespondencePart existingCorrespondencePart = getCorrespondencePartOrThrow(systemId);
        // Copy all the values you are allowed to copy ....
        CorrespondencePart incomingCorrespondencePart = (CorrespondencePart) noarkSystemIdEntity;
        existingCorrespondencePart.setAdministrativeUnit(incomingCorrespondencePart.getAdministrativeUnit());
        existingCorrespondencePart.setCaseHandler(incomingCorrespondencePart.getCaseHandler());
        existingCorrespondencePart.setContactPerson(incomingCorrespondencePart.getContactPerson());
        existingCorrespondencePart.setCorrespondencePartName(incomingCorrespondencePart.getCorrespondencePartName());
        existingCorrespondencePart.setCountry(incomingCorrespondencePart.getCountry());
        existingCorrespondencePart.setPostalTown(incomingCorrespondencePart.getPostalTown());existingCorrespondencePart.setCorrespondencePartType(incomingCorrespondencePart.getCorrespondencePartType());existingCorrespondencePart.setEmailAddress(incomingCorrespondencePart.getEmailAddress());
        existingCorrespondencePart.setPostalAddress(incomingCorrespondencePart.getPostalAddress());
        existingCorrespondencePart.setPostCode(incomingCorrespondencePart.getPostCode());
        existingCorrespondencePart.setVersion(version);
        correspondencePartRepository.save(existingCorrespondencePart);
        return existingCorrespondencePart;
    }

    @Override
    public INoarkSystemIdEntity createNewNoarkEntity(INoarkSystemIdEntity entity) {
        return correspondencePartRepository.save((CorrespondencePart)entity);
    }

    /**
     * Internal helper method. Rather than having a find and try catch in multiple methods, we have it here once.
     * If you call this, be aware that you will only ever get a valid CorrespondencePart back. If there is no valid
     * CorrespondencePart, an exception is thrown
     *
     * @param correspondencePartSystemId
     * @return
     */
    protected CorrespondencePart getCorrespondencePartOrThrow(@NotNull String correspondencePartSystemId) {
        CorrespondencePart correspondencePart = correspondencePartRepository.findBySystemId(correspondencePartSystemId);
        if (correspondencePart == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " CorrespondencePart, using systemId " + correspondencePartSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return correspondencePart;
    }
}
