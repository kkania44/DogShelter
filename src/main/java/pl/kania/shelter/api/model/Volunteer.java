package pl.kania.shelter.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.kania.shelter.domain.dog.DogEntity;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Volunteer {

    private Integer id;
    private String firstName;
    private String lastName;
    private String pesel;
    private List<DogEntity> dogs;

    public Volunteer(Integer id, String firstName, String lastName, String pesel) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
    }
}
