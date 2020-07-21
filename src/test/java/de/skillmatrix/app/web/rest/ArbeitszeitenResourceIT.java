package de.skillmatrix.app.web.rest;

import de.skillmatrix.app.SkillmatrixApp;
import de.skillmatrix.app.domain.Arbeitszeiten;
import de.skillmatrix.app.repository.ArbeitszeitenRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ArbeitszeitenResource} REST controller.
 */
@SpringBootTest(classes = SkillmatrixApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArbeitszeitenResourceIT {

    private static final Integer DEFAULT_WOCHENSTUNDEN = 1;
    private static final Integer UPDATED_WOCHENSTUNDEN = 2;

    @Autowired
    private ArbeitszeitenRepository arbeitszeitenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArbeitszeitenMockMvc;

    private Arbeitszeiten arbeitszeiten;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arbeitszeiten createEntity(EntityManager em) {
        Arbeitszeiten arbeitszeiten = new Arbeitszeiten()
            .wochenstunden(DEFAULT_WOCHENSTUNDEN);
        return arbeitszeiten;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arbeitszeiten createUpdatedEntity(EntityManager em) {
        Arbeitszeiten arbeitszeiten = new Arbeitszeiten()
            .wochenstunden(UPDATED_WOCHENSTUNDEN);
        return arbeitszeiten;
    }

    @BeforeEach
    public void initTest() {
        arbeitszeiten = createEntity(em);
    }

    @Test
    @Transactional
    public void createArbeitszeiten() throws Exception {
        int databaseSizeBeforeCreate = arbeitszeitenRepository.findAll().size();
        // Create the Arbeitszeiten
        restArbeitszeitenMockMvc.perform(post("/api/arbeitszeitens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arbeitszeiten)))
            .andExpect(status().isCreated());

        // Validate the Arbeitszeiten in the database
        List<Arbeitszeiten> arbeitszeitenList = arbeitszeitenRepository.findAll();
        assertThat(arbeitszeitenList).hasSize(databaseSizeBeforeCreate + 1);
        Arbeitszeiten testArbeitszeiten = arbeitszeitenList.get(arbeitszeitenList.size() - 1);
        assertThat(testArbeitszeiten.getWochenstunden()).isEqualTo(DEFAULT_WOCHENSTUNDEN);
    }

    @Test
    @Transactional
    public void createArbeitszeitenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = arbeitszeitenRepository.findAll().size();

        // Create the Arbeitszeiten with an existing ID
        arbeitszeiten.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArbeitszeitenMockMvc.perform(post("/api/arbeitszeitens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arbeitszeiten)))
            .andExpect(status().isBadRequest());

        // Validate the Arbeitszeiten in the database
        List<Arbeitszeiten> arbeitszeitenList = arbeitszeitenRepository.findAll();
        assertThat(arbeitszeitenList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkWochenstundenIsRequired() throws Exception {
        int databaseSizeBeforeTest = arbeitszeitenRepository.findAll().size();
        // set the field null
        arbeitszeiten.setWochenstunden(null);

        // Create the Arbeitszeiten, which fails.


        restArbeitszeitenMockMvc.perform(post("/api/arbeitszeitens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arbeitszeiten)))
            .andExpect(status().isBadRequest());

        List<Arbeitszeiten> arbeitszeitenList = arbeitszeitenRepository.findAll();
        assertThat(arbeitszeitenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArbeitszeitens() throws Exception {
        // Initialize the database
        arbeitszeitenRepository.saveAndFlush(arbeitszeiten);

        // Get all the arbeitszeitenList
        restArbeitszeitenMockMvc.perform(get("/api/arbeitszeitens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arbeitszeiten.getId().intValue())))
            .andExpect(jsonPath("$.[*].wochenstunden").value(hasItem(DEFAULT_WOCHENSTUNDEN)));
    }
    
    @Test
    @Transactional
    public void getArbeitszeiten() throws Exception {
        // Initialize the database
        arbeitszeitenRepository.saveAndFlush(arbeitszeiten);

        // Get the arbeitszeiten
        restArbeitszeitenMockMvc.perform(get("/api/arbeitszeitens/{id}", arbeitszeiten.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(arbeitszeiten.getId().intValue()))
            .andExpect(jsonPath("$.wochenstunden").value(DEFAULT_WOCHENSTUNDEN));
    }
    @Test
    @Transactional
    public void getNonExistingArbeitszeiten() throws Exception {
        // Get the arbeitszeiten
        restArbeitszeitenMockMvc.perform(get("/api/arbeitszeitens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArbeitszeiten() throws Exception {
        // Initialize the database
        arbeitszeitenRepository.saveAndFlush(arbeitszeiten);

        int databaseSizeBeforeUpdate = arbeitszeitenRepository.findAll().size();

        // Update the arbeitszeiten
        Arbeitszeiten updatedArbeitszeiten = arbeitszeitenRepository.findById(arbeitszeiten.getId()).get();
        // Disconnect from session so that the updates on updatedArbeitszeiten are not directly saved in db
        em.detach(updatedArbeitszeiten);
        updatedArbeitszeiten
            .wochenstunden(UPDATED_WOCHENSTUNDEN);

        restArbeitszeitenMockMvc.perform(put("/api/arbeitszeitens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedArbeitszeiten)))
            .andExpect(status().isOk());

        // Validate the Arbeitszeiten in the database
        List<Arbeitszeiten> arbeitszeitenList = arbeitszeitenRepository.findAll();
        assertThat(arbeitszeitenList).hasSize(databaseSizeBeforeUpdate);
        Arbeitszeiten testArbeitszeiten = arbeitszeitenList.get(arbeitszeitenList.size() - 1);
        assertThat(testArbeitszeiten.getWochenstunden()).isEqualTo(UPDATED_WOCHENSTUNDEN);
    }

    @Test
    @Transactional
    public void updateNonExistingArbeitszeiten() throws Exception {
        int databaseSizeBeforeUpdate = arbeitszeitenRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArbeitszeitenMockMvc.perform(put("/api/arbeitszeitens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arbeitszeiten)))
            .andExpect(status().isBadRequest());

        // Validate the Arbeitszeiten in the database
        List<Arbeitszeiten> arbeitszeitenList = arbeitszeitenRepository.findAll();
        assertThat(arbeitszeitenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArbeitszeiten() throws Exception {
        // Initialize the database
        arbeitszeitenRepository.saveAndFlush(arbeitszeiten);

        int databaseSizeBeforeDelete = arbeitszeitenRepository.findAll().size();

        // Delete the arbeitszeiten
        restArbeitszeitenMockMvc.perform(delete("/api/arbeitszeitens/{id}", arbeitszeiten.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Arbeitszeiten> arbeitszeitenList = arbeitszeitenRepository.findAll();
        assertThat(arbeitszeitenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
