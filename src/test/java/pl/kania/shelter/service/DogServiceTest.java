package pl.kania.shelter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import pl.kania.shelter.api.model.Dog;
import pl.kania.shelter.domain.dog.DogEntity;
import pl.kania.shelter.domain.dog.DogRepository;
import pl.kania.shelter.domain.volunteer.VolunteerEntity;
import pl.kania.shelter.domain.volunteer.VolunteerRepository;
import pl.kania.shelter.exceptions.ResourceNotFoundException;
import pl.kania.shelter.test_samples.TestDogFactory;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DogServiceTest {

    private DogRepository dogRepo;
    private VolunteerRepository volunteerRepo;
    private DogService dogService;

    @BeforeEach
    public void setup() {
        dogRepo = Mockito.mock(DogRepository.class);
        volunteerRepo = Mockito.mock(VolunteerRepository.class);
        dogService = new DogService(dogRepo, volunteerRepo);
    }

    @Test
    public void shouldGetDogById() {
        // given
        DogEntity dogEntity = new DogEntity(1, "Rex", "M", LocalDate.parse("2012-01-14"),
                21f, "rice allergy", "21-05");
        Dog dog = new Dog(1, "Rex", "M", LocalDate.parse("2012-01-14"),
                21f, Collections.singletonList("rice allergy"), "21-05", null);
        // when
        Mockito.when(dogRepo.findById(1)).thenReturn(Optional.of(dogEntity));
        Dog actualDog = dogService.getById(1);
        // then
        assertEquals(dog, actualDog);
    }

    @Test
    public void shouldNotGetDogByInvalidId() {
        // when
        Mockito.when(dogRepo.findById(90)).thenReturn(Optional.empty());
        Executable executable = () -> dogService.getById(90);
        // then
        assertThrows(ResourceNotFoundException.class, executable);
    }

    @Test
    public void shouldCreateNewDog() {
        // given
        Dog dog = TestDogFactory.getDog(null, "Max", null);
        // when
        dogService.createDog(dog);
        // then
        Mockito.verify(dogRepo, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void shouldUpdateAllChangeableFields() {
        // given
        Dog updateDog = TestDogFactory.getDog(1, "Tom", 1);
        DogEntity dogFromDB = TestDogFactory.getDogEntity(1, "Tom", null);
        // when
        Mockito.when(dogRepo.findById(1)).thenReturn(Optional.of(dogFromDB));
        Mockito.when(volunteerRepo.findById(1)).thenReturn(Optional.of(new VolunteerEntity()));
        dogService.updateDog(updateDog);
        // then
        Mockito.verify(dogRepo).save(dogFromDB);
    }

    @Test
    public void shouldNotUpdateWhenVolunteerDoesNotExist() {
        // given
        Dog updateDog = TestDogFactory.getDog(1, "Tom", 20);
        DogEntity dogFromDB = TestDogFactory.getDogEntity(1, "Tom", null);
        // when
        Mockito.when(dogRepo.findById(1)).thenReturn(Optional.of(dogFromDB));
        Mockito.when(volunteerRepo.findById(20)).thenReturn(Optional.empty());
        Executable executable = () -> dogService.updateDog(updateDog);
        // then
        assertThrows(ResourceNotFoundException.class, executable);
        Mockito.verify(dogRepo, Mockito.never()).save(Mockito.any());
    }


}