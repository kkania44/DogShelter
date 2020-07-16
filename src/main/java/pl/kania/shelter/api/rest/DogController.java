package pl.kania.shelter.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kania.shelter.api.model.Dog;
import pl.kania.shelter.service.DogService;

import java.util.List;

@RestController
@RequestMapping("/dogs")
public class DogController {

    private DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addNewDog(@RequestBody Dog dog) {
        dogService.createDog(dog);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateDogData(@RequestBody Dog dog) {
        dogService.updateDog(dog);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public Dog getDogById(@PathVariable("id") Integer id) {
        return dogService.getById(id);
    }

    @GetMapping("/all")
    public List<Dog> getAllDogs() {
        return dogService.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(Integer id) {
        dogService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
