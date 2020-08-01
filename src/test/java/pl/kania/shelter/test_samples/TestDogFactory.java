package pl.kania.shelter.test_samples;

import pl.kania.shelter.api.model.Dog;
import pl.kania.shelter.api.model.Volunteer;
import pl.kania.shelter.domain.dog.DogEntity;
import pl.kania.shelter.domain.volunteer.VolunteerEntity;

import java.time.LocalDate;
import java.util.Collections;

public class TestDogFactory {

    public static Dog getDog(Integer id, String name, Integer volunteerId) {
        return new Dog(id, name, "M", LocalDate.parse("2015-04-13"), 15f,
                Collections.singletonList("chicken allergy"), "10-05", volunteerId);
    }

    public static DogEntity getDogEntity(Integer id, String name, VolunteerEntity volunteer) {
        return new DogEntity(id, name, "M", LocalDate.parse("2015-04-13"), 15f,
                "chicken allergy", "10-05", volunteer);
    }
}
