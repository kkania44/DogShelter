package pl.kania.shelter.api.rest;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.kania.shelter.api.model.Volunteer;
import pl.kania.shelter.domain.volunteer.VolunteerRepository;
import pl.kania.shelter.exceptions.ResourceNotFoundException;
import pl.kania.shelter.service.VolunteerService;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser(roles = "USER")
class VolunteerControllerTest {

//    @Autowired
//    private VolunteerRepository volunteerRepo;
//
//    @Autowired
//    private TestRestTemplate testRestTemplate;
//
//    @BeforeEach
//    public void before() {
//        volunteerRepo.deleteAll();
//    }
//
//    @Test
//    @WithMockUser(roles = "USER", username = "user")
//    public void shouldCreateVolunteer() {
//        //given
//        Volunteer volunteer = createSampleVolunteer(null, "881010123234");
//        HttpEntity<Volunteer> httpEntity = new HttpEntity<>(volunteer);
//        //when
//        ResponseEntity<Void> responseEnt = testRestTemplate.exchange("/volunteers", HttpMethod.POST, httpEntity, Void.class);
//        //then
//        System.out.println(responseEnt.getHeaders().toString());
//        assertEquals(201, responseEnt.getStatusCodeValue());
//        assertEquals(1, volunteerRepo.count());
//    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolunteerService volunteerService;

    @Test
    public void shouldGetVolunteerById() throws Exception {
        // given
        Volunteer volunteer = createSampleVolunteer(1, "971010123999");
        String expected = "{\"id\":1,\"firstName\":\"Marek\",\"lastName\":\"Marek\",\"pesel\":\"971010123999\"}";
        // when
        Mockito.when(volunteerService.getById(1)).thenReturn(volunteer);
        RequestBuilder requestBuilder = getRequestBuilderForGetMethod("/volunteers/1");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        // then
        assertEquals(200, mvcResult.getResponse().getStatus());
        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void shouldNotGetVolunteerWithNonExistentId() throws Exception {
        // given
        int expectedStatus = 404;
        // when
        Mockito.when(volunteerService.getById(102)).thenThrow(ResourceNotFoundException.class);
        RequestBuilder builder = getRequestBuilderForGetMethod("/volunteers/102");
        MvcResult result = mockMvc.perform(builder).andReturn();
        // then
        assertEquals(expectedStatus, result.getResponse().getStatus());
    }

    @Test
    public void shouldGetAllVolunteers() throws Exception {
        // given
        Volunteer volunteer1 = createSampleVolunteer(1, "861023123432");
        Volunteer volunteer2 = createSampleVolunteer(2, "451223137432");
        List<Volunteer> volunteersFromDB = new ArrayList<>();
        volunteersFromDB.add(volunteer1);
        volunteersFromDB.add(volunteer2);
        String expected = "[{\"id\":1,\"firstName\":\"Marek\",\"lastName\":\"Marek\",\"pesel\":\"861023123432\",\"dogs\":null}," +
                "{\"id\":2,\"firstName\":\"Marek\",\"lastName\":\"Marek\",\"pesel\":\"451223137432\",\"dogs\":null}]";
        // when
        Mockito.when(volunteerService.getAll()).thenReturn(volunteersFromDB);
        RequestBuilder builder = getRequestBuilderForGetMethod("/volunteers");
        MvcResult result = mockMvc.perform(builder).andReturn();
        final MockHttpServletResponse response = result.getResponse();
        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(expected, response.getContentAsString(), true);
    }

    @Test
    public void shouldAddNewVolunteer() throws Exception {
        //given
        String newVolunteerJson = "{\"firstName\":\"Marek\",\"lastName\":\"Marek\",\"pesel\":\"77050335484\"}";
        // when
        RequestBuilder builder = getRequestBuilderForPostMethod("/volunteers", newVolunteerJson);
        MvcResult result = mockMvc.perform(builder).andReturn();
        // then
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    @Test
    public void shouldNotAddNewVolunteerWithInvalidPesel() throws Exception {
        String newVolunteerJson = "{\"firstName\":\"Marek\",\"lastName\":\"Marek\",\"pesel\":\"77050322484\"}";
        // when
        RequestBuilder builder = getRequestBuilderForPostMethod("/volunteers", newVolunteerJson);
        MvcResult result = mockMvc.perform(builder).andReturn();
        // then
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        System.out.println(result.getResponse().getErrorMessage());
    }

    private RequestBuilder getRequestBuilderForGetMethod(String url) {
        return MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);
    }

    private RequestBuilder getRequestBuilderForPostMethod(String url, String jsonToPost) {
        return MockMvcRequestBuilders.post(url)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonToPost)
                .contentType(MediaType.APPLICATION_JSON);
    }

    private Volunteer createSampleVolunteer(Integer id, String pesel) {
        return new Volunteer(id, "Marek", "Marek", pesel, null);
    }

}