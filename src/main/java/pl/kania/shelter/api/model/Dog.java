package pl.kania.shelter.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class Dog {

    private Integer id;
    private String name;
    private String sex;
    private LocalDate birthDate;
    private float weight;
    @Setter
    private List<String> conditions;
    @Setter
    private String rabiesVaccinationDate;
    @Setter
    private Integer volunteerId;

}
