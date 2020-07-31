package pl.kania.shelter.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kania.shelter.api.model.Volunteer;
import pl.kania.shelter.service.VolunteerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/volunteers")
@PreAuthorize("isAuthenticated()")
public class VolunteerController {

    private VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping
    public ResponseEntity<Void> addNewVolunteer(@Valid @RequestBody Volunteer volunteer) {
        volunteerService.createVolunteer(volunteer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public Volunteer getById(@PathVariable("id") Integer id) {
        return volunteerService.getById(id);
    }

    @GetMapping
    public List<Volunteer> getAll() {
        return volunteerService.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        volunteerService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
