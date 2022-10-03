package com.example.monolith.service;

import com.example.monolith.dto.PhotoDto;
import com.example.monolith.model.Photo;

import java.util.List;

public interface IPokedexService {

    Photo savePhoto(PhotoDto photoId);

    List<PhotoDto> getAllPhotos();

    PhotoDto getPhoto(String photoId);

    Photo updatePhoto (PhotoDto photoId);

    void deletePhoto (String photoId);
}