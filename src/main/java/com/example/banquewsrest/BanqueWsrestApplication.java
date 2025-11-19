package com.example.banquewsrest;

import com.example.banquewsrest.entities.Compte;
import com.example.banquewsrest.entities.TypeCompte;
import com.example.banquewsrest.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class BanqueWsrestApplication {

	public static void main(String[] args) {

		SpringApplication.run(BanqueWsrestApplication.class, args);

	}

	@Bean
	CommandLineRunner start(CompteRepository compteRepository) {
		return args -> {
            compteRepository.save(new Compte(null,Math.random()*1000, new Date(), TypeCompte.COURANT));
			compteRepository.save(new Compte(null,Math.random()*1000, new Date(), TypeCompte.EPARGNE));
			compteRepository.save(new Compte(null,Math.random()*1000, new Date(), TypeCompte.COURANT));
			compteRepository.findAll().forEach(c ->{
				System.out.println(c.toString());
			} );
		};

	}
}
