package com.example.monolith.repository;

import com.example.monolith.model.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IPhotoRepository extends MongoRepository<Photo, String> {}