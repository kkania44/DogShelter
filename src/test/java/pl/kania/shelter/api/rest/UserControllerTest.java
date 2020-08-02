package pl.kania.shelter.api.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.kania.shelter.api.model.User;
import pl.kania.shelter.domain.user.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldRegisterNewUser() {
        // given
        User user = new User(null, "user", "pass");
        HttpEntity<User> httpEntity = new HttpEntity<>(user);
        // when
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/register", HttpMethod.POST, httpEntity, Void.class);
        // then
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals(1, userRepo.count());
    }
}