package ecf_spring.repository;

import ecf_spring.entity.Tournoi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournoiRepository extends CrudRepository<Tournoi,Integer> {
    boolean existsTournoiByTitle(String title);
}
