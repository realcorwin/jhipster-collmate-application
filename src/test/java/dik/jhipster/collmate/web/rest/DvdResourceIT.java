package dik.jhipster.collmate.web.rest;

import dik.jhipster.collmate.CollmateApp;
import dik.jhipster.collmate.domain.Dvd;
import dik.jhipster.collmate.repository.DvdRepository;
import dik.jhipster.collmate.service.DvdService;
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
 * Integration tests for the {@Link DvdResource} REST controller.
 */
@SpringBootTest(classes = CollmateApp.class)
public class DvdResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RELEASE_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_RELEASE_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_DISK_COUNT = "AAAAAAAAAA";
    private static final String UPDATED_DISK_COUNT = "BBBBBBBBBB";

    private static final String DEFAULT_FORMAT = "AAAAAAAAAA";
    private static final String UPDATED_FORMAT = "BBBBBBBBBB";

    private static final String DEFAULT_LANG = "AAAAAAAAAA";
    private static final String UPDATED_LANG = "BBBBBBBBBB";

    private static final State DEFAULT_STATE = State.OK;
    private static final State UPDATED_STATE = State.AWAY;

    private static final Instant DEFAULT_ADDED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ADDED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DvdRepository dvdRepository;

    @Autowired
    private DvdService dvdService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restDvdMockMvc;

    private Dvd dvd;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DvdResource dvdResource = new DvdResource(dvdService);
        this.restDvdMockMvc = MockMvcBuilders.standaloneSetup(dvdResource)
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
    public static Dvd createEntity() {
        Dvd dvd = new Dvd()
            .name(DEFAULT_NAME)
            .releaseYear(DEFAULT_RELEASE_YEAR)
            .diskCount(DEFAULT_DISK_COUNT)
            .format(DEFAULT_FORMAT)
            .lang(DEFAULT_LANG)
            .state(DEFAULT_STATE)
            .added(DEFAULT_ADDED);
        return dvd;
    }

    @BeforeEach
    public void initTest() {
        dvdRepository.deleteAll();
        dvd = createEntity();
    }

    @Test
    public void createDvd() throws Exception {
        int databaseSizeBeforeCreate = dvdRepository.findAll().size();

        // Create the Dvd
        restDvdMockMvc.perform(post("/api/dvds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dvd)))
            .andExpect(status().isCreated());

        // Validate the Dvd in the database
        List<Dvd> dvdList = dvdRepository.findAll();
        assertThat(dvdList).hasSize(databaseSizeBeforeCreate + 1);
        Dvd testDvd = dvdList.get(dvdList.size() - 1);
        assertThat(testDvd.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDvd.getReleaseYear()).isEqualTo(DEFAULT_RELEASE_YEAR);
        assertThat(testDvd.getDiskCount()).isEqualTo(DEFAULT_DISK_COUNT);
        assertThat(testDvd.getFormat()).isEqualTo(DEFAULT_FORMAT);
        assertThat(testDvd.getLang()).isEqualTo(DEFAULT_LANG);
        assertThat(testDvd.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testDvd.getAdded()).isEqualTo(DEFAULT_ADDED);
    }

    @Test
    public void createDvdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dvdRepository.findAll().size();

        // Create the Dvd with an existing ID
        dvd.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restDvdMockMvc.perform(post("/api/dvds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dvd)))
            .andExpect(status().isBadRequest());

        // Validate the Dvd in the database
        List<Dvd> dvdList = dvdRepository.findAll();
        assertThat(dvdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dvdRepository.findAll().size();
        // set the field null
        dvd.setName(null);

        // Create the Dvd, which fails.

        restDvdMockMvc.perform(post("/api/dvds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dvd)))
            .andExpect(status().isBadRequest());

        List<Dvd> dvdList = dvdRepository.findAll();
        assertThat(dvdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDvds() throws Exception {
        // Initialize the database
        dvdRepository.save(dvd);

        // Get all the dvdList
        restDvdMockMvc.perform(get("/api/dvds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dvd.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].releaseYear").value(hasItem(DEFAULT_RELEASE_YEAR.toString())))
            .andExpect(jsonPath("$.[*].diskCount").value(hasItem(DEFAULT_DISK_COUNT.toString())))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].lang").value(hasItem(DEFAULT_LANG.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].added").value(hasItem(DEFAULT_ADDED.toString())));
    }
    
    @Test
    public void getDvd() throws Exception {
        // Initialize the database
        dvdRepository.save(dvd);

        // Get the dvd
        restDvdMockMvc.perform(get("/api/dvds/{id}", dvd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dvd.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.releaseYear").value(DEFAULT_RELEASE_YEAR.toString()))
            .andExpect(jsonPath("$.diskCount").value(DEFAULT_DISK_COUNT.toString()))
            .andExpect(jsonPath("$.format").value(DEFAULT_FORMAT.toString()))
            .andExpect(jsonPath("$.lang").value(DEFAULT_LANG.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.added").value(DEFAULT_ADDED.toString()));
    }

    @Test
    public void getNonExistingDvd() throws Exception {
        // Get the dvd
        restDvdMockMvc.perform(get("/api/dvds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDvd() throws Exception {
        // Initialize the database
        dvdService.save(dvd);

        int databaseSizeBeforeUpdate = dvdRepository.findAll().size();

        // Update the dvd
        Dvd updatedDvd = dvdRepository.findById(dvd.getId()).get();
        updatedDvd
            .name(UPDATED_NAME)
            .releaseYear(UPDATED_RELEASE_YEAR)
            .diskCount(UPDATED_DISK_COUNT)
            .format(UPDATED_FORMAT)
            .lang(UPDATED_LANG)
            .state(UPDATED_STATE)
            .added(UPDATED_ADDED);

        restDvdMockMvc.perform(put("/api/dvds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDvd)))
            .andExpect(status().isOk());

        // Validate the Dvd in the database
        List<Dvd> dvdList = dvdRepository.findAll();
        assertThat(dvdList).hasSize(databaseSizeBeforeUpdate);
        Dvd testDvd = dvdList.get(dvdList.size() - 1);
        assertThat(testDvd.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDvd.getReleaseYear()).isEqualTo(UPDATED_RELEASE_YEAR);
        assertThat(testDvd.getDiskCount()).isEqualTo(UPDATED_DISK_COUNT);
        assertThat(testDvd.getFormat()).isEqualTo(UPDATED_FORMAT);
        assertThat(testDvd.getLang()).isEqualTo(UPDATED_LANG);
        assertThat(testDvd.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testDvd.getAdded()).isEqualTo(UPDATED_ADDED);
    }

    @Test
    public void updateNonExistingDvd() throws Exception {
        int databaseSizeBeforeUpdate = dvdRepository.findAll().size();

        // Create the Dvd

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDvdMockMvc.perform(put("/api/dvds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dvd)))
            .andExpect(status().isBadRequest());

        // Validate the Dvd in the database
        List<Dvd> dvdList = dvdRepository.findAll();
        assertThat(dvdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteDvd() throws Exception {
        // Initialize the database
        dvdService.save(dvd);

        int databaseSizeBeforeDelete = dvdRepository.findAll().size();

        // Delete the dvd
        restDvdMockMvc.perform(delete("/api/dvds/{id}", dvd.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Dvd> dvdList = dvdRepository.findAll();
        assertThat(dvdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dvd.class);
        Dvd dvd1 = new Dvd();
        dvd1.setId("id1");
        Dvd dvd2 = new Dvd();
        dvd2.setId(dvd1.getId());
        assertThat(dvd1).isEqualTo(dvd2);
        dvd2.setId("id2");
        assertThat(dvd1).isNotEqualTo(dvd2);
        dvd1.setId(null);
        assertThat(dvd1).isNotEqualTo(dvd2);
    }
}
