package ecf_spring.repository;

import ecf_spring.entity.Resultat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultatRepository extends CrudRepository<Resultat,Integer> {

}
