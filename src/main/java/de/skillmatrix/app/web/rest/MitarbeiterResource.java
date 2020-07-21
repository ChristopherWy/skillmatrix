package de.skillmatrix.app.web.rest;

import de.skillmatrix.app.domain.Mitarbeiter;
import de.skillmatrix.app.repository.MitarbeiterRepository;
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
 * REST controller for managing {@link de.skillmatrix.app.domain.Mitarbeiter}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MitarbeiterResource {

    private final Logger log = LoggerFactory.getLogger(MitarbeiterResource.class);

    private static final String ENTITY_NAME = "mitarbeiter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MitarbeiterRepository mitarbeiterRepository;

    public MitarbeiterResource(MitarbeiterRepository mitarbeiterRepository) {
        this.mitarbeiterRepository = mitarbeiterRepository;
    }

    /**
     * {@code POST  /mitarbeiters} : Create a new mitarbeiter.
     *
     * @param mitarbeiter the mitarbeiter to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mitarbeiter, or with status {@code 400 (Bad Request)} if the mitarbeiter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mitarbeiters")
    public ResponseEntity<Mitarbeiter> createMitarbeiter(@Valid @RequestBody Mitarbeiter mitarbeiter) throws URISyntaxException {
        log.debug("REST request to save Mitarbeiter : {}", mitarbeiter);
        if (mitarbeiter.getId() != null) {
            throw new BadRequestAlertException("A new mitarbeiter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mitarbeiter result = mitarbeiterRepository.save(mitarbeiter);
        return ResponseEntity.created(new URI("/api/mitarbeiters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mitarbeiters} : Updates an existing mitarbeiter.
     *
     * @param mitarbeiter the mitarbeiter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mitarbeiter,
     * or with status {@code 400 (Bad Request)} if the mitarbeiter is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mitarbeiter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mitarbeiters")
    public ResponseEntity<Mitarbeiter> updateMitarbeiter(@Valid @RequestBody Mitarbeiter mitarbeiter) throws URISyntaxException {
        log.debug("REST request to update Mitarbeiter : {}", mitarbeiter);
        if (mitarbeiter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Mitarbeiter result = mitarbeiterRepository.save(mitarbeiter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mitarbeiter.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mitarbeiters} : get all the mitarbeiters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mitarbeiters in body.
     */
    @GetMapping("/mitarbeiters")
    public ResponseEntity<List<Mitarbeiter>> getAllMitarbeiters(Pageable pageable) {
        log.debug("REST request to get a page of Mitarbeiters");
        Page<Mitarbeiter> page = mitarbeiterRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mitarbeiters/:id} : get the "id" mitarbeiter.
     *
     * @param id the id of the mitarbeiter to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mitarbeiter, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mitarbeiters/{id}")
    public ResponseEntity<Mitarbeiter> getMitarbeiter(@PathVariable Long id) {
        log.debug("REST request to get Mitarbeiter : {}", id);
        Optional<Mitarbeiter> mitarbeiter = mitarbeiterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mitarbeiter);
    }

    /**
     * {@code DELETE  /mitarbeiters/:id} : delete the "id" mitarbeiter.
     *
     * @param id the id of the mitarbeiter to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mitarbeiters/{id}")
    public ResponseEntity<Void> deleteMitarbeiter(@PathVariable Long id) {
        log.debug("REST request to delete Mitarbeiter : {}", id);
        mitarbeiterRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
