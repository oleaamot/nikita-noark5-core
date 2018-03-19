package nikita.webapp.service.impl.secondary;

import nikita.common.model.noark5.v4.casehandling.secondary.CorrespondencePart;
import nikita.common.model.noark5.v4.casehandling.secondary.CorrespondencePartInternal;
import nikita.common.model.noark5.v4.casehandling.secondary.CorrespondencePartPerson;
import nikita.common.model.noark5.v4.casehandling.secondary.CorrespondencePartUnit;
import nikita.common.repository.n5v4.secondary.ICorrespondencePartRepository;
import nikita.common.util.exceptions.NoarkEntityNotFoundException;
import nikita.webapp.service.interfaces.secondary.ICorrespondencePartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

import static nikita.common.config.Constants.INFO_CANNOT_FIND_OBJECT;

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
        /*
        TODO: Temp disabled!
        CorrespondencePartPerson existingCorrespondencePart =
                (CorrespondencePartPerson) getCorrespondencePartOrThrow(systemId);

        // Copy all the values you are allowed to copy ....
        existingCorrespondencePart.setContactInformation(incomingCorrespondencePart.getContactInformation());
        existingCorrespondencePart.setdNumber(incomingCorrespondencePart.getdNumber());
        existingCorrespondencePart.setName(incomingCorrespondencePart.getName());
        existingCorrespondencePart.setSocialSecurityNumber(incomingCorrespondencePart.getSocialSecurityNumber());
        existingCorrespondencePart.setPostalAddress(incomingCorrespondencePart.getPostalAddress());
        existingCorrespondencePart.setResidingAddress(incomingCorrespondencePart.getResidingAddress());
        existingCorrespondencePart.setVersion(version);
        correspondencePartRepository.save(existingCorrespondencePart);
        return existingCorrespondencePart;*/
        return null;

    }

    @Override
    public CorrespondencePartInternal updateCorrespondencePartInternal(String systemId, Long version,
                                                                       CorrespondencePartInternal incomingCorrespondencePart) {
        CorrespondencePartInternal existingCorrespondencePart =
                (CorrespondencePartInternal) getCorrespondencePartOrThrow(systemId);
        // Copy all the values you are allowed to copy ....

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
/*
        TODO: Temp disabled!

        return correspondencePartRepository.save(correspondencePartPerson);

        TODO: Temp disabled!

        */
        return null;
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

    @Override
    public void deleteCorrespondencePartUnit(@NotNull String code) {
        CorrespondencePartUnit correspondencePartUnit = (CorrespondencePartUnit) getCorrespondencePartOrThrow(code);
        correspondencePartRepository.delete(correspondencePartUnit);
    }

    @Override
    public void deleteCorrespondencePartPerson(@NotNull String code) {
        /*
        TODO: Temp disabled!
        CorrespondencePartPerson correspondencePartPerson = (CorrespondencePartPerson) getCorrespondencePartOrThrow(code);
        correspondencePartRepository.delete(correspondencePartPerson);
        */
    }

    @Override
    public void deleteCorrespondencePartInternal(@NotNull String code) {
        CorrespondencePartInternal correspondencePartInternal = (CorrespondencePartInternal) getCorrespondencePartOrThrow(code);

/*
        // Disassociate the link between Fonds and FondsCreator
        // https://github.com/HiOA-ABI/nikita-noark5-core/issues/82
        Query q = entityManager.createNativeQuery("DELETE FROM fonds_fonds_creator WHERE f_pk_fonds_id  = :id ;");
        q.setParameter("id", fonds.getId());
        q.executeUpdate();
        entityManager.remove(fonds);
        entityManager.flush();
        entityManager.clear();*/
        correspondencePartRepository.delete(correspondencePartInternal);
    }
}
