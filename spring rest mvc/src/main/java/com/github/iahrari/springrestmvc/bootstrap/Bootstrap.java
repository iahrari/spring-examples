package com.github.iahrari.springrestmvc.bootstrap;

import com.github.iahrari.springrestmvc.domain.Category;
import com.github.iahrari.springrestmvc.domain.Customer;
import com.github.iahrari.springrestmvc.domain.Vendor;
import com.github.iahrari.springrestmvc.repository.CategoryRepository;
import com.github.iahrari.springrestmvc.repository.CustomerRepository;
import com.github.iahrari.springrestmvc.repository.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public record Bootstrap(
		CategoryRepository categoryRepository,
		CustomerRepository customerRepository,
		VendorRepository vendorRepository
) implements CommandLineRunner {
	@Override
	public void run(String... args) {
		loadCategories();
		loadCustomers();
		loadVendors();
	}
	
	private void loadCategories() {
		Category fruits = new Category();
		fruits.setName("Fruits");
		
		Category dried = new Category();
		dried.setName("Dried");
		
		Category fresh = new Category();
		fresh.setName("Fresh");
		
		Category exotic = new Category();
		exotic.setName("Exotic");
		
		Category nuts = new Category();
		nuts.setName("Nuts");
		
		categoryRepository.save(fruits);
		categoryRepository.save(dried);
		categoryRepository.save(fresh);
		categoryRepository.save(exotic);
		categoryRepository.save(nuts);
		
		System.out.println("Categories Loaded: " + categoryRepository.count());
	}
	
	private void loadCustomers() {
		//given
		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setFirstName("Michale");
		customer1.setLastName("Weston");
		customerRepository.save(customer1);
		
		Customer customer2 = new Customer();
		customer2.setId(2L);
		customer2.setFirstName("Sam");
		customer2.setLastName("Axe");
		
		customerRepository.save(customer2);
		
		System.out.println("Customers Loaded: " + customerRepository.count());
	}
	
	private void loadVendors() {
		Vendor vendor1 = new Vendor();
		vendor1.setName("Vendor 1");
		vendorRepository.save(vendor1);
		
		Vendor vendor2 = new Vendor();
		vendor2.setName("Vendor 2");
		vendorRepository.save(vendor2);
		
		System.out.println("Vendors Loaded: " + vendorRepository.count());
	}
}
