package com.example.banquewsrest.web;

import com.example.banquewsrest.entities.Compte;
import com.example.banquewsrest.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API REST pour la gestion des comptes bancaires avec Spring MVC
 */
@RestController
@RequestMapping("/banque")
public class CompteRESTController {

    @Autowired
    private CompteRepository compteRepository;

    /**
     * Récupérer tous les comptes
     * URL: GET http://localhost:8089/banque/comptes
     * Supporte JSON et XML selon le header Accept
     */
    @GetMapping(value = "/comptes", 
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Compte> getComptes() {
        return compteRepository.findAll();
    }

    /**
     * Récupérer un compte par son ID
     * URL: GET http://localhost:8089/banque/comptes/{id}
     */
    @GetMapping(value = "/comptes/{id}", 
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Compte> getCompte(@PathVariable Long id) {
        return compteRepository.findById(id)
                .map(compte -> ResponseEntity.ok(compte))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Créer un nouveau compte
     * URL: POST http://localhost:8089/banque/comptes
     */
    @PostMapping(value = "/comptes",
                 consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Compte> saveCompte(@RequestBody Compte compte) {
        Compte savedCompte = compteRepository.save(compte);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompte);
    }

    /**
     * Mettre à jour un compte existant
     * URL: PUT http://localhost:8089/banque/comptes/{id}
     */
    @PutMapping(value = "/comptes/{id}",
                consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Compte> updateCompte(@PathVariable Long id, @RequestBody Compte compte) {
        return compteRepository.findById(id)
                .map(existingCompte -> {
                    if (compte.getSolde() != 0) {
                        existingCompte.setSolde(compte.getSolde());
                    }
                    if (compte.getType() != null) {
                        existingCompte.setType(compte.getType());
                    }
                    Compte updatedCompte = compteRepository.save(existingCompte);
                    return ResponseEntity.ok(updatedCompte);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Supprimer un compte
     * URL: DELETE http://localhost:8089/banque/comptes/{id}
     */
    @DeleteMapping("/comptes/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        if (compteRepository.existsById(id)) {
            compteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Convertir un montant de Dinar en Euro
     * URL: GET http://localhost:8089/banque/convertir/{montant}
     * Taux de conversion : 1 TND = 0.30 EUR (exemple)
     */
    @GetMapping(value = "/convertir/{montant}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String convertirDinarEnEuro(@PathVariable double montant) {
        double tauxConversion = 0.30; // 1 TND = 0.30 EUR
        double montantEuro = montant * tauxConversion;
        return montant + " TND = " + String.format("%.2f", montantEuro) + " EUR";
    }

    /**
     * Endpoint de santé pour vérifier que l'API fonctionne
     * URL: GET http://localhost:8089/banque/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("API REST avec Spring MVC est opérationnelle!");
    }
}