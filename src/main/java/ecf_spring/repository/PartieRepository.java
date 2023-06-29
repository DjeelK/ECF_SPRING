package ecf_spring.repository;

import ecf_spring.entity.AppUser;
import ecf_spring.entity.Partie;
import ecf_spring.entity.Tournoi;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PartieRepository extends CrudRepository<Partie,Integer> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Partie p WHERE p.tournoi = :tournoi AND p.appUser_1 = :appUser1 AND p.appUser_2 = :appUser2")
    boolean existsByTournoi(@Param("tournoi") Tournoi tournoi, @Param("appUser1") AppUser appUser1, @Param("appUser2") AppUser appUser2);
}
