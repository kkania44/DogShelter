package pl.kania.shelter.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.pl.PESEL;
import pl.kania.shelter.domain.dog.DogEntity;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Volunteer {

    private Integer id;
    private String firstName;
    private String lastName;
    @PESEL
    private String pesel;
    private List<DogEntity> dogs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return Objects.equals(id, volunteer.id) &&
                Objects.equals(firstName, volunteer.firstName) &&
                Objects.equals(lastName, volunteer.lastName) &&
                Objects.equals(pesel, volunteer.pesel) &&
                Objects.equals(dogs, volunteer.dogs);
    }

    @Override
    public int hashCode() {
        return 7*firstName.hashCode() + 7*pesel.hashCode();
    }
}
