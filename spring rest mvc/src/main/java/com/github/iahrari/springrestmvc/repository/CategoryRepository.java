package com.github.iahrari.springrestmvc.repository;

import com.github.iahrari.springrestmvc.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	public Optional<Category> findCategoryByName(String name);
}

