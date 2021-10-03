package com.github.iahrari.springwebfluxrest.controllers;

import com.github.iahrari.springwebfluxrest.domain.Category;
import com.github.iahrari.springwebfluxrest.domain.Vendor;
import com.github.iahrari.springwebfluxrest.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CategoryControllerTest {
	private WebTestClient testClient;
	@Mock
	private CategoryRepository repository;
	
	@InjectMocks
	private CategoryController controller;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		testClient = WebTestClient.bindToController(controller).build();
	}
	
	@Test
	void getAllCategories() {
		given(repository.findAll()).willReturn(
				Flux.just(
						Category.builder().description("Cat1").build(),
						Category.builder().description("Cat2").build()
				)
		);
		
		testClient.get().uri(CategoryController.BASE_URL)
				.exchange()
				.expectBodyList(Category.class)
				.hasSize(2);
	}
	
	@Test
	void getCategory() {
		given(repository.findById(anyString())).willReturn(
				Mono.just(Category.builder().description("Cat1").build())
		);
		
		testClient.get().uri(CategoryController.BASE_URL + "/some")
				.exchange()
				.expectBody(Category.class);
	}
	
	@Test
	public void  testCreate() {
		given(repository.save(any()))
				.willReturn(Mono.just(Category.builder().build()));
		var cat = Mono.just(Category.builder().build());
		
		testClient.post().uri(CategoryController.BASE_URL)
				.body(cat, Category.class)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Category.class);
	}
	
	@Test
	public void testUpdate() {
		given(repository.save(any()))
				.willReturn(Mono.just(Category.builder().build()));
		var cat = Mono.just(Category.builder().build());
		
		testClient.put()
				.uri(CategoryController.BASE_URL + "/id")
				.body(cat, Category.class)
				.exchange()
				.expectStatus().isOk();
	}
	
	@Test
	public void patchWithUpdate() {
		given(repository.findById(anyString()))
				.willReturn(Mono.just(Category.builder().build()));
		given(repository.save(any()))
				.willReturn(Mono.just(Category.builder().build()));
		var vendor = Mono.just(Vendor.builder().firstName("Jim").build());
		
		testClient.patch().uri(CategoryController.BASE_URL + "/id")
				.body(vendor, Category.class)
				.exchange()
				.expectStatus().isOk();
		
		verify(repository, times(1)).save(any());
	}
}