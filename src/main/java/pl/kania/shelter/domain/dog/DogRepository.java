package pl.kania.shelter.domain.dog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DogRepository extends JpaRepository<DogEntity, Integer> {

    List<DogEntity> findAllByVolunteerNotNull();

    List<DogEntity> findAllByVolunteerNull();

    List<DogEntity> findAllByRabiesVaccinationDateLike(String month);

}
