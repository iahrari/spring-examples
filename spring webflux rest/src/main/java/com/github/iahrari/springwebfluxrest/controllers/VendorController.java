package com.github.iahrari.springwebfluxrest.controllers;

import com.github.iahrari.springwebfluxrest.domain.Vendor;
import com.github.iahrari.springwebfluxrest.repository.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping({VendorController.BASE_URL})
public record VendorController(
		VendorRepository repository
) {
	public static final String BASE_URL = "/api/v1/vendors";
	
	@GetMapping
	public Flux<Vendor> getAllVendors(){
		return repository.findAll();
	}
	
	@GetMapping({"/{id}"})
	public Mono<Vendor> getVendorById(@PathVariable("id") String id){
		return repository.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Publisher<Vendor> createPublisher(@RequestBody Mono<Vendor> vendorStream){
		return vendorStream.flatMap(repository::save);
	}
	
	@PutMapping({"/{id}"})
	public Mono<Vendor> updatePublisher(
			@PathVariable("id") String id,
			@RequestBody Mono<Vendor> vendor
	){
		return vendor.map((v) -> {
			v.setId(id);
			return v;
		}).flatMap(repository::save);
	}
	
	@PatchMapping({"/{id}"})
	public Mono<Vendor> patchPublisher(
			@PathVariable("id") String id,
			@RequestBody Mono<Vendor> vendor
	){
		return repository.findById(id).flatMap((v) -> vendor.map((receivedVendor) -> {
			if(receivedVendor.getFirstName() != null)
				v.setFirstName(receivedVendor.getFirstName());
			if(receivedVendor.getLastName() != null)
				v.setLastName(receivedVendor.getLastName());
			return v;
		})).flatMap(repository::save);
	}
}
