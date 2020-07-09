package pl.kania.shelter.domain;

import lombok.*;
import pl.kania.shelter.domain.DogEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class VolunteerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vr_id")
    private Integer id;
    @Column(name = "vr_first_name", length = 32, nullable = false)
    private String firstName;
    @Column(name = "vr_last_name", length = 32, nullable = false)
    private String lastName;
    @Column(name = "vr_pesel", length = 11, nullable = false, unique = true)
    private String pesel;
    @Setter
    @Column(name = "vr_dogs")
    @OneToMany(mappedBy = "volunteer")
    private List<DogEntity> dogs;

}
