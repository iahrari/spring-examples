package com.github.iahrari.springrestmvc.service;

import com.github.iahrari.springrestmvc.api.v1.model.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
	List<CategoryDTO> getAllCategories();
	Optional<CategoryDTO> getCategoryByName(String name);
}
