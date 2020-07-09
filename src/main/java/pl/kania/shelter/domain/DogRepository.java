package pl.kania.shelter.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<DogEntity, Integer> {

}
