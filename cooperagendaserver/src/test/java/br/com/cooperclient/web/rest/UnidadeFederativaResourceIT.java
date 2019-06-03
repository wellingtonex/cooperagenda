package br.com.cooperclient.web.rest;

import br.com.cooperclient.CooperagendaserverApp;
import br.com.cooperclient.domain.UnidadeFederativa;
import br.com.cooperclient.repository.UnidadeFederativaRepository;
import br.com.cooperclient.service.UnidadeFederativaService;
import br.com.cooperclient.service.dto.UnidadeFederativaDTO;
import br.com.cooperclient.service.mapper.UnidadeFederativaMapper;
import br.com.cooperclient.web.rest.errors.ExceptionTranslator;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static br.com.cooperclient.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link UnidadeFederativaResource} REST controller.
 */
@SpringBootTest(classes = CooperagendaserverApp.class)
public class UnidadeFederativaResourceIT {

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private UnidadeFederativaRepository unidadeFederativaRepository;

    @Autowired
    private UnidadeFederativaMapper unidadeFederativaMapper;

    @Autowired
    private UnidadeFederativaService unidadeFederativaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restUnidadeFederativaMockMvc;

    private UnidadeFederativa unidadeFederativa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnidadeFederativaResource unidadeFederativaResource = new UnidadeFederativaResource(unidadeFederativaService);
        this.restUnidadeFederativaMockMvc = MockMvcBuilders.standaloneSetup(unidadeFederativaResource)
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
    public static UnidadeFederativa createEntity(EntityManager em) {
        UnidadeFederativa unidadeFederativa = new UnidadeFederativa()
            .sigla(DEFAULT_SIGLA)
            .nome(DEFAULT_NOME);
        return unidadeFederativa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadeFederativa createUpdatedEntity(EntityManager em) {
        UnidadeFederativa unidadeFederativa = new UnidadeFederativa()
            .sigla(UPDATED_SIGLA)
            .nome(UPDATED_NOME);
        return unidadeFederativa;
    }

    @BeforeEach
    public void initTest() {
        unidadeFederativa = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidadeFederativa() throws Exception {
        int databaseSizeBeforeCreate = unidadeFederativaRepository.findAll().size();

        // Create the UnidadeFederativa
        UnidadeFederativaDTO unidadeFederativaDTO = unidadeFederativaMapper.toDto(unidadeFederativa);
        restUnidadeFederativaMockMvc.perform(post("/api/unidade-federativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeFederativaDTO)))
            .andExpect(status().isCreated());

        // Validate the UnidadeFederativa in the database
        List<UnidadeFederativa> unidadeFederativaList = unidadeFederativaRepository.findAll();
        assertThat(unidadeFederativaList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadeFederativa testUnidadeFederativa = unidadeFederativaList.get(unidadeFederativaList.size() - 1);
        assertThat(testUnidadeFederativa.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testUnidadeFederativa.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createUnidadeFederativaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadeFederativaRepository.findAll().size();

        // Create the UnidadeFederativa with an existing ID
        unidadeFederativa.setId(1L);
        UnidadeFederativaDTO unidadeFederativaDTO = unidadeFederativaMapper.toDto(unidadeFederativa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadeFederativaMockMvc.perform(post("/api/unidade-federativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeFederativaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeFederativa in the database
        List<UnidadeFederativa> unidadeFederativaList = unidadeFederativaRepository.findAll();
        assertThat(unidadeFederativaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeFederativaRepository.findAll().size();
        // set the field null
        unidadeFederativa.setSigla(null);

        // Create the UnidadeFederativa, which fails.
        UnidadeFederativaDTO unidadeFederativaDTO = unidadeFederativaMapper.toDto(unidadeFederativa);

        restUnidadeFederativaMockMvc.perform(post("/api/unidade-federativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeFederativaDTO)))
            .andExpect(status().isBadRequest());

        List<UnidadeFederativa> unidadeFederativaList = unidadeFederativaRepository.findAll();
        assertThat(unidadeFederativaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeFederativaRepository.findAll().size();
        // set the field null
        unidadeFederativa.setNome(null);

        // Create the UnidadeFederativa, which fails.
        UnidadeFederativaDTO unidadeFederativaDTO = unidadeFederativaMapper.toDto(unidadeFederativa);

        restUnidadeFederativaMockMvc.perform(post("/api/unidade-federativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeFederativaDTO)))
            .andExpect(status().isBadRequest());

        List<UnidadeFederativa> unidadeFederativaList = unidadeFederativaRepository.findAll();
        assertThat(unidadeFederativaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnidadeFederativas() throws Exception {
        // Initialize the database
        unidadeFederativaRepository.saveAndFlush(unidadeFederativa);

        // Get all the unidadeFederativaList
        restUnidadeFederativaMockMvc.perform(get("/api/unidade-federativas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeFederativa.getId().intValue())))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }
    
    @Test
    @Transactional
    public void getUnidadeFederativa() throws Exception {
        // Initialize the database
        unidadeFederativaRepository.saveAndFlush(unidadeFederativa);

        // Get the unidadeFederativa
        restUnidadeFederativaMockMvc.perform(get("/api/unidade-federativas/{id}", unidadeFederativa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unidadeFederativa.getId().intValue()))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUnidadeFederativa() throws Exception {
        // Get the unidadeFederativa
        restUnidadeFederativaMockMvc.perform(get("/api/unidade-federativas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidadeFederativa() throws Exception {
        // Initialize the database
        unidadeFederativaRepository.saveAndFlush(unidadeFederativa);

        int databaseSizeBeforeUpdate = unidadeFederativaRepository.findAll().size();

        // Update the unidadeFederativa
        UnidadeFederativa updatedUnidadeFederativa = unidadeFederativaRepository.findById(unidadeFederativa.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadeFederativa are not directly saved in db
        em.detach(updatedUnidadeFederativa);
        updatedUnidadeFederativa
            .sigla(UPDATED_SIGLA)
            .nome(UPDATED_NOME);
        UnidadeFederativaDTO unidadeFederativaDTO = unidadeFederativaMapper.toDto(updatedUnidadeFederativa);

        restUnidadeFederativaMockMvc.perform(put("/api/unidade-federativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeFederativaDTO)))
            .andExpect(status().isOk());

        // Validate the UnidadeFederativa in the database
        List<UnidadeFederativa> unidadeFederativaList = unidadeFederativaRepository.findAll();
        assertThat(unidadeFederativaList).hasSize(databaseSizeBeforeUpdate);
        UnidadeFederativa testUnidadeFederativa = unidadeFederativaList.get(unidadeFederativaList.size() - 1);
        assertThat(testUnidadeFederativa.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testUnidadeFederativa.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidadeFederativa() throws Exception {
        int databaseSizeBeforeUpdate = unidadeFederativaRepository.findAll().size();

        // Create the UnidadeFederativa
        UnidadeFederativaDTO unidadeFederativaDTO = unidadeFederativaMapper.toDto(unidadeFederativa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadeFederativaMockMvc.perform(put("/api/unidade-federativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeFederativaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeFederativa in the database
        List<UnidadeFederativa> unidadeFederativaList = unidadeFederativaRepository.findAll();
        assertThat(unidadeFederativaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnidadeFederativa() throws Exception {
        // Initialize the database
        unidadeFederativaRepository.saveAndFlush(unidadeFederativa);

        int databaseSizeBeforeDelete = unidadeFederativaRepository.findAll().size();

        // Delete the unidadeFederativa
        restUnidadeFederativaMockMvc.perform(delete("/api/unidade-federativas/{id}", unidadeFederativa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<UnidadeFederativa> unidadeFederativaList = unidadeFederativaRepository.findAll();
        assertThat(unidadeFederativaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeFederativa.class);
        UnidadeFederativa unidadeFederativa1 = new UnidadeFederativa();
        unidadeFederativa1.setId(1L);
        UnidadeFederativa unidadeFederativa2 = new UnidadeFederativa();
        unidadeFederativa2.setId(unidadeFederativa1.getId());
        assertThat(unidadeFederativa1).isEqualTo(unidadeFederativa2);
        unidadeFederativa2.setId(2L);
        assertThat(unidadeFederativa1).isNotEqualTo(unidadeFederativa2);
        unidadeFederativa1.setId(null);
        assertThat(unidadeFederativa1).isNotEqualTo(unidadeFederativa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeFederativaDTO.class);
        UnidadeFederativaDTO unidadeFederativaDTO1 = new UnidadeFederativaDTO();
        unidadeFederativaDTO1.setId(1L);
        UnidadeFederativaDTO unidadeFederativaDTO2 = new UnidadeFederativaDTO();
        assertThat(unidadeFederativaDTO1).isNotEqualTo(unidadeFederativaDTO2);
        unidadeFederativaDTO2.setId(unidadeFederativaDTO1.getId());
        assertThat(unidadeFederativaDTO1).isEqualTo(unidadeFederativaDTO2);
        unidadeFederativaDTO2.setId(2L);
        assertThat(unidadeFederativaDTO1).isNotEqualTo(unidadeFederativaDTO2);
        unidadeFederativaDTO1.setId(null);
        assertThat(unidadeFederativaDTO1).isNotEqualTo(unidadeFederativaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(unidadeFederativaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(unidadeFederativaMapper.fromId(null)).isNull();
    }
}
