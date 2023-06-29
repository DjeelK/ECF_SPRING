package ecf_spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="tournoi")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tournoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date dateDebut;
    private Date dateFin;
    @OneToMany(mappedBy = "tournoi",fetch = FetchType.EAGER)
    private List <Partie> parties;

}
