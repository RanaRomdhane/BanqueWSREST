package com.example.banquewsrest.web;

import com.example.banquewsrest.entities.Compte;
import com.example.banquewsrest.repositories.CompteRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * API REST pour la gestion des comptes bancaires avec JAX-RS (Jersey)
 */
@Component
@Path("/banque")
public class CompteJAXRSAPI {

    @Autowired
    private CompteRepository compteRepository;

    /**
     * Récupérer tous les comptes
     * URL: GET http://localhost:8089/banque/comptes
     */
    @Path("/comptes")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Compte> getComptes() {
        return compteRepository.findAll();
    }

    /**
     * Récupérer un compte par son ID
     * URL: GET http://localhost:8089/banque/comptes/{id}
     */
    @Path("/comptes/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Compte getCompte(@PathParam("id") Long id) {
        return compteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compte introuvable avec l'ID: " + id));
    }

    /**
     * Créer un nouveau compte
     * URL: POST http://localhost:8089/banque/comptes
     */
    @Path("/comptes")
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Compte saveCompte(Compte compte) {
        return compteRepository.save(compte);
    }

    /**
     * Mettre à jour un compte existant
     * URL: PUT http://localhost:8089/banque/comptes/{id}
     */
    @Path("/comptes/{id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Compte updateCompte(@PathParam("id") Long id, Compte compte) {
        Compte existingCompte = compteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compte introuvable avec l'ID: " + id));
        
        if (compte.getSolde() != 0) {
            existingCompte.setSolde(compte.getSolde());
        }
        if (compte.getType() != null) {
            existingCompte.setType(compte.getType());
        }
        
        return compteRepository.save(existingCompte);
    }

    /**
     * Supprimer un compte
     * URL: DELETE http://localhost:8089/banque/comptes/{id}
     */
    @Path("/comptes/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void deleteCompte(@PathParam("id") Long id) {
        compteRepository.deleteById(id);
    }

    /**
     * Convertir un montant de Dinar en Euro
     * URL: GET http://localhost:8089/banque/convertir/{montant}
     * Taux de conversion : 1 TND = 0.30 EUR (exemple)
     */
    @Path("/convertir/{montant}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String convertirDinarEnEuro(@PathParam("montant") double montant) {
        double tauxConversion = 0.30; // 1 TND = 0.30 EUR
        double montantEuro = montant * tauxConversion;
        return montant + " TND = " + String.format("%.2f", montantEuro) + " EUR";
    }
}