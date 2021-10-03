package com.github.iahrari.springrestmvc.service;

import com.github.iahrari.springrestmvc.api.v1.model.CategoryDTO;
import com.github.iahrari.springrestmvc.repository.CategoryRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record CategoryServiceImpl(
		ConversionService converter,
		CategoryRepository categoryRepository
) implements CategoryService {
	@Override
	public List<CategoryDTO> getAllCategories() {
		return categoryRepository.findAll().stream()
				.map((category) -> converter.convert(category, CategoryDTO.class))
				.toList();
	}
	
	@Override
	public Optional<CategoryDTO> getCategoryByName(String name) {
		return categoryRepository.findCategoryByName(name)
				.map((category) -> converter.convert(category, CategoryDTO.class));
	}
}
