package com.example.monolith.service;

import com.example.monolith.dto.PokemonDto;

import java.util.List;

public interface IPokedexService {

    void savePokemon(PokemonDto pokemonDto);

    List<PokemonDto> getAllPokemon();

    PokemonDto getPokemon(Long pokemonNumber);

    void updatePokemon (PokemonDto pokemonDto);

    void deletePokemon (Long pokemonNumber);
}