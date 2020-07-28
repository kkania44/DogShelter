package pl.kania.shelter.service;

import org.springframework.stereotype.Service;
import pl.kania.shelter.api.model.Volunteer;
import pl.kania.shelter.domain.dog.DogEntity;
import pl.kania.shelter.domain.dog.DogRepository;
import pl.kania.shelter.domain.volunteer.VolunteerEntity;
import pl.kania.shelter.domain.volunteer.VolunteerRepository;
import pl.kania.shelter.exceptions.ResourceAlreadyExistsException;
import pl.kania.shelter.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VolunteerService {

    static final String VOLUNTEER_NOT_FOUND_MESSAGE = "Brak wolontariusza o podanym id";

    private VolunteerRepository volunteerRepository;
    private DogRepository dogRepository;

    public VolunteerService(VolunteerRepository volunteerRepository, DogRepository dogRepository) {
        this.volunteerRepository = volunteerRepository;
        this.dogRepository = dogRepository;
    }

    public void createVolunteer(Volunteer newVolunteer) {
        Optional<VolunteerEntity> volunteerWithGivenPesel = volunteerRepository.findByPesel(newVolunteer.getPesel());
        if (volunteerWithGivenPesel.isPresent()) {
            throw new ResourceAlreadyExistsException("Volontariusz o podanym PESELU jest już w bazie");
        }

        VolunteerEntity volunteerToAdd = new VolunteerEntity(newVolunteer.getId(), newVolunteer.getFirstName(),
                newVolunteer.getLastName(), newVolunteer.getPesel(), newVolunteer.getDogs());
        volunteerRepository.save(volunteerToAdd);
    }

    public Volunteer getById(Integer id) {
        return volunteerRepository
                .findById(id)
                .map(this::mapToModel)
                .orElseThrow(() -> new ResourceNotFoundException(VOLUNTEER_NOT_FOUND_MESSAGE));
    }

    public List<Volunteer> getAll() {
        return volunteerRepository.findAll().stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    public void deleteById(Integer id) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .map(this::mapToModel)
                .orElseThrow(() -> new ResourceNotFoundException(VOLUNTEER_NOT_FOUND_MESSAGE));

        List<DogEntity> dogs = volunteer.getDogs();
        deleteVolunteerFodEachDog(dogs);
        volunteerRepository.deleteById(id);
    }

    void deleteVolunteerFodEachDog(List<DogEntity> dogs) {
        for (DogEntity dog : dogs) {
            dog.setVolunteer(null);
            dogRepository.save(dog);
        }
    }

    Volunteer mapToModel(VolunteerEntity volEntity) {
        return new Volunteer(volEntity.getId(), volEntity.getFirstName(), volEntity.getLastName(),
                volEntity.getPesel(), volEntity.getDogs());
    }

}
