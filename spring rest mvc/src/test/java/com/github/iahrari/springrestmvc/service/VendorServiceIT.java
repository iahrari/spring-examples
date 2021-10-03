package com.github.iahrari.springrestmvc.service;

import com.github.iahrari.springrestmvc.api.v1.mapper.VendorDTOMapperImpl;
import com.github.iahrari.springrestmvc.api.v1.mapper.VendorMapperImpl;
import com.github.iahrari.springrestmvc.api.v1.model.VendorDTO;
import com.github.iahrari.springrestmvc.bootstrap.Bootstrap;
import com.github.iahrari.springrestmvc.domain.Vendor;
import com.github.iahrari.springrestmvc.repository.CategoryRepository;
import com.github.iahrari.springrestmvc.repository.CustomerRepository;
import com.github.iahrari.springrestmvc.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class VendorServiceIT {
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	VendorRepository vendorRepository;
	
	private VendorService service;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		new Bootstrap(categoryRepository, customerRepository, vendorRepository).run();
		
		var converter = new GenericConversionService();
		converter.addConverter(new VendorMapperImpl());
		converter.addConverter(new VendorDTOMapperImpl());
		service = new VendorServiceImpl(vendorRepository, converter);
	}
	
	@Test
	public void patchVendorUpdateName() {
		String updatedName = "UpdatedName";
		long id = getCustomerIdValue();
		var optVendor = vendorRepository.findById(id);
		assertTrue(optVendor.isPresent());
		var originalVendor = optVendor.get();
		assertNotNull(originalVendor);
		//save original first name
		String originalFirstName = originalVendor.getName();
		
		var vendorDTO = new VendorDTO();
		vendorDTO.setName(updatedName);
		
		service.patchVendorById(id, vendorDTO);
		
		var updatedVendorOpt = vendorRepository.findById(id);
		assertTrue(updatedVendorOpt.isPresent());
		var updatedCustomer = updatedVendorOpt.get();
		
		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getName());
		assertNotEquals(originalFirstName, updatedCustomer.getName());
	}
	
	@Test
	public void updateVendor() {
		String updatedName = "UpdatedName";
		long id = getCustomerIdValue();
		var optVendor = vendorRepository.findById(id);
		assertTrue(optVendor.isPresent());
		var originalVendor = optVendor.get();
		assertNotNull(originalVendor);
		//save original first name
		String originalFirstName = originalVendor.getName();
		
		var vendorDTO = new VendorDTO();
		vendorDTO.setName(updatedName);
		
		service.updateVendorById(id, vendorDTO);
		
		var updatedVendorOpt = vendorRepository.findById(id);
		assertTrue(updatedVendorOpt.isPresent());
		var updatedCustomer = updatedVendorOpt.get();
		
		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getName());
		assertNotEquals(originalFirstName, updatedCustomer.getName());
	}
	
	private Long getCustomerIdValue(){
		List<Vendor> vendors = vendorRepository.findAll();
		
		System.out.println("Vendors Found: " + vendors.size());
		
		//return first id
		return vendors.get(0).getId();
	}
}
