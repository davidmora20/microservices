package com.example.monolith.repository;

import com.example.monolith.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPokemonRepository extends JpaRepository<Pokemon, Long> {

    Optional<Pokemon> findByNumber(Long pokemonNumber);

    void deleteByNumber(Long pokemonNumber);
}