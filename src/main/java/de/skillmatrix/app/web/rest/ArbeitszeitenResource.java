package de.skillmatrix.app.web.rest;

import de.skillmatrix.app.domain.Arbeitszeiten;
import de.skillmatrix.app.repository.ArbeitszeitenRepository;
import de.skillmatrix.app.web.rest.errors.BadRequestAlertException;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link de.skillmatrix.app.domain.Arbeitszeiten}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ArbeitszeitenResource {

    private final Logger log = LoggerFactory.getLogger(ArbeitszeitenResource.class);

    private static final String ENTITY_NAME = "arbeitszeiten";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArbeitszeitenRepository arbeitszeitenRepository;

    public ArbeitszeitenResource(ArbeitszeitenRepository arbeitszeitenRepository) {
        this.arbeitszeitenRepository = arbeitszeitenRepository;
    }

    /**
     * {@code POST  /arbeitszeitens} : Create a new arbeitszeiten.
     *
     * @param arbeitszeiten the arbeitszeiten to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new arbeitszeiten, or with status {@code 400 (Bad Request)} if the arbeitszeiten has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/arbeitszeitens")
    public ResponseEntity<Arbeitszeiten> createArbeitszeiten(@Valid @RequestBody Arbeitszeiten arbeitszeiten) throws URISyntaxException {
        log.debug("REST request to save Arbeitszeiten : {}", arbeitszeiten);
        if (arbeitszeiten.getId() != null) {
            throw new BadRequestAlertException("A new arbeitszeiten cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Arbeitszeiten result = arbeitszeitenRepository.save(arbeitszeiten);
        return ResponseEntity.created(new URI("/api/arbeitszeitens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /arbeitszeitens} : Updates an existing arbeitszeiten.
     *
     * @param arbeitszeiten the arbeitszeiten to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arbeitszeiten,
     * or with status {@code 400 (Bad Request)} if the arbeitszeiten is not valid,
     * or with status {@code 500 (Internal Server Error)} if the arbeitszeiten couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/arbeitszeitens")
    public ResponseEntity<Arbeitszeiten> updateArbeitszeiten(@Valid @RequestBody Arbeitszeiten arbeitszeiten) throws URISyntaxException {
        log.debug("REST request to update Arbeitszeiten : {}", arbeitszeiten);
        if (arbeitszeiten.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Arbeitszeiten result = arbeitszeitenRepository.save(arbeitszeiten);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, arbeitszeiten.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /arbeitszeitens} : get all the arbeitszeitens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arbeitszeitens in body.
     */
    @GetMapping("/arbeitszeitens")
    public ResponseEntity<List<Arbeitszeiten>> getAllArbeitszeitens(Pageable pageable) {
        log.debug("REST request to get a page of Arbeitszeitens");
        Page<Arbeitszeiten> page = arbeitszeitenRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /arbeitszeitens/:id} : get the "id" arbeitszeiten.
     *
     * @param id the id of the arbeitszeiten to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the arbeitszeiten, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/arbeitszeitens/{id}")
    public ResponseEntity<Arbeitszeiten> getArbeitszeiten(@PathVariable Long id) {
        log.debug("REST request to get Arbeitszeiten : {}", id);
        Optional<Arbeitszeiten> arbeitszeiten = arbeitszeitenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(arbeitszeiten);
    }

    /**
     * {@code DELETE  /arbeitszeitens/:id} : delete the "id" arbeitszeiten.
     *
     * @param id the id of the arbeitszeiten to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/arbeitszeitens/{id}")
    public ResponseEntity<Void> deleteArbeitszeiten(@PathVariable Long id) {
        log.debug("REST request to delete Arbeitszeiten : {}", id);
        arbeitszeitenRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
