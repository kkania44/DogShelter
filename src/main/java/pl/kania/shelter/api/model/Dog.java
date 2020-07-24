package pl.kania.shelter.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

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

}
