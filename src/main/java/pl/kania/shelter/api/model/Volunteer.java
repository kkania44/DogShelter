package pl.kania.shelter.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.kania.shelter.domain.dog.DogEntity;
import java.util.List;

@AllArgsConstructor
@Getter
public class Volunteer {

    private Integer id;
    private String firstName;
    private String lastName;
    private String pesel;
    @Setter
    private List<DogEntity> dogs;
}
