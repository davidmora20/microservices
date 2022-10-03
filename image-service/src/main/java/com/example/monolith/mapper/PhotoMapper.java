package com.example.monolith.mapper;

import com.example.monolith.dto.PhotoDto;
import com.example.monolith.model.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Base64;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhotoMapper {
    @Mapping(target = "photo", qualifiedByName = "byteArrayToBase64")
    PhotoDto toDto(Photo photo);

    @Mapping(target = "photo", qualifiedByName = "base64ToByteArray")
    Photo toPhoto(PhotoDto pokemonDto);

    @Named("base64ToByteArray")
    static byte[] base64ToByteArray(String base64Photo) {
        return Base64.getDecoder().decode(base64Photo);
    }

    @Named("byteArrayToBase64")
    static String byteArrayToBase64(byte[] byteArrayPhoto) {
        return Base64.getEncoder().encodeToString(byteArrayPhoto);
    }

    @Mapping(target = "photo", qualifiedByName = "byteArrayToBase64")
    List<PhotoDto> toListDto(List<Photo> photos);
}