package pl.kania.shelter.service;

import org.springframework.stereotype.Service;
import pl.kania.shelter.api.model.Volunteer;
import pl.kania.shelter.domain.dog.DogEntity;
import pl.kania.shelter.domain.dog.DogRepository;
import pl.kania.shelter.domain.volunteer.VolunteerEntity;
import pl.kania.shelter.domain.volunteer.VolunteerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VolunteerService {

    private VolunteerRepository volunteerRepository;
    private DogRepository dogRepository;

    public VolunteerService(VolunteerRepository volunteerRepository, DogRepository dogRepository) {
        this.volunteerRepository = volunteerRepository;
        this.dogRepository = dogRepository;
    }

    public void createVolunteer(Volunteer newVolunteer) {
        VolunteerEntity volunteerToAdd = new VolunteerEntity(newVolunteer.getId(), newVolunteer.getFirstName(),
                newVolunteer.getLastName(), newVolunteer.getPesel(), newVolunteer.getDogs());
        volunteerRepository.save(volunteerToAdd);
    }

    public void addNewDogForVolunteer(Integer volunteerId, Integer dogId) {
        DogEntity dogToAdd = dogRepository.getOne(dogId);
        VolunteerEntity volunteerToUpdate = volunteerRepository.getOne(volunteerId);
        volunteerToUpdate.addDogToList(dogToAdd);
        volunteerRepository.save(volunteerToUpdate);
    }

    public Volunteer getById(Integer id) {
        VolunteerEntity volEntity = volunteerRepository.getOne(id);
        return mapToModel(volEntity);
    }

    public List<Volunteer> getAll() {
        return volunteerRepository.findAll().stream()
                .map(volEnt -> mapToModel(volEnt))
                .collect(Collectors.toList());
    }

    public void deleteById(Integer id) {
        volunteerRepository.deleteById(id);
    }

    private Volunteer mapToModel(VolunteerEntity volEntity) {
        Volunteer volunteer =  new Volunteer(volEntity.getId(), volEntity.getFirstName(), volEntity.getLastName(),
                volEntity.getPesel(), volEntity.getDogs());
//        if (volEntity.getDogs() != null) {
//            volunteer.setDogs(volEntity.getDogs());
//        }
        return volunteer;
    }

 }
