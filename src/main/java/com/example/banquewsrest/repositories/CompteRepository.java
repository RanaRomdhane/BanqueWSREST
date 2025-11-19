package com.example.banquewsrest.repositories;

import com.example.banquewsrest.entities.Compte;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource
public interface CompteRepository extends JpaRepository<Compte,Long> {
}
