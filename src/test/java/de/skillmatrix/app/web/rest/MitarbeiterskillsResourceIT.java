package de.skillmatrix.app.web.rest;

import de.skillmatrix.app.SkillmatrixApp;
import de.skillmatrix.app.domain.Mitarbeiterskills;
import de.skillmatrix.app.repository.MitarbeiterskillsRepository;

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
 * Integration tests for the {@link MitarbeiterskillsResource} REST controller.
 */
@SpringBootTest(classes = SkillmatrixApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MitarbeiterskillsResourceIT {

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    @Autowired
    private MitarbeiterskillsRepository mitarbeiterskillsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMitarbeiterskillsMockMvc;

    private Mitarbeiterskills mitarbeiterskills;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mitarbeiterskills createEntity(EntityManager em) {
        Mitarbeiterskills mitarbeiterskills = new Mitarbeiterskills()
            .level(DEFAULT_LEVEL);
        return mitarbeiterskills;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mitarbeiterskills createUpdatedEntity(EntityManager em) {
        Mitarbeiterskills mitarbeiterskills = new Mitarbeiterskills()
            .level(UPDATED_LEVEL);
        return mitarbeiterskills;
    }

    @BeforeEach
    public void initTest() {
        mitarbeiterskills = createEntity(em);
    }

    @Test
    @Transactional
    public void createMitarbeiterskills() throws Exception {
        int databaseSizeBeforeCreate = mitarbeiterskillsRepository.findAll().size();
        // Create the Mitarbeiterskills
        restMitarbeiterskillsMockMvc.perform(post("/api/mitarbeiterskills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mitarbeiterskills)))
            .andExpect(status().isCreated());

        // Validate the Mitarbeiterskills in the database
        List<Mitarbeiterskills> mitarbeiterskillsList = mitarbeiterskillsRepository.findAll();
        assertThat(mitarbeiterskillsList).hasSize(databaseSizeBeforeCreate + 1);
        Mitarbeiterskills testMitarbeiterskills = mitarbeiterskillsList.get(mitarbeiterskillsList.size() - 1);
        assertThat(testMitarbeiterskills.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createMitarbeiterskillsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mitarbeiterskillsRepository.findAll().size();

        // Create the Mitarbeiterskills with an existing ID
        mitarbeiterskills.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMitarbeiterskillsMockMvc.perform(post("/api/mitarbeiterskills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mitarbeiterskills)))
            .andExpect(status().isBadRequest());

        // Validate the Mitarbeiterskills in the database
        List<Mitarbeiterskills> mitarbeiterskillsList = mitarbeiterskillsRepository.findAll();
        assertThat(mitarbeiterskillsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = mitarbeiterskillsRepository.findAll().size();
        // set the field null
        mitarbeiterskills.setLevel(null);

        // Create the Mitarbeiterskills, which fails.


        restMitarbeiterskillsMockMvc.perform(post("/api/mitarbeiterskills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mitarbeiterskills)))
            .andExpect(status().isBadRequest());

        List<Mitarbeiterskills> mitarbeiterskillsList = mitarbeiterskillsRepository.findAll();
        assertThat(mitarbeiterskillsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMitarbeiterskills() throws Exception {
        // Initialize the database
        mitarbeiterskillsRepository.saveAndFlush(mitarbeiterskills);

        // Get all the mitarbeiterskillsList
        restMitarbeiterskillsMockMvc.perform(get("/api/mitarbeiterskills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mitarbeiterskills.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)));
    }
    
    @Test
    @Transactional
    public void getMitarbeiterskills() throws Exception {
        // Initialize the database
        mitarbeiterskillsRepository.saveAndFlush(mitarbeiterskills);

        // Get the mitarbeiterskills
        restMitarbeiterskillsMockMvc.perform(get("/api/mitarbeiterskills/{id}", mitarbeiterskills.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mitarbeiterskills.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL));
    }
    @Test
    @Transactional
    public void getNonExistingMitarbeiterskills() throws Exception {
        // Get the mitarbeiterskills
        restMitarbeiterskillsMockMvc.perform(get("/api/mitarbeiterskills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMitarbeiterskills() throws Exception {
        // Initialize the database
        mitarbeiterskillsRepository.saveAndFlush(mitarbeiterskills);

        int databaseSizeBeforeUpdate = mitarbeiterskillsRepository.findAll().size();

        // Update the mitarbeiterskills
        Mitarbeiterskills updatedMitarbeiterskills = mitarbeiterskillsRepository.findById(mitarbeiterskills.getId()).get();
        // Disconnect from session so that the updates on updatedMitarbeiterskills are not directly saved in db
        em.detach(updatedMitarbeiterskills);
        updatedMitarbeiterskills
            .level(UPDATED_LEVEL);

        restMitarbeiterskillsMockMvc.perform(put("/api/mitarbeiterskills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMitarbeiterskills)))
            .andExpect(status().isOk());

        // Validate the Mitarbeiterskills in the database
        List<Mitarbeiterskills> mitarbeiterskillsList = mitarbeiterskillsRepository.findAll();
        assertThat(mitarbeiterskillsList).hasSize(databaseSizeBeforeUpdate);
        Mitarbeiterskills testMitarbeiterskills = mitarbeiterskillsList.get(mitarbeiterskillsList.size() - 1);
        assertThat(testMitarbeiterskills.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingMitarbeiterskills() throws Exception {
        int databaseSizeBeforeUpdate = mitarbeiterskillsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMitarbeiterskillsMockMvc.perform(put("/api/mitarbeiterskills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mitarbeiterskills)))
            .andExpect(status().isBadRequest());

        // Validate the Mitarbeiterskills in the database
        List<Mitarbeiterskills> mitarbeiterskillsList = mitarbeiterskillsRepository.findAll();
        assertThat(mitarbeiterskillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMitarbeiterskills() throws Exception {
        // Initialize the database
        mitarbeiterskillsRepository.saveAndFlush(mitarbeiterskills);

        int databaseSizeBeforeDelete = mitarbeiterskillsRepository.findAll().size();

        // Delete the mitarbeiterskills
        restMitarbeiterskillsMockMvc.perform(delete("/api/mitarbeiterskills/{id}", mitarbeiterskills.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mitarbeiterskills> mitarbeiterskillsList = mitarbeiterskillsRepository.findAll();
        assertThat(mitarbeiterskillsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
