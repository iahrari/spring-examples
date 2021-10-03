package com.github.iahrari.springrestmvc.service;

import com.github.iahrari.springrestmvc.api.v1.mapper.CustomerMapperDTOImpl;
import com.github.iahrari.springrestmvc.api.v1.mapper.CustomerMapperImpl;
import com.github.iahrari.springrestmvc.api.v1.model.CustomerDTO;
import com.github.iahrari.springrestmvc.bootstrap.Bootstrap;
import com.github.iahrari.springrestmvc.domain.Customer;
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
public class CustomerServiceIT {
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	VendorRepository vendorRepository;
	
	private CustomerService service;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		new Bootstrap(categoryRepository, customerRepository, vendorRepository).run();
		
		var converter = new GenericConversionService();
		converter.addConverter(new CustomerMapperDTOImpl());
		converter.addConverter(new CustomerMapperImpl());
		service = new CustomerServiceImpl(customerRepository, converter);
	}
	
	@Test
	public void patchCustomerUpdateFirstName() {
		String updatedName = "UpdatedName";
		long id = getCustomerIdValue();
		var optCustomer = customerRepository.findById(id);
		assertTrue(optCustomer.isPresent());
		var originalCustomer = optCustomer.get();
		assertNotNull(originalCustomer);
		//save original first name
		String originalFirstName = originalCustomer.getFirstName();
		String originalLastName = originalCustomer.getLastName();
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName(updatedName);
		
		service.patchCustomerById(id, customerDTO);
		
		var updatedCustomerOpt = customerRepository.findById(id);
		assertTrue(updatedCustomerOpt.isPresent());
		Customer updatedCustomer = updatedCustomerOpt.get();
		
		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getFirstName());
		assertNotEquals(originalFirstName, updatedCustomer.getFirstName());
		assertEquals(originalLastName, updatedCustomer.getLastName());
	}
	
	@Test
	public void patchCustomerUpdateLastName() {
		String updatedName = "UpdatedName";
		long id = getCustomerIdValue();
		
		var optCustomer = customerRepository.findById(id);
		assertTrue(optCustomer.isPresent());
		var originalCustomer = optCustomer.get();
		assertNotNull(originalCustomer);
		
		//save original first/last name
		String originalFirstName = originalCustomer.getFirstName();
		String originalLastName = originalCustomer.getLastName();
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setLastName(updatedName);
		
		service.patchCustomerById(id, customerDTO);
		
		var updatedCustomerOpt = customerRepository.findById(id);
		assertTrue(updatedCustomerOpt.isPresent());
		Customer updatedCustomer = updatedCustomerOpt.get();
		
		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getLastName());
		assertEquals(originalFirstName, updatedCustomer.getFirstName());
		assertNotEquals(originalLastName, updatedCustomer.getLastName());
	}
	
	private Long getCustomerIdValue(){
		List<Customer> customers = customerRepository.findAll();
		
		System.out.println("Customers Found: " + customers.size());
		
		//return first id
		return customers.get(0).getId();
	}
}
