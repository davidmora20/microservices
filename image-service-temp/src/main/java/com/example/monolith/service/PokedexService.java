package com.example.monolith.service;

import com.example.monolith.dto.PhotoDto;
import com.example.monolith.exceptionhandler.NoDataFoundException;
import com.example.monolith.exceptionhandler.PhotoNotFoundException;
import com.example.monolith.mapper.PhotoMapper;
import com.example.monolith.model.Photo;
import com.example.monolith.repository.IPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class PokedexService implements IPokedexService {

    private final IPhotoRepository photoRepository;
    private final PhotoMapper pokemonMapper;

    @Override
    public Photo savePhoto(PhotoDto pokemonDto) {
        return photoRepository.save(pokemonMapper.toPhoto(pokemonDto));
    }

    @Override
    public List<PhotoDto> getAllPhotos() {
        List<Photo> photos = photoRepository.findAll();
        if (photos.isEmpty()) {
            throw new NoDataFoundException();
        }
        return pokemonMapper.toListDto(photos);
    }

    @Override
    public PhotoDto getPhoto(String photoId) {
        Photo photo = photoRepository.findById(photoId).orElseThrow(PhotoNotFoundException::new);
        return pokemonMapper.toDto(photo);
    }

    @Override
    public Photo updatePhoto(PhotoDto pokemonDto) {
        Photo newPhoto = pokemonMapper.toPhoto(pokemonDto);
        return photoRepository.save(newPhoto);
    }

    @Override
    public void deletePhoto(String photoId) {
        photoRepository.deleteById(photoId);
    }
}