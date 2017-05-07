package no.arkivlab.hioa.nikita.webapp.service.impl.secondary;

import nikita.model.noark5.v4.interfaces.IPrecedence;
import nikita.model.noark5.v4.secondary.Precedence;
import nikita.repository.n5v4.secondary.IPrecedenceRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.secondary.IPrecedenceService;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;

@Service
@Transactional
public class PrecedenceService implements IPrecedenceService {

    private static final Logger logger = LoggerFactory.getLogger(PrecedenceService.class);

    // TODO: Trying to pick up property from yaml file, but not working ...
    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    Integer maxPageSize = new Integer(10);

    private IPrecedenceRepository precedenceRepository;

    public PrecedenceService(IPrecedenceRepository precedenceRepository) {
        this.precedenceRepository = precedenceRepository;
    }

    @Override
    public Precedence updatePrecedence(String systemId, Long version, Precedence incomingPrecedence) {
        Precedence existingPrecedence = getPrecedenceOrThrow(systemId);
        // Copy all the values you are allowed to copy ....
        existingPrecedence.setDescription(incomingPrecedence.getDescription());
        existingPrecedence.setPrecedenceDate(incomingPrecedence.getPrecedenceDate());
        existingPrecedence.setPrecedenceApprovedBy(incomingPrecedence.getPrecedenceApprovedBy());
        existingPrecedence.setPrecedenceApprovedDate(incomingPrecedence.getPrecedenceApprovedDate());
        existingPrecedence.setPrecedenceAuthority(incomingPrecedence.getPrecedenceAuthority());
        precedenceRepository.save(existingPrecedence);
        return existingPrecedence;
    }

    @Override
    public Precedence createNewPrecedence(Precedence entity) {
        return precedenceRepository.save(entity);
    }

    @Override
    public Precedence findBySystemId(String precedenceSystemId) {
        return precedenceRepository.findBySystemId(precedenceSystemId);
    }


    /**
     * Internal helper method. Rather than having a find and try catch in multiple methods, we have it here once.
     * If you call this, be aware that you will only ever get a valid Precedence back. If there is no valid
     * Precedence, an exception is thrown
     *
     * @param precedenceSystemId
     * @return
     */
    protected Precedence getPrecedenceOrThrow(@NotNull String precedenceSystemId) {
        Precedence precedence = precedenceRepository.findBySystemId(precedenceSystemId);
        if (precedence == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " Precedence, using systemId " + precedenceSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return precedence;
    }
}
