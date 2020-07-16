package pl.kania.shelter.service;

import org.springframework.stereotype.Service;
import pl.kania.shelter.api.model.Dog;
import pl.kania.shelter.domain.dog.DogEntity;
import pl.kania.shelter.domain.dog.DogRepository;
import pl.kania.shelter.domain.volunteer.VolunteerEntity;
import pl.kania.shelter.domain.volunteer.VolunteerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DogService {

    private DogRepository dogRepository;
    private VolunteerRepository volunteerRepository;

    public DogService(DogRepository dogRepository, VolunteerRepository volunteerRepository) {
        this.dogRepository = dogRepository;
        this.volunteerRepository = volunteerRepository;
    }

    public void createDog(Dog newDog) {
        DogEntity dogToAdd = new DogEntity(null, newDog.getName(), newDog.getSex(), newDog.getBirthDate(),
                newDog.getWeight(), String.join(",", newDog.getConditions()),
                newDog.getRabiesVaccinationDate());
        dogRepository.save(dogToAdd);
    }

    public void updateDog(Dog dog) {
        DogEntity dogToUpdate = dogRepository.getOne(dog.getId());
        VolunteerEntity volunteer = volunteerRepository.getOne(dog.getVolunteerId());
        dogToUpdate.setWeight(dog.getWeight());
        dogToUpdate.setRabiesVaccinationDate(dog.getRabiesVaccinationDate());
        dogToUpdate.setVolunteer(volunteer);
        dogRepository.save(dogToUpdate);
    }

    public Dog getById(Integer id) {
        DogEntity dogEntity = dogRepository.getOne(id);
        return mapToModel(dogEntity);
    }

    public List<Dog> getAll() {
        return dogRepository.findAll().stream()
                .map(dogEnt -> mapToModel(dogEnt))
                .collect(Collectors.toList());
    }

    public void deleteById(Integer id) {
        dogRepository.deleteById(id);
    }

    private Dog mapToModel(DogEntity dogEntity) {
        Dog dog = new Dog(dogEntity.getId(), dogEntity.getName(), dogEntity.getSex(), dogEntity.getBirthDate(),
                dogEntity.getWeight(), Arrays.asList(dogEntity.getConditions().split(",")),
                dogEntity.getRabiesVaccinationDate(), null);
        if (dogEntity.getVolunteer() != null) {
            dog.setVolunteerId(dogEntity.getVolunteer().getId());
        }
        return dog;
    }

}
