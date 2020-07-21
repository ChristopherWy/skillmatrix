package de.skillmatrix.app.web.rest;

import de.skillmatrix.app.SkillmatrixApp;
import de.skillmatrix.app.domain.Mitarbeiter;
import de.skillmatrix.app.repository.MitarbeiterRepository;

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
 * Integration tests for the {@link MitarbeiterResource} REST controller.
 */
@SpringBootTest(classes = SkillmatrixApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MitarbeiterResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_VORNAME = "AAAAAAAAAA";
    private static final String UPDATED_VORNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NACHNAME = "AAAAAAAAAA";
    private static final String UPDATED_NACHNAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INTERN = false;
    private static final Boolean UPDATED_INTERN = true;

    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMitarbeiterMockMvc;

    private Mitarbeiter mitarbeiter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mitarbeiter createEntity(EntityManager em) {
        Mitarbeiter mitarbeiter = new Mitarbeiter()
            .email(DEFAULT_EMAIL)
            .vorname(DEFAULT_VORNAME)
            .nachname(DEFAULT_NACHNAME)
            .intern(DEFAULT_INTERN);
        return mitarbeiter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mitarbeiter createUpdatedEntity(EntityManager em) {
        Mitarbeiter mitarbeiter = new Mitarbeiter()
            .email(UPDATED_EMAIL)
            .vorname(UPDATED_VORNAME)
            .nachname(UPDATED_NACHNAME)
            .intern(UPDATED_INTERN);
        return mitarbeiter;
    }

    @BeforeEach
    public void initTest() {
        mitarbeiter = createEntity(em);
    }

    @Test
    @Transactional
    public void createMitarbeiter() throws Exception {
        int databaseSizeBeforeCreate = mitarbeiterRepository.findAll().size();
        // Create the Mitarbeiter
        restMitarbeiterMockMvc.perform(post("/api/mitarbeiters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mitarbeiter)))
            .andExpect(status().isCreated());

        // Validate the Mitarbeiter in the database
        List<Mitarbeiter> mitarbeiterList = mitarbeiterRepository.findAll();
        assertThat(mitarbeiterList).hasSize(databaseSizeBeforeCreate + 1);
        Mitarbeiter testMitarbeiter = mitarbeiterList.get(mitarbeiterList.size() - 1);
        assertThat(testMitarbeiter.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMitarbeiter.getVorname()).isEqualTo(DEFAULT_VORNAME);
        assertThat(testMitarbeiter.getNachname()).isEqualTo(DEFAULT_NACHNAME);
        assertThat(testMitarbeiter.isIntern()).isEqualTo(DEFAULT_INTERN);
    }

    @Test
    @Transactional
    public void createMitarbeiterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mitarbeiterRepository.findAll().size();

        // Create the Mitarbeiter with an existing ID
        mitarbeiter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMitarbeiterMockMvc.perform(post("/api/mitarbeiters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mitarbeiter)))
            .andExpect(status().isBadRequest());

        // Validate the Mitarbeiter in the database
        List<Mitarbeiter> mitarbeiterList = mitarbeiterRepository.findAll();
        assertThat(mitarbeiterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = mitarbeiterRepository.findAll().size();
        // set the field null
        mitarbeiter.setEmail(null);

        // Create the Mitarbeiter, which fails.


        restMitarbeiterMockMvc.perform(post("/api/mitarbeiters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mitarbeiter)))
            .andExpect(status().isBadRequest());

        List<Mitarbeiter> mitarbeiterList = mitarbeiterRepository.findAll();
        assertThat(mitarbeiterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMitarbeiters() throws Exception {
        // Initialize the database
        mitarbeiterRepository.saveAndFlush(mitarbeiter);

        // Get all the mitarbeiterList
        restMitarbeiterMockMvc.perform(get("/api/mitarbeiters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mitarbeiter.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].vorname").value(hasItem(DEFAULT_VORNAME)))
            .andExpect(jsonPath("$.[*].nachname").value(hasItem(DEFAULT_NACHNAME)))
            .andExpect(jsonPath("$.[*].intern").value(hasItem(DEFAULT_INTERN.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMitarbeiter() throws Exception {
        // Initialize the database
        mitarbeiterRepository.saveAndFlush(mitarbeiter);

        // Get the mitarbeiter
        restMitarbeiterMockMvc.perform(get("/api/mitarbeiters/{id}", mitarbeiter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mitarbeiter.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.vorname").value(DEFAULT_VORNAME))
            .andExpect(jsonPath("$.nachname").value(DEFAULT_NACHNAME))
            .andExpect(jsonPath("$.intern").value(DEFAULT_INTERN.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingMitarbeiter() throws Exception {
        // Get the mitarbeiter
        restMitarbeiterMockMvc.perform(get("/api/mitarbeiters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMitarbeiter() throws Exception {
        // Initialize the database
        mitarbeiterRepository.saveAndFlush(mitarbeiter);

        int databaseSizeBeforeUpdate = mitarbeiterRepository.findAll().size();

        // Update the mitarbeiter
        Mitarbeiter updatedMitarbeiter = mitarbeiterRepository.findById(mitarbeiter.getId()).get();
        // Disconnect from session so that the updates on updatedMitarbeiter are not directly saved in db
        em.detach(updatedMitarbeiter);
        updatedMitarbeiter
            .email(UPDATED_EMAIL)
            .vorname(UPDATED_VORNAME)
            .nachname(UPDATED_NACHNAME)
            .intern(UPDATED_INTERN);

        restMitarbeiterMockMvc.perform(put("/api/mitarbeiters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMitarbeiter)))
            .andExpect(status().isOk());

        // Validate the Mitarbeiter in the database
        List<Mitarbeiter> mitarbeiterList = mitarbeiterRepository.findAll();
        assertThat(mitarbeiterList).hasSize(databaseSizeBeforeUpdate);
        Mitarbeiter testMitarbeiter = mitarbeiterList.get(mitarbeiterList.size() - 1);
        assertThat(testMitarbeiter.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMitarbeiter.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testMitarbeiter.getNachname()).isEqualTo(UPDATED_NACHNAME);
        assertThat(testMitarbeiter.isIntern()).isEqualTo(UPDATED_INTERN);
    }

    @Test
    @Transactional
    public void updateNonExistingMitarbeiter() throws Exception {
        int databaseSizeBeforeUpdate = mitarbeiterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMitarbeiterMockMvc.perform(put("/api/mitarbeiters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mitarbeiter)))
            .andExpect(status().isBadRequest());

        // Validate the Mitarbeiter in the database
        List<Mitarbeiter> mitarbeiterList = mitarbeiterRepository.findAll();
        assertThat(mitarbeiterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMitarbeiter() throws Exception {
        // Initialize the database
        mitarbeiterRepository.saveAndFlush(mitarbeiter);

        int databaseSizeBeforeDelete = mitarbeiterRepository.findAll().size();

        // Delete the mitarbeiter
        restMitarbeiterMockMvc.perform(delete("/api/mitarbeiters/{id}", mitarbeiter.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mitarbeiter> mitarbeiterList = mitarbeiterRepository.findAll();
        assertThat(mitarbeiterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
