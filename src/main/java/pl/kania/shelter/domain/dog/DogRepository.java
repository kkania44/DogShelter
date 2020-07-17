package pl.kania.shelter.domain.dog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kania.shelter.api.model.DogWithVolunteerName;

import java.util.List;

@Repository
public interface DogRepository extends JpaRepository<DogEntity, Integer> {

    @Query(value = "select new pl.kania.shelter.api.model.DogWithVolunteerName(dog.name, vol.firstName, vol.lastName) " +
            "from DogEntity dog inner join dog.volunteer vol")
    List<DogWithVolunteerName> findAllDogsWithAssignedVolunteers();

    List<DogEntity> findAllByVolunteerNotNull();

    List<DogEntity> findAllByVolunteerNull();

    List<DogEntity> findAllByRabiesVaccinationDateLike(String month);

}
