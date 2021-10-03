package com.github.iahrari.springwebfluxrest.repository;

import com.github.iahrari.springwebfluxrest.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {}
