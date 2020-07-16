package pl.kania.shelter.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kania.shelter.api.model.Volunteer;
import pl.kania.shelter.service.VolunteerService;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController {

    private VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addNewVolunteer(@RequestBody Volunteer volunteer) {
        volunteerService.createVolunteer(volunteer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<Void> assignDogToVolunteer(@PathVariable("id") Integer volId, @RequestParam("dog_id") Integer dogId) {
        volunteerService.addNewDogForVolunteer(volId, dogId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public Volunteer getById(@PathVariable("id") Integer id) {
        return volunteerService.getById(id);
    }

}
