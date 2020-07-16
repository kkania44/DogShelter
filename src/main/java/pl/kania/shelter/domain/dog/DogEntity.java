package pl.kania.shelter.domain.dog;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.kania.shelter.domain.volunteer.VolunteerEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "dogs")
public class DogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dg_id")
    private Integer id;
    @Column(name = "dg_name", length = 12, nullable = false)
    private String name;
    @Column(name = "dg_sex",length = 1, nullable = false)
    private String sex;
    @Column(name = "dg_birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @Column(name = "dg_weight", nullable = false)
    @Setter
    private float weight;
    @Column(name = "dg_conditions")
    private String conditions;
    @Column(name = "dg_rabies_vacc_date")
    @Setter
    private String rabiesVaccinationDate;
    @Setter
    @ManyToOne
    @JoinColumn(name = "vr_id")
    private VolunteerEntity volunteer;

    public DogEntity(Integer id, String name, String sex, LocalDate birthDate,
                     float weight, String conditions, String rabiesVaccinationDate) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birthDate = birthDate;
        this.weight = weight;
        this.conditions = conditions;
        this.rabiesVaccinationDate = rabiesVaccinationDate;
    }

}
