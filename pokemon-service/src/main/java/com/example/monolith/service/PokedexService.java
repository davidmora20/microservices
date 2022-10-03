package com.example.monolith.service;

import com.example.monolith.dto.PokemonDto;
import com.example.monolith.exceptionhandler.NoDataFoundException;
import com.example.monolith.exceptionhandler.PhotoNotFoundException;
import com.example.monolith.exceptionhandler.PokemonAlreadyExistsException;
import com.example.monolith.exceptionhandler.PokemonNotFoundException;
import com.example.monolith.exceptionhandler.TypeNotFoundException;
import com.example.monolith.mapper.PokemonMapper;
import com.example.monolith.model.Photo;
import com.example.monolith.model.Pokemon;
import com.example.monolith.model.Type;
import com.example.monolith.repository.IPhotoRepository;
import com.example.monolith.repository.IPokemonRepository;
import com.example.monolith.repository.ITypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class PokedexService implements IPokedexService {

    private final IPokemonRepository pokemonRepository;
    private final ITypeRepository typeRepository;
    private final IPhotoRepository photoRepository;
    private final PokemonMapper pokemonMapper;

    @Override
    public void savePokemon(PokemonDto pokemonDto) {
        if (pokemonRepository.findByNumber(pokemonDto.getNumber()).isPresent()) {
            throw new PokemonAlreadyExistsException();
        }
        Type type = typeRepository.save(pokemonMapper.toType(pokemonDto));
        Photo photo = photoRepository.save(pokemonMapper.toPhoto(pokemonDto));
        Pokemon pokemon = pokemonMapper.toPokemon(pokemonDto);
        pokemon.setTypeId(type.getId());
        pokemon.setPhotoId(photo.getId());
        pokemonRepository.save(pokemon);
    }

    @Override
    public List<PokemonDto> getAllPokemon() {
        List<Pokemon> pokemons = pokemonRepository.findAll();
        List<Type> types = typeRepository.findAll();
        List<Photo> photos = photoRepository.findAll();
        if (pokemons.isEmpty() || types.isEmpty() || photos.isEmpty()) {
            throw new NoDataFoundException();
        }
        return pokemonMapper.toListDto(pokemons, types, photos);
    }

    @Override
    public PokemonDto getPokemon(Long pokemonNumber) {
        Pokemon pokemon = pokemonRepository.findByNumber(pokemonNumber).orElseThrow(PokemonNotFoundException::new);
        Type type = typeRepository.findById(pokemon.getTypeId()).orElseThrow(TypeNotFoundException::new);
        Photo photo = photoRepository.findById(pokemon.getPhotoId()).orElseThrow(PhotoNotFoundException::new);
        return pokemonMapper.toDto(pokemon, type, photo);
    }

    @Override
    public void updatePokemon(PokemonDto pokemonDto) {
        Pokemon oldPokemon = pokemonRepository.findByNumber(pokemonDto.getNumber()).orElseThrow(PokemonNotFoundException::new);
        Type newType = pokemonMapper.toType(pokemonDto);
        newType.setId(oldPokemon.getTypeId());
        typeRepository.save(newType);
        Photo newPhoto = pokemonMapper.toPhoto(pokemonDto);
        newPhoto.setId(oldPokemon.getPhotoId());
        photoRepository.save(newPhoto);
        Pokemon newPokemon = pokemonMapper.toPokemon(pokemonDto);
        newPokemon.setId(oldPokemon.getId());
        newPokemon.setTypeId(oldPokemon.getTypeId());
        newPokemon.setPhotoId(oldPokemon.getPhotoId());
        pokemonRepository.save(newPokemon);
    }

    @Override
    public void deletePokemon(Long pokemonNumber) {
        Pokemon pokemon = pokemonRepository.findByNumber(pokemonNumber).orElseThrow(PokemonNotFoundException::new);
        typeRepository.deleteById(pokemon.getTypeId());
        photoRepository.deleteById(pokemon.getPhotoId());
        pokemonRepository.deleteByNumber(pokemonNumber);
    }
}