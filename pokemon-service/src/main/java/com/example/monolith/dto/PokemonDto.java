package com.example.monolith.dto;

import com.example.monolith.model.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PokemonDto {

    private Long number;
    private String name;
    private Type types;
    private String photo;
}