package pl.kania.shelter.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DogWithVolunteerName implements Serializable {

    private String dogName;
    private String volunteerFirstName;
    private String volunteerLastName;

}
