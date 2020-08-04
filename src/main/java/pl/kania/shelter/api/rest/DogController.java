package pl.kania.shelter.api.rest;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.kania.shelter.api.model.Dog;
import pl.kania.shelter.api.model.DogWithVolunteerName;
import pl.kania.shelter.service.DogService;

import javax.validation.Valid;
import java.beans.BeanInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dogs")
@PreAuthorize("isAuthenticated()")
public class DogController {

    private DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @PostMapping
    public ResponseEntity<?> addNewDog(@Valid @RequestBody Dog dog, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                errors.put(fieldName, error.getDefaultMessage());
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        dogService.createDog(dog);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateDogData(@RequestBody Dog dog) {
        dogService.updateDog(dog);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public Dog getDogById(@PathVariable("id") Integer id) {
        return dogService.getById(id);
    }

    @GetMapping
    public List<Dog> getAllDogs() {
        return dogService.getAll();
    }

    @GetMapping("/noVolunteer")
    public List<Dog> getAllWithoutVolunteer() {
        return dogService.getAllDogsWithoutVolunteer();
    }

    @GetMapping("/toVaccinate")
    public List<Dog> getAllDogsToBeVaccinatedThisMonth() {
        return dogService.getDogsMustBeVaccinated();
    }

    @GetMapping("/withVolunteers")
    public List<DogWithVolunteerName> getAllWithVolunteers() {
        return dogService.getAllThatHaveVolunteer();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        dogService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
