package pl.kania.shelter.api.rest;

import org.hamcrest.core.Is;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kania.shelter.domain.dog.DogRepository;
import pl.kania.shelter.service.DogService;
import pl.kania.shelter.test_samples.TestDogFactory;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser(roles = "USER")
class DogControllerTest {

    private final Logger log = Logger.getLogger(DogControllerTest.class.getName());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DogService dogService;

    @MockBean
    private DogRepository dogRepository;

    @Test
    public void shouldGetAllDogs() throws Exception {
        // given
        String expected = "[" +TestDogFactory.getDogObjectAsJson(1, "Max", null)+ ","
                +TestDogFactory.getDogObjectAsJson(2, "Mili", null)+ "]";
        // when
        when(dogService.getAll()).thenReturn(
                Arrays.asList(TestDogFactory.getDog(1, "Max", null),
                            TestDogFactory.getDog(2, "Mili", null)));
        MockHttpServletResponse response = getServletResponse("/dogs");
        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }

    @Test
    public void shouldGetDogById() throws Exception {
        //given
        String expectedJson = TestDogFactory.getDogObjectAsJson(1, "Tim", null);
        // when
        when(dogService.getById(1)).thenReturn(TestDogFactory.getDog(1, "Tim", null));
        MockHttpServletResponse response = getServletResponse("/dogs/1");
        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(expectedJson, response.getContentAsString(), true);
    }

    @Test void shouldNotGetWithWrongId() throws Exception {
        // given
        String expectedJson = "\"message\":\"Brak psa o podanym id\"";
        // when
        Mockito.when(dogRepository.findById(20)).thenReturn(Optional.empty());
        MockHttpServletResponse response = getServletResponse("/dogs/20");
        // then
        log.log(Level.INFO, String.valueOf(response.getStatus()));
        assertEquals(404, response.getStatus());
        String json = response.getContentAsString();
        assertTrue(json.contains(expectedJson));
    }

    @Test
    public void shouldAddNewDog() throws Exception {
        // given
        String newDog = TestDogFactory.getDogObjectAsJson(1, "Max", null);
        // when
        RequestBuilder builder = MockMvcRequestBuilders.post("/dogs", newDog);
        MvcResult result = mockMvc.perform(builder).andReturn();
        // then
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    @Test
    public void shouldNotAddDogWithInvalidData() throws Exception {
        // given
        String newDog = "{\"name\":\"Max\",\"sex\":\"Male\",\"birthDate\":\"2015-04-13\"," +
                "\"weight\":15.0,\"conditions\":[\"chicken allergy\"],\"rabiesVaccinationDate\":\"5-05\"}";
        // when
        RequestBuilder builder = MockMvcRequestBuilders.post("/dogs")
                .content(newDog)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(builder).andReturn();
        // then
        JSONAssert.assertEquals("{\"sex\":\"uzyj tylko jedej litery (F-female, M-male)\"," +
                        "\"rabiesVaccinationDate\":\"data musi spelniac format 'DD-MM'\"}",
                result.getResponse().getContentAsString(), false);
               }

    private MockHttpServletResponse getServletResponse(String url) throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.get(url);
        MvcResult result = mockMvc.perform(builder).andReturn();
        return result.getResponse();
    }

}