package dik.jhipster.collmate.web.rest;

import dik.jhipster.collmate.domain.Dvd;
import dik.jhipster.collmate.service.DvdService;
import dik.jhipster.collmate.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link dik.jhipster.collmate.domain.Dvd}.
 */
@RestController
@RequestMapping("/api")
public class DvdResource {

    private final Logger log = LoggerFactory.getLogger(DvdResource.class);

    private static final String ENTITY_NAME = "dvd";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DvdService dvdService;

    public DvdResource(DvdService dvdService) {
        this.dvdService = dvdService;
    }

    /**
     * {@code POST  /dvds} : Create a new dvd.
     *
     * @param dvd the dvd to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dvd, or with status {@code 400 (Bad Request)} if the dvd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dvds")
    public ResponseEntity<Dvd> createDvd(@Valid @RequestBody Dvd dvd) throws URISyntaxException {
        log.debug("REST request to save Dvd : {}", dvd);
        if (dvd.getId() != null) {
            throw new BadRequestAlertException("A new dvd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dvd result = dvdService.save(dvd);
        return ResponseEntity.created(new URI("/api/dvds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dvds} : Updates an existing dvd.
     *
     * @param dvd the dvd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dvd,
     * or with status {@code 400 (Bad Request)} if the dvd is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dvd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dvds")
    public ResponseEntity<Dvd> updateDvd(@Valid @RequestBody Dvd dvd) throws URISyntaxException {
        log.debug("REST request to update Dvd : {}", dvd);
        if (dvd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dvd result = dvdService.save(dvd);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dvd.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dvds} : get all the dvds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dvds in body.
     */
    @GetMapping("/dvds")
    public ResponseEntity<List<Dvd>> getAllDvds(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Dvds");
        Page<Dvd> page = dvdService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dvds/:id} : get the "id" dvd.
     *
     * @param id the id of the dvd to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dvd, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dvds/{id}")
    public ResponseEntity<Dvd> getDvd(@PathVariable String id) {
        log.debug("REST request to get Dvd : {}", id);
        Optional<Dvd> dvd = dvdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dvd);
    }

    /**
     * {@code DELETE  /dvds/:id} : delete the "id" dvd.
     *
     * @param id the id of the dvd to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dvds/{id}")
    public ResponseEntity<Void> deleteDvd(@PathVariable String id) {
        log.debug("REST request to delete Dvd : {}", id);
        dvdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
