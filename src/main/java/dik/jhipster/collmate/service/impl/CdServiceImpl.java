package dik.jhipster.collmate.service.impl;

import dik.jhipster.collmate.service.CdService;
import dik.jhipster.collmate.domain.Cd;
import dik.jhipster.collmate.repository.CdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Cd}.
 */
@Service
public class CdServiceImpl implements CdService {

    private final Logger log = LoggerFactory.getLogger(CdServiceImpl.class);

    private final CdRepository cdRepository;

    public CdServiceImpl(CdRepository cdRepository) {
        this.cdRepository = cdRepository;
    }

    /**
     * Save a cd.
     *
     * @param cd the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Cd save(Cd cd) {
        log.debug("Request to save Cd : {}", cd);
        return cdRepository.save(cd);
    }

    /**
     * Get all the cds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Cd> findAll(Pageable pageable) {
        log.debug("Request to get all Cds");
        return cdRepository.findAll(pageable);
    }


    /**
     * Get one cd by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Cd> findOne(String id) {
        log.debug("Request to get Cd : {}", id);
        return cdRepository.findById(id);
    }

    /**
     * Delete the cd by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Cd : {}", id);
        cdRepository.deleteById(id);
    }
}
