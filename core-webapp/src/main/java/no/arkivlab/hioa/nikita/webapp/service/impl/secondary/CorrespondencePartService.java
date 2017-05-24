package no.arkivlab.hioa.nikita.webapp.service.impl.secondary;

import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePart;
import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePartInternal;
import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePartPerson;
import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePartUnit;
import nikita.repository.n5v4.secondary.ICorrespondencePartRepository;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.secondary.ICorrespondencePartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;

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
    public CorrespondencePartPerson updateCorrespondencePartPerson(String systemId, Long version,
                                                                   CorrespondencePartPerson incomingCorrespondencePart) {
        CorrespondencePartPerson existingCorrespondencePart =
                (CorrespondencePartPerson) getCorrespondencePartOrThrow(systemId);

        // Copy all the values you are allowed to copy ....
        existingCorrespondencePart.setContactInformation(incomingCorrespondencePart.getContactInformation());
        existingCorrespondencePart.setCorrespondencePartType(incomingCorrespondencePart.getCorrespondencePartType());
        existingCorrespondencePart.setdNumber(incomingCorrespondencePart.getdNumber());
        existingCorrespondencePart.setName(incomingCorrespondencePart.getName());
        existingCorrespondencePart.setSocialSecurityNumber(incomingCorrespondencePart.getSocialSecurityNumber());
        existingCorrespondencePart.setPostalAddress(incomingCorrespondencePart.getPostalAddress());
        existingCorrespondencePart.setResidingAddress(incomingCorrespondencePart.getResidingAddress());
        existingCorrespondencePart.setVersion(version);
        correspondencePartRepository.save(existingCorrespondencePart);
        return existingCorrespondencePart;
    }

    @Override
    public CorrespondencePartInternal updateCorrespondencePartInternal(String systemId, Long version,
                                                                       CorrespondencePartInternal incomingCorrespondencePart) {
        CorrespondencePartInternal existingCorrespondencePart =
                (CorrespondencePartInternal) getCorrespondencePartOrThrow(systemId);
        // Copy all the values you are allowed to copy ....
        existingCorrespondencePart.setCorrespondencePartType(incomingCorrespondencePart.getCorrespondencePartType());
        existingCorrespondencePart.setAdministrativeUnit(incomingCorrespondencePart.getAdministrativeUnit());
        existingCorrespondencePart.setCaseHandler(incomingCorrespondencePart.getCaseHandler());
        existingCorrespondencePart.setReferenceAdministrativeUnit(incomingCorrespondencePart.getReferenceAdministrativeUnit());
        existingCorrespondencePart.setReferenceCaseHandler(incomingCorrespondencePart.getReferenceCaseHandler());
        existingCorrespondencePart.setVersion(version);
        correspondencePartRepository.save(existingCorrespondencePart);
        return existingCorrespondencePart;
    }

    @Override
    public CorrespondencePartUnit updateCorrespondencePartUnit(String systemId, Long version,
                                                               CorrespondencePartUnit incomingCorrespondencePart) {
        CorrespondencePartUnit existingCorrespondencePart =
                (CorrespondencePartUnit) getCorrespondencePartOrThrow(systemId);
        // Copy all the values you are allowed to copy ....
        existingCorrespondencePart.setBusinessAddress(incomingCorrespondencePart.getBusinessAddress());
        existingCorrespondencePart.setPostalAddress(incomingCorrespondencePart.getPostalAddress());
        existingCorrespondencePart.setContactInformation(incomingCorrespondencePart.getContactInformation());
        existingCorrespondencePart.setContactPerson(incomingCorrespondencePart.getContactPerson());
        existingCorrespondencePart.setOrganisationNumber(incomingCorrespondencePart.getOrganisationNumber());
        existingCorrespondencePart.setName(incomingCorrespondencePart.getName());
        existingCorrespondencePart.setVersion(version);
        correspondencePartRepository.save(existingCorrespondencePart);
        return existingCorrespondencePart;
    }


    @Override
    public CorrespondencePartPerson createNewCorrespondencePartPerson(CorrespondencePartPerson
                                                                              correspondencePartPerson) {
        return correspondencePartRepository.save(correspondencePartPerson);
    }

    @Override
    public CorrespondencePartUnit createNewCorrespondencePartUnit(CorrespondencePartUnit correspondencePartUnit) {
        return correspondencePartRepository.save(correspondencePartUnit);
    }

    @Override
    public CorrespondencePartInternal createNewCorrespondencePartInternal(CorrespondencePartInternal
                                                                                  correspondencePartInternal) {
        return correspondencePartRepository.save(correspondencePartInternal);
    }

    @Override
    public CorrespondencePart findBySystemId(String correspondencePartSystemId) {
        return correspondencePartRepository.findBySystemId(correspondencePartSystemId);
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
