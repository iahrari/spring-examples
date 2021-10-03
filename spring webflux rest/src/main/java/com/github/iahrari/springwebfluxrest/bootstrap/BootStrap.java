package com.github.iahrari.springwebfluxrest.bootstrap;

import com.github.iahrari.springwebfluxrest.domain.Category;
import com.github.iahrari.springwebfluxrest.domain.Vendor;
import com.github.iahrari.springwebfluxrest.repository.CategoryRepository;
import com.github.iahrari.springwebfluxrest.repository.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public record BootStrap(
		CategoryRepository categoryRepository,
		VendorRepository vendorRepository
) implements CommandLineRunner {
	@Override
	public void run(String... args) {
		categoryRepository.deleteAll().block();
		vendorRepository.deleteAll().block();
		
		populateCategories();
		populateVendors();
	}
	
	public void populateCategories(){
		categoryRepository.save(
				Category.builder()
						.description("Fruits")
						.build()
		).block();
		
		categoryRepository.save(
				Category.builder()
						.description("Nuts")
						.build()
		).block();
		
		categoryRepository.save(
				Category.builder()
						.description("Breads")
						.build()
		).block();
		
		categoryRepository.save(
				Category.builder()
						.description("Meats")
						.build()
		).block();
		
		categoryRepository.save(
				Category.builder()
						.description("Eggs")
						.build()
		).block();
		
		System.out.println("Categories loaded: " + categoryRepository.count().block());
	}
	
	public void populateVendors(){
		vendorRepository.save(
				Vendor.builder()
						.firstName("Joe")
						.lastName("Buck")
						.build()
		).block();
		
		vendorRepository.save(
				Vendor.builder()
						.firstName("Jim")
						.lastName("Jordan")
						.build()
		).block();
		
		vendorRepository.save(
				Vendor.builder()
						.firstName("Haley")
						.lastName("Green")
						.build()
		).block();
		
		vendorRepository.save(
				Vendor.builder()
						.firstName("Manny")
						.lastName("Geller")
						.build()
		).block();
		
		vendorRepository.save(
				Vendor.builder()
						.firstName("Maria")
						.lastName("Abdul")
						.build()
		).block();
		
		System.out.println("Vendors loaded: " + vendorRepository.count().block());
	}
}
