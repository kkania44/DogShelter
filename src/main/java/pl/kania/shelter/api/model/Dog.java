package pl.kania.shelter.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Dog {

    private Integer id;
    private String name;
    @Length(max = 1, message = "uzyj tylko jedej litery (F-female, M-male)")
    private String sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private float weight;
    @Setter
    private List<String> conditions;
    @Setter
    @Pattern(regexp = "\\d{2}[-]\\d{2}", message = "data musi spelniac format 'DD-MM'")
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
