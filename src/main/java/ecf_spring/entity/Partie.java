package ecf_spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="partie")
public class Partie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Tournoi tournoi;
    @ManyToOne
    private AppUser appUser_1;
    @ManyToOne
    private AppUser appUser_2;
    @OneToOne
    private Resultat resultat;
    @ManyToOne
    private AppUser user;

}
