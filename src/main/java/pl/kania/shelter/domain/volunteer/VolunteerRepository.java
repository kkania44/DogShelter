package pl.kania.shelter.domain.volunteer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<VolunteerEntity, Integer> {

    Optional<VolunteerEntity> findByPesel(String pesel);

}
