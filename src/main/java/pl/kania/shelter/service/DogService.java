package pl.kania.shelter.service;

import org.springframework.stereotype.Service;
import pl.kania.shelter.api.model.Dog;
import pl.kania.shelter.api.model.DogWithVolunteerName;
import pl.kania.shelter.domain.dog.DogEntity;
import pl.kania.shelter.domain.dog.DogRepository;
import pl.kania.shelter.domain.volunteer.VolunteerEntity;
import pl.kania.shelter.domain.volunteer.VolunteerRepository;
import pl.kania.shelter.exceptions.ResourceNotFoundException;

import java.util.*;
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
        Optional<DogEntity> optionalDogToUpdate = dogRepository.findById(dog.getId());
        DogEntity dogToUpdate = optionalDogToUpdate
                .orElseThrow(() -> new ResourceNotFoundException("Brak psa o podanym id"));

        dogToUpdate.setWeight(dog.getWeight());
        dogToUpdate.setRabiesVaccinationDate(dog.getRabiesVaccinationDate());

        if(dog.getVolunteerId() != null) {
            VolunteerEntity volunteer = volunteerRepository
                    .findById(dog.getVolunteerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Wolontariusz o podanym id nie istnieje"));
            dogToUpdate.setVolunteer(volunteer);
        }
        dogRepository.save(dogToUpdate);
    }

    public Dog getById(Integer id) {
        Optional<DogEntity> dogEntity = dogRepository.findById(id);
        return dogEntity.map(this::mapToModel)
                .orElseThrow(() -> new ResourceNotFoundException("Brak psa o podanym id"));
    }

    public List<Dog> getAll() {
        return dogRepository.findAll().stream()
                .map(dogEnt -> mapToModel(dogEnt))
                .collect(Collectors.toList());
    }

    // return only dog's name, volunteer's first and last name
    public List<DogWithVolunteerName> getAllThatHaveVolunteer() {
        List<DogEntity> dogsWithVolunteer = dogRepository.findAllByVolunteerNotNull();
        return createListOfDogsWithVolunteers(dogsWithVolunteer);
    }

    private List<DogWithVolunteerName> createListOfDogsWithVolunteers(List<DogEntity> dogsWithVolunteer) {
        List<DogWithVolunteerName> dogsPlusVolunteers = new ArrayList<>();
        for (DogEntity dog : dogsWithVolunteer) {
            DogWithVolunteerName dogWithVolunteerName = new DogWithVolunteerName();

            dogWithVolunteerName.setDogName(dog.getName());
            VolunteerEntity volunteer = volunteerRepository.getOne(dog.getVolunteer().getId());
            dogWithVolunteerName.setVolunteerFirstName(volunteer.getFirstName());
            dogWithVolunteerName.setVolunteerLastName(volunteer.getLastName());

            dogsPlusVolunteers.add(dogWithVolunteerName);
        }
        return dogsPlusVolunteers;
    }

    public List<Dog> getAllDogsWithoutVolunteer() {
        return dogRepository.findAllByVolunteerNull().stream()
                .map(dog -> mapToModel(dog))
                .collect(Collectors.toList());
    }

    public List<Dog> getDogsMustBeVaccinated() {
        String currentMonthNumber = getCurrentMonthNumberAsString();
        return dogRepository.findAllByRabiesVaccinationDateLike("%-" + currentMonthNumber).stream()
                .map(dog -> mapToModel(dog))
                .collect(Collectors.toList());
    }

    private String getCurrentMonthNumberAsString() {
        Calendar calendar = Calendar.getInstance();
        Integer monthNumber = calendar.get(Calendar.MONTH) + 1;
        String monthNumberAsString = monthNumber.toString();
        if (monthNumber < 10) {
            monthNumberAsString = "0" + monthNumberAsString;
        }
        return monthNumberAsString;
    }

    public void deleteById(Integer id) {
        removeDogFromVolunteersList(id);
        dogRepository.deleteById(id);
    }

    private void removeDogFromVolunteersList(Integer id) {
        Optional<DogEntity> optionalDog = dogRepository.findById(id);
        DogEntity dog = optionalDog.orElseThrow(() -> new ResourceNotFoundException("Brak psa o podanym id"));

        VolunteerEntity volunteer = volunteerRepository.getOne(dog.getVolunteer().getId());
        volunteer.removeDogFromList(dog);
        volunteerRepository.save(volunteer);
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
