package pl.kania.shelter.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Dog {

    private Integer id;
    private String name;
    private String sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private float weight;
    @Setter
    private List<String> conditions;
    @Setter
    private String rabiesVaccinationDate;
    @Setter
    private Integer volunteerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return Float.compare(dog.weight, weight) == 0 &&
                Objects.equals(id, dog.id) &&
                Objects.equals(name, dog.name) &&
                Objects.equals(sex, dog.sex) &&
                Objects.equals(birthDate, dog.birthDate) &&
                Objects.equals(conditions, dog.conditions) &&
                Objects.equals(rabiesVaccinationDate, dog.rabiesVaccinationDate) &&
                Objects.equals(volunteerId, dog.volunteerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight, rabiesVaccinationDate);
    }
}
