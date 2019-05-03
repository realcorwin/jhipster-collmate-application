package dik.jhipster.collmate.service.impl;

import dik.jhipster.collmate.service.DvdService;
import dik.jhipster.collmate.domain.Dvd;
import dik.jhipster.collmate.repository.DvdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Dvd}.
 */
@Service
public class DvdServiceImpl implements DvdService {

    private final Logger log = LoggerFactory.getLogger(DvdServiceImpl.class);

    private final DvdRepository dvdRepository;

    public DvdServiceImpl(DvdRepository dvdRepository) {
        this.dvdRepository = dvdRepository;
    }

    /**
     * Save a dvd.
     *
     * @param dvd the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Dvd save(Dvd dvd) {
        log.debug("Request to save Dvd : {}", dvd);
        return dvdRepository.save(dvd);
    }

    /**
     * Get all the dvds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Dvd> findAll(Pageable pageable) {
        log.debug("Request to get all Dvds");
        return dvdRepository.findAll(pageable);
    }


    /**
     * Get one dvd by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Dvd> findOne(String id) {
        log.debug("Request to get Dvd : {}", id);
        return dvdRepository.findById(id);
    }

    /**
     * Delete the dvd by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Dvd : {}", id);
        dvdRepository.deleteById(id);
    }
}
