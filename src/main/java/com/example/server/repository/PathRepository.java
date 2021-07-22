package com.example.server.repository;

import com.example.server.entity.Path;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PathRepository extends JpaRepository<Path, Long> {
	Optional<Path> findByPath(String path);
}
