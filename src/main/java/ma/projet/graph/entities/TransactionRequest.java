package ma.projet.graph.entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionRequest {
    private Double montant;
    private LocalDate date;
    private TypeTransaction type;
    private Long compteId; // ID du compte associé
}