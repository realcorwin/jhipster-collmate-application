package dik.jhipster.collmate.web.rest;

import dik.jhipster.collmate.CollmateApp;
import dik.jhipster.collmate.domain.Cd;
import dik.jhipster.collmate.repository.CdRepository;
import dik.jhipster.collmate.service.CdService;
import dik.jhipster.collmate.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static dik.jhipster.collmate.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dik.jhipster.collmate.domain.enumeration.State;
/**
 * Integration tests for the {@Link CdResource} REST controller.
 */
@SpringBootTest(classes = CollmateApp.class)
public class CdResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PERFORMER = "AAAAAAAAAA";
    private static final String UPDATED_PERFORMER = "BBBBBBBBBB";

    private static final String DEFAULT_RELEASE_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_RELEASE_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_DISK_COUNT = "AAAAAAAAAA";
    private static final String UPDATED_DISK_COUNT = "BBBBBBBBBB";

    private static final String DEFAULT_MEDIUM = "AAAAAAAAAA";
    private static final String UPDATED_MEDIUM = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final State DEFAULT_STATE = State.OK;
    private static final State UPDATED_STATE = State.AWAY;

    private static final Instant DEFAULT_ADDED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ADDED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CdRepository cdRepository;

    @Autowired
    private CdService cdService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCdMockMvc;

    private Cd cd;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CdResource cdResource = new CdResource(cdService);
        this.restCdMockMvc = MockMvcBuilders.standaloneSetup(cdResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cd createEntity() {
        Cd cd = new Cd()
            .name(DEFAULT_NAME)
            .performer(DEFAULT_PERFORMER)
            .releaseYear(DEFAULT_RELEASE_YEAR)
            .diskCount(DEFAULT_DISK_COUNT)
            .medium(DEFAULT_MEDIUM)
            .label(DEFAULT_LABEL)
            .state(DEFAULT_STATE)
            .added(DEFAULT_ADDED);
        return cd;
    }

    @BeforeEach
    public void initTest() {
        cdRepository.deleteAll();
        cd = createEntity();
    }

    @Test
    public void createCd() throws Exception {
        int databaseSizeBeforeCreate = cdRepository.findAll().size();

        // Create the Cd
        restCdMockMvc.perform(post("/api/cds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cd)))
            .andExpect(status().isCreated());

        // Validate the Cd in the database
        List<Cd> cdList = cdRepository.findAll();
        assertThat(cdList).hasSize(databaseSizeBeforeCreate + 1);
        Cd testCd = cdList.get(cdList.size() - 1);
        assertThat(testCd.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCd.getPerformer()).isEqualTo(DEFAULT_PERFORMER);
        assertThat(testCd.getReleaseYear()).isEqualTo(DEFAULT_RELEASE_YEAR);
        assertThat(testCd.getDiskCount()).isEqualTo(DEFAULT_DISK_COUNT);
        assertThat(testCd.getMedium()).isEqualTo(DEFAULT_MEDIUM);
        assertThat(testCd.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testCd.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCd.getAdded()).isEqualTo(DEFAULT_ADDED);
    }

    @Test
    public void createCdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cdRepository.findAll().size();

        // Create the Cd with an existing ID
        cd.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCdMockMvc.perform(post("/api/cds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cd)))
            .andExpect(status().isBadRequest());

        // Validate the Cd in the database
        List<Cd> cdList = cdRepository.findAll();
        assertThat(cdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cdRepository.findAll().size();
        // set the field null
        cd.setName(null);

        // Create the Cd, which fails.

        restCdMockMvc.perform(post("/api/cds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cd)))
            .andExpect(status().isBadRequest());

        List<Cd> cdList = cdRepository.findAll();
        assertThat(cdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllCds() throws Exception {
        // Initialize the database
        cdRepository.save(cd);

        // Get all the cdList
        restCdMockMvc.perform(get("/api/cds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cd.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].performer").value(hasItem(DEFAULT_PERFORMER.toString())))
            .andExpect(jsonPath("$.[*].releaseYear").value(hasItem(DEFAULT_RELEASE_YEAR.toString())))
            .andExpect(jsonPath("$.[*].diskCount").value(hasItem(DEFAULT_DISK_COUNT.toString())))
            .andExpect(jsonPath("$.[*].medium").value(hasItem(DEFAULT_MEDIUM.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].added").value(hasItem(DEFAULT_ADDED.toString())));
    }
    
    @Test
    public void getCd() throws Exception {
        // Initialize the database
        cdRepository.save(cd);

        // Get the cd
        restCdMockMvc.perform(get("/api/cds/{id}", cd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cd.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.performer").value(DEFAULT_PERFORMER.toString()))
            .andExpect(jsonPath("$.releaseYear").value(DEFAULT_RELEASE_YEAR.toString()))
            .andExpect(jsonPath("$.diskCount").value(DEFAULT_DISK_COUNT.toString()))
            .andExpect(jsonPath("$.medium").value(DEFAULT_MEDIUM.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.added").value(DEFAULT_ADDED.toString()));
    }

    @Test
    public void getNonExistingCd() throws Exception {
        // Get the cd
        restCdMockMvc.perform(get("/api/cds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCd() throws Exception {
        // Initialize the database
        cdService.save(cd);

        int databaseSizeBeforeUpdate = cdRepository.findAll().size();

        // Update the cd
        Cd updatedCd = cdRepository.findById(cd.getId()).get();
        updatedCd
            .name(UPDATED_NAME)
            .performer(UPDATED_PERFORMER)
            .releaseYear(UPDATED_RELEASE_YEAR)
            .diskCount(UPDATED_DISK_COUNT)
            .medium(UPDATED_MEDIUM)
            .label(UPDATED_LABEL)
            .state(UPDATED_STATE)
            .added(UPDATED_ADDED);

        restCdMockMvc.perform(put("/api/cds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCd)))
            .andExpect(status().isOk());

        // Validate the Cd in the database
        List<Cd> cdList = cdRepository.findAll();
        assertThat(cdList).hasSize(databaseSizeBeforeUpdate);
        Cd testCd = cdList.get(cdList.size() - 1);
        assertThat(testCd.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCd.getPerformer()).isEqualTo(UPDATED_PERFORMER);
        assertThat(testCd.getReleaseYear()).isEqualTo(UPDATED_RELEASE_YEAR);
        assertThat(testCd.getDiskCount()).isEqualTo(UPDATED_DISK_COUNT);
        assertThat(testCd.getMedium()).isEqualTo(UPDATED_MEDIUM);
        assertThat(testCd.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testCd.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCd.getAdded()).isEqualTo(UPDATED_ADDED);
    }

    @Test
    public void updateNonExistingCd() throws Exception {
        int databaseSizeBeforeUpdate = cdRepository.findAll().size();

        // Create the Cd

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCdMockMvc.perform(put("/api/cds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cd)))
            .andExpect(status().isBadRequest());

        // Validate the Cd in the database
        List<Cd> cdList = cdRepository.findAll();
        assertThat(cdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCd() throws Exception {
        // Initialize the database
        cdService.save(cd);

        int databaseSizeBeforeDelete = cdRepository.findAll().size();

        // Delete the cd
        restCdMockMvc.perform(delete("/api/cds/{id}", cd.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Cd> cdList = cdRepository.findAll();
        assertThat(cdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cd.class);
        Cd cd1 = new Cd();
        cd1.setId("id1");
        Cd cd2 = new Cd();
        cd2.setId(cd1.getId());
        assertThat(cd1).isEqualTo(cd2);
        cd2.setId("id2");
        assertThat(cd1).isNotEqualTo(cd2);
        cd1.setId(null);
        assertThat(cd1).isNotEqualTo(cd2);
    }
}
