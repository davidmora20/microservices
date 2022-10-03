package com.example.monolith.mapper;

import com.example.monolith.dto.PokemonDto;
import com.example.monolith.exceptionhandler.PhotoNotFoundException;
import com.example.monolith.exceptionhandler.TypeNotFoundException;
import com.example.monolith.model.Photo;
import com.example.monolith.model.Pokemon;
import com.example.monolith.model.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PokemonMapper {
    @Mapping(source = "type", target = "types")
    @Mapping(target = "photo", qualifiedByName = "byteArrayToBase64")
    PokemonDto toDto(Pokemon pokemon, Type type, Photo photo);

    Pokemon toPokemon(PokemonDto pokemonDto);

    @Mapping(source = "pokemonDto.types.firstType", target = "firstType")
    @Mapping(source = "pokemonDto.types.secondType", target = "secondType")
    Type toType(PokemonDto pokemonDto);

    @Mapping(target = "photo", qualifiedByName = "base64ToByteArray")
    Photo toPhoto(PokemonDto pokemonDto);

    @Named("base64ToByteArray")
    static byte[] base64ToByteArray(String base64Photo) {
        return Base64.getDecoder().decode(base64Photo);
    }

    @Named("byteArrayToBase64")
    static String byteArrayToBase64(byte[] byteArrayPhoto) {
        return Base64.getEncoder().encodeToString(byteArrayPhoto);
    }

    default List<PokemonDto> toListDto(List<Pokemon> pokemonList, List<Type> types, List<Photo> photos) {
        List<PokemonDto> pokemonDtoList = pokemonList.stream().map(pokemon -> {
            PokemonDto pokeDto = new PokemonDto();
            pokeDto.setNumber(pokemon.getNumber());
            pokeDto.setName(pokemon.getName());
            pokeDto.setTypes(types.stream().filter(pokeType -> pokeType.getId() == pokemon.getTypeId()).findFirst().orElseThrow(TypeNotFoundException::new));
            pokeDto.setPhoto(byteArrayToBase64(photos.stream().filter(photo -> photo.getId().equals(pokemon.getPhotoId())).findFirst().orElseThrow(PhotoNotFoundException::new).getPhoto()));
            return pokeDto;
        }).collect(Collectors.toList());
        return pokemonDtoList;
    }
}