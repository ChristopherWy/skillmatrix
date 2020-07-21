package de.skillmatrix.app.web.rest;

import de.skillmatrix.app.domain.Mitarbeiterskills;
import de.skillmatrix.app.repository.MitarbeiterskillsRepository;
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
 * REST controller for managing {@link de.skillmatrix.app.domain.Mitarbeiterskills}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MitarbeiterskillsResource {

    private final Logger log = LoggerFactory.getLogger(MitarbeiterskillsResource.class);

    private static final String ENTITY_NAME = "mitarbeiterskills";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MitarbeiterskillsRepository mitarbeiterskillsRepository;

    public MitarbeiterskillsResource(MitarbeiterskillsRepository mitarbeiterskillsRepository) {
        this.mitarbeiterskillsRepository = mitarbeiterskillsRepository;
    }

    /**
     * {@code POST  /mitarbeiterskills} : Create a new mitarbeiterskills.
     *
     * @param mitarbeiterskills the mitarbeiterskills to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mitarbeiterskills, or with status {@code 400 (Bad Request)} if the mitarbeiterskills has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mitarbeiterskills")
    public ResponseEntity<Mitarbeiterskills> createMitarbeiterskills(@Valid @RequestBody Mitarbeiterskills mitarbeiterskills) throws URISyntaxException {
        log.debug("REST request to save Mitarbeiterskills : {}", mitarbeiterskills);
        if (mitarbeiterskills.getId() != null) {
            throw new BadRequestAlertException("A new mitarbeiterskills cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mitarbeiterskills result = mitarbeiterskillsRepository.save(mitarbeiterskills);
        return ResponseEntity.created(new URI("/api/mitarbeiterskills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mitarbeiterskills} : Updates an existing mitarbeiterskills.
     *
     * @param mitarbeiterskills the mitarbeiterskills to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mitarbeiterskills,
     * or with status {@code 400 (Bad Request)} if the mitarbeiterskills is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mitarbeiterskills couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mitarbeiterskills")
    public ResponseEntity<Mitarbeiterskills> updateMitarbeiterskills(@Valid @RequestBody Mitarbeiterskills mitarbeiterskills) throws URISyntaxException {
        log.debug("REST request to update Mitarbeiterskills : {}", mitarbeiterskills);
        if (mitarbeiterskills.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Mitarbeiterskills result = mitarbeiterskillsRepository.save(mitarbeiterskills);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mitarbeiterskills.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mitarbeiterskills} : get all the mitarbeiterskills.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mitarbeiterskills in body.
     */
    @GetMapping("/mitarbeiterskills")
    public ResponseEntity<List<Mitarbeiterskills>> getAllMitarbeiterskills(Pageable pageable) {
        log.debug("REST request to get a page of Mitarbeiterskills");
        Page<Mitarbeiterskills> page = mitarbeiterskillsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mitarbeiterskills/:id} : get the "id" mitarbeiterskills.
     *
     * @param id the id of the mitarbeiterskills to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mitarbeiterskills, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mitarbeiterskills/{id}")
    public ResponseEntity<Mitarbeiterskills> getMitarbeiterskills(@PathVariable Long id) {
        log.debug("REST request to get Mitarbeiterskills : {}", id);
        Optional<Mitarbeiterskills> mitarbeiterskills = mitarbeiterskillsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mitarbeiterskills);
    }

    /**
     * {@code DELETE  /mitarbeiterskills/:id} : delete the "id" mitarbeiterskills.
     *
     * @param id the id of the mitarbeiterskills to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mitarbeiterskills/{id}")
    public ResponseEntity<Void> deleteMitarbeiterskills(@PathVariable Long id) {
        log.debug("REST request to delete Mitarbeiterskills : {}", id);
        mitarbeiterskillsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
