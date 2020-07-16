package pl.kania.shelter.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
