package com.github.iahrari.springrestmvc.controllers.v1;

import com.github.iahrari.springrestmvc.api.v1.model.CategoryDTO;
import com.github.iahrari.springrestmvc.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static com.github.iahrari.springrestmvc.controllers.v1.AbstractRestControllerTest.standAloneMockWithAdvice;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest {
	private final static String NAME = "Jim";
	
	@Mock
	CategoryService service;
	
	@InjectMocks
	CategoryController controller;
	
	private MockMvc mvc;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mvc = standAloneMockWithAdvice(controller);
	}
	
	@Test
	public void testListCategories() throws Exception {
		//given
		var dto1 = new CategoryDTO(1L, NAME);
		var dto2 = new CategoryDTO(2L, "John");
		
		var list = Arrays.asList(dto1, dto2);
		when(service.getAllCategories())
				.thenReturn(list);
		
		mvc.perform(get(CategoryController.BASE_URL)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.categories", hasSize(2)));
	}
	
	@Test
	public void getCategoriesByName() throws Exception {
		var dto1 = new CategoryDTO(1L, NAME);
		
		when(service.getCategoryByName(anyString())).thenReturn(Optional.of(dto1));
		
		mvc.perform(get(CategoryController.BASE_URL + "/Jim")
						.accept(MediaType.APPLICATION_JSON)	
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo(NAME)));
	}
	
	@Test
	public void getCategoriesByWrongName() throws Exception {
		when(service.getCategoryByName(anyString())).thenReturn(Optional.empty());
		
		mvc.perform(get(CategoryController.BASE_URL + "/Jim")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error", containsString(CategoryController.CATEGORY_NOT_FOUND)));
	}
}