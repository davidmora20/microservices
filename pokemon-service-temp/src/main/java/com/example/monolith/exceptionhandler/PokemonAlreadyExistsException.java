package com.example.monolith.exceptionhandler;

public class PokemonAlreadyExistsException extends RuntimeException {
    public PokemonAlreadyExistsException() {
        super();
    }
}