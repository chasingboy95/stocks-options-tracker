package com.chasing.repository;

import com.chasing.entity.Definition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefinitionsRepository extends JpaRepository<Definition, String> {
}
