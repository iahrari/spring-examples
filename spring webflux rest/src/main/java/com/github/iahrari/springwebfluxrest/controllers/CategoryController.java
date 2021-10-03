package com.github.iahrari.springwebfluxrest.controllers;

import com.github.iahrari.springwebfluxrest.domain.Category;
import com.github.iahrari.springwebfluxrest.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public record CategoryController(
		CategoryRepository repository
) {
	
	public static final String BASE_URL = "/api/v1/categories";
	
	@GetMapping
	public Flux<Category> getAllCategories() {
		return repository.findAll();
	}
	
	@GetMapping({"/{id}"})
	public Mono<Category> getCategory(@PathVariable String id) {
		return repository.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Category> create(@RequestBody Mono<Category> categoryStream) {
		return categoryStream.flatMap(repository::save);
	}
	
	@PutMapping({"/{id}"})
	public Mono<Category> update(
			@PathVariable("id") String id,
			@RequestBody Mono<Category> category
	){
		return category.map((c) -> {
					c.setId(id);
					return c;
				}).flatMap(repository::save);
	}
	
	@PatchMapping({"/{id}"})
	public Mono<Category> patch(
			@PathVariable("id") String id,
			@RequestBody Mono<Category> category
	){
		return repository.findById(id).flatMap((cat) -> category.map((receivedCat) -> {
			if((receivedCat != null ? receivedCat.getDescription() : null) != null)
				cat.setDescription(receivedCat.getDescription());
			return cat;
		})).flatMap(repository::save);
	}
}
