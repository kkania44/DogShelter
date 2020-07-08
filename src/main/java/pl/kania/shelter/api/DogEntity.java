package pl.kania.shelter.api;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class DogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 12, nullable = false)
    private String name;
    @Column(length = 1, nullable = false)
    private String sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @Column(nullable = false)
    @Setter
    private float weight;
    @Setter
    private String rabiesVaccinationDate;
    @Setter
    @ManyToOne
    @JoinColumn(name = "vr_id")
    private VolunteerEntity volunteer;

}
