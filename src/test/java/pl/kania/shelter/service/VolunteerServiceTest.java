package pl.kania.shelter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import pl.kania.shelter.api.model.Volunteer;
import pl.kania.shelter.domain.dog.DogEntity;
import pl.kania.shelter.domain.dog.DogRepository;
import pl.kania.shelter.domain.volunteer.VolunteerEntity;
import pl.kania.shelter.domain.volunteer.VolunteerRepository;
import pl.kania.shelter.exceptions.ResourceAlreadyExistsException;
import pl.kania.shelter.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class VolunteerServiceTest {

    private VolunteerRepository volunteerRepo;
    private DogRepository dogRepo;
    private VolunteerService volunteerService;

    @BeforeEach
    public void setup() {
        volunteerRepo = Mockito.mock(VolunteerRepository.class);
        dogRepo = Mockito.mock(DogRepository.class);
        volunteerService = new VolunteerService(volunteerRepo, dogRepo);
    }

    @Test
    public void shouldAddNewVolunteer() {
        // given:
        Volunteer volunteer = new Volunteer(null, "Tadeusz", "Mazurek", "950120887321", null);
        // when:
        volunteerService.createVolunteer(volunteer);
        // then:
        Mockito.verify(volunteerRepo, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void shouldNotAddNewVolunteerIfPeselExists() {
        // given
        VolunteerEntity volunteerEntity1 = createSampleVolunteerEntity(null, "991010123123");
        Volunteer volunteer = createSampleVolunteer(null, "991010123123");
        // when
        Mockito.when(volunteerRepo.findByPesel(volunteerEntity1.getPesel()))
                .thenReturn(Optional.of(volunteerEntity1));
        Executable executable = () -> volunteerService.createVolunteer(volunteer);
        //then
        assertThrows(ResourceAlreadyExistsException.class, executable);
    }

    @Test
    public void shouldNotAddVolunteerWithInvalidPesel() {
        // given
        Volunteer volunteer = createSampleVolunteer(null, "9921211324");
        // when
        Mockito.when(volunteerRepo.findByPesel(volunteer.getPesel())).thenReturn(Optional.empty());
        Executable executable = () -> volunteerService.createVolunteer(volunteer);
        // then
    }

    @Test
    public void shouldGetVolunteerById() {
        // given:
        Volunteer volunteer = createSampleVolunteer(1, "980123223144");
        VolunteerEntity volunteerEntity = createSampleVolunteerEntity(1, "980123223144");
        // when:
        Mockito.when(volunteerRepo.findById(1)).thenReturn(Optional.of(volunteerEntity));
        // then:
        Volunteer actualVolunteer = volunteerService.getById(1);
        assertEquals(volunteer, actualVolunteer);
    }

    @Test
    public void shouldNotGetVolunteerWithInvalidId() {
        // given:

        //when
        Mockito.when(volunteerRepo.findById(10)).thenReturn(Optional.empty());
        Executable expectedException = () -> volunteerService.getById(10);
        // then
        assertThrows(ResourceNotFoundException.class, expectedException);
    }

    @Test
    public void shouldGetAllVolunteers() {
        // given
        List<Volunteer> expectedVolunteers = new ArrayList<>();
        expectedVolunteers.add(createSampleVolunteer(1, "990101123456"));
        expectedVolunteers.add(createSampleVolunteer(2, "880101121212"));
        List<VolunteerEntity> volunteersFromDB = new ArrayList<>();
        volunteersFromDB.add(createSampleVolunteerEntity(1, "990101123456"));
        volunteersFromDB.add(createSampleVolunteerEntity(2, "880101121212"));
        // when
        Mockito.when(volunteerRepo.findAll()).thenReturn(volunteersFromDB);
        // then
        assertEquals(expectedVolunteers, volunteerService.getAll());
    }

    @Test
    public void shouldDeleteVolunteerById() {
        // given
        VolunteerEntity volunteerEntity = new VolunteerEntity(
                10, "Marek", "Marek", "991010123345", Collections.singletonList(new DogEntity()));
        // when
        Mockito.when(volunteerRepo.findById(10)).thenReturn(Optional.of(volunteerEntity));
        volunteerService.deleteById(10);
        // then
        Mockito.verify(volunteerRepo, Mockito.times(1)).deleteById(10);
        Mockito.verify(dogRepo).save(Mockito.any());
    }

    @Test
    public void shouldNotDeleteVolunteerWhenIdDoesNotExists() {
        // when
        Mockito.when(volunteerRepo.findById(20)).thenReturn(Optional.empty());
        Executable executable = () -> volunteerService.deleteById(20);
        // then
        assertThrows(ResourceNotFoundException.class, executable);
    }

    private Volunteer createSampleVolunteer(Integer id, String pesel) {
        return new Volunteer(id, "Marek", "Marek", pesel, null);
    }

    private VolunteerEntity createSampleVolunteerEntity(Integer id, String pesel) {
        return new VolunteerEntity(id, "Marek", "Marek", pesel, null);
    }
}