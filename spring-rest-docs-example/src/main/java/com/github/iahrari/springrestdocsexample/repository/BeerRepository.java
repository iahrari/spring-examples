package com.github.iahrari.springrestdocsexample.repository;

import com.github.iahrari.springrestdocsexample.domain.Beer;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<Beer, UUID> {}
