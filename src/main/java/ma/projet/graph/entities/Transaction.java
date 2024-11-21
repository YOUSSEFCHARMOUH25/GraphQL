package ma.projet.graph.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data 
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double montant;
    
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private TypeTransaction type;

    @ManyToOne
    @JoinColumn(name = "compte_id", nullable = false)
    private Compte compte;
}
