package ecf_spring.repository;

import ecf_spring.entity.Partie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartieRepository extends CrudRepository<Partie,Integer> {

}
