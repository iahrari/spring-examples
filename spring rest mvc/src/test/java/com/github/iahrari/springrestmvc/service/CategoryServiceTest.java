package com.github.iahrari.springrestmvc.service;

import com.github.iahrari.springrestmvc.api.v1.mapper.CategoryDTOMapperImpl;
import com.github.iahrari.springrestmvc.domain.Category;
import com.github.iahrari.springrestmvc.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceTest {
	private static final Long ID = 1L;
	private static final String NAME = "John";
	private CategoryService service;
	
	@Mock
	private CategoryRepository repository;
	
	@BeforeEach
	void setUp() {
		var converter = new GenericConversionService();
		converter.addConverter(new CategoryDTOMapperImpl());
		
		MockitoAnnotations.openMocks(this);
		service = new CategoryServiceImpl(converter, repository);
	}
	
	@Test
	void getAllCategories() {
		//given
		List<Category> categories = Arrays.asList(
				new Category(), new Category(), new Category()
		);
		when(repository.findAll()).thenReturn(categories);
		
		//when
		var dts = service.getAllCategories();
		
		//then
		assertEquals(categories.size(), dts.size());
	}
	
	@Test
	void getCategoryByName() {
		//given
		var category = new Category();
		category.setName(NAME);
		category.setId(ID);
		
		when(repository.findCategoryByName(anyString()))
				.thenReturn(Optional.of(category));
		
		//when
		var dto = service.getCategoryByName(NAME);
		
		//then
		assertTrue(dto.isPresent());
		assertEquals(ID, dto.get().getId());
		assertEquals(NAME, dto.get().getName());
	}
}