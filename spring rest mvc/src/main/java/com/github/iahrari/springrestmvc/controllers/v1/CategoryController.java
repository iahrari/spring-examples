package com.github.iahrari.springrestmvc.controllers.v1;

import com.github.iahrari.springrestmvc.api.v1.model.CategoryDTO;
import com.github.iahrari.springrestmvc.api.v1.model.ListDTO;
import com.github.iahrari.springrestmvc.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public record CategoryController(
		CategoryService categoryService
) {
	public static final String BASE_URL = "/api/v1/categories";
	public static final String CATEGORY_NOT_FOUND = "Category doesn't exists";
	
	@GetMapping({"", "/"})
	@ResponseStatus(HttpStatus.OK)
	public ListDTO<CategoryDTO> getAllCategories() {
		return new ListDTO<>(categoryService.getAllCategories(), CategoryDTO.dtoPluralName);
	}
	
	@GetMapping("/{name}")
	@ResponseStatus(HttpStatus.OK)
	public CategoryDTO getCategoryByName(@PathVariable("name") String name) {
		return categoryService.getCategoryByName(name)
				.orElseThrow(() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						CATEGORY_NOT_FOUND
				));
	}
}
