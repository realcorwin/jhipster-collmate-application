package dik.jhipster.collmate.service;

import dik.jhipster.collmate.domain.Cd;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Cd}.
 */
public interface CdService {

    /**
     * Save a cd.
     *
     * @param cd the entity to save.
     * @return the persisted entity.
     */
    Cd save(Cd cd);

    /**
     * Get all the cds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Cd> findAll(Pageable pageable);


    /**
     * Get the "id" cd.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Cd> findOne(String id);

    /**
     * Delete the "id" cd.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
