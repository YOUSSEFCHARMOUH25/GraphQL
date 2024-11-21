package ma.projet.graph.controllers;



import lombok.AllArgsConstructor;
import ma.projet.graph.entities.Transaction;
import ma.projet.graph.entities.TransactionRequest;
import ma.projet.graph.entities.TypeTransaction;
import ma.projet.graph.entities.Compte;
import ma.projet.graph.repositories.TransactionRepository;
import ma.projet.graph.repositories.CompteRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class TransactionControllerGraphQL {

    private TransactionRepository transactionRepository;
    private CompteRepository compteRepository;

    // Ajouter une transaction
    @MutationMapping public Transaction addTransaction(@Argument TransactionRequest transactionRequest) { 
    	Compte compte = compteRepository.findById(transactionRequest.getCompteId())
    	.orElseThrow(() -> new RuntimeException("Compte not found")); 
    	Transaction transaction = new Transaction();
    	transaction.setMontant (transactionRequest.getMontant ());
    	transaction.setDate(transactionRequest.getDate());
    	transaction.setType(transactionRequest.getType()); 
    	transaction.setCompte (compte); 
    	transactionRepository.save(transaction);
    	return transaction;
    }
    @QueryMapping 
    public List<Transaction> compteTransactions (@Argument Long id){
    	Compte compte = compteRepository.findById(id)
    	.orElseThrow(()-> new RuntimeException("Compte not found"));
    	return transactionRepository.findByCompte(compte); 
    	}
    
    @QueryMapping
    public Map<String, Object> transactionStats() { 
        long count = transactionRepository.count();
        
        // Tester chaque somme individuellement
        double sumDepots = transactionRepository.sumByType(TypeTransaction.DEPOT);
        double sumRetraits = transactionRepository.sumByType(TypeTransaction.RETRAIT);

        // Afficher dans la console pour déboguer
        System.out.println("Sum of DEPOTS: " + sumDepots);
        System.out.println("Sum of RETRAITS: " + sumRetraits);

        return Map.of(
                "count", count,
                "sumDepots", sumDepots, 
                "sumRetraits", sumRetraits
        );
    }

   
}
