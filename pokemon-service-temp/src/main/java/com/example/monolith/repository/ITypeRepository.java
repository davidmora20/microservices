package com.example.monolith.repository;

import com.example.monolith.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITypeRepository extends JpaRepository<Type, Long> {}