package com.github.iahrari.springwebfluxrest.controllers;

import com.github.iahrari.springwebfluxrest.domain.Vendor;
import com.github.iahrari.springwebfluxrest.repository.VendorRepository;
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
import static org.mockito.Mockito.*;

class VendorControllerTest {
	@Mock
	private VendorRepository vendorRepository;
	
	@InjectMocks
	private VendorController controller;
	
	private WebTestClient testClient;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		testClient = WebTestClient.bindToController(controller).build();
	}
	
	@Test
	void getAllVendors() {
		var list = Flux.just(new Vendor(), new Vendor());
		given(vendorRepository.findAll()).willReturn(list);
		
		testClient.get().uri(VendorController.BASE_URL)
				.exchange()
				.expectBodyList(Vendor.class)
				.hasSize(Math.toIntExact(2));
	}
	
	@Test
	void getVendorById() {
		Vendor.builder().id("1").build();
		var mono = Mono.just(Vendor.builder().build());
		given(vendorRepository.findById(anyString()))
				.willReturn(mono);
		
		testClient.get().uri(VendorController.BASE_URL + "/d")
				.exchange()
				.expectBody(Vendor.class);
	}
	
	@Test
	public void createVendor() {
		given(vendorRepository.save(any()))
				.willReturn(Mono.just(Vendor.builder().build()));

		var vendor = Mono.just(Vendor.builder().build());
		
		testClient.post().uri(VendorController.BASE_URL)
				.body(vendor, Vendor.class)
				.exchange()
				.expectStatus().isCreated();
	}
	
	@Test
	public void updateVendor() {
		given(vendorRepository.save(any()))
				.willReturn(Mono.just(Vendor.builder().build()));
		
		var vendor = Mono.just(Vendor.builder().build());
		
		testClient.put().uri(VendorController.BASE_URL + "/id")
				.body(vendor, Vendor.class)
				.exchange()
				.expectStatus().isOk();
	}
	
	@Test
	public void patchWithUpdate() {
		given(vendorRepository.findById(anyString()))
				.willReturn(Mono.just(Vendor.builder().build()));
		given(vendorRepository.save(any()))
				.willReturn(Mono.just(Vendor.builder().build()));
		var vendor = Mono.just(Vendor.builder().firstName("Jim").build());
		
		testClient.patch().uri(VendorController.BASE_URL + "/id")
				.body(vendor, Vendor.class)
				.exchange()
				.expectStatus().isOk();
		
		verify(vendorRepository, times(1)).save(any());
	}
}