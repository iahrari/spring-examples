package com.github.iahrari.springrestmvc.service;

import com.github.iahrari.springrestmvc.api.v1.mapper.CustomerMapperDTOImpl;
import com.github.iahrari.springrestmvc.api.v1.mapper.CustomerMapperImpl;
import com.github.iahrari.springrestmvc.api.v1.model.CustomerDTO;
import com.github.iahrari.springrestmvc.domain.Customer;
import com.github.iahrari.springrestmvc.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest {
	@Mock
	private CustomerRepository repository;
	private CustomerService service;
	
	private final Long ID = 1L;
	private final String FIRST_NAME = "Iman";
	private final String LAST_NAME = "Ahrari";
	private final String CUSTOMER_URL = "/api/v1/customers/" + ID;
	private Customer customer;
	private CustomerDTO dto;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
		var converter = new GenericConversionService();
		converter.addConverter(new CustomerMapperDTOImpl());
		converter.addConverter(new CustomerMapperImpl());
		
		service = new CustomerServiceImpl(repository, converter);
		
		customer = new Customer();
		customer.setId(ID);
		customer.setFirstName(FIRST_NAME);
		customer.setLastName(LAST_NAME);
		
		dto = new CustomerDTO();
		dto.setFirstName(FIRST_NAME);
		dto.setLastName(LAST_NAME);
	}
	
	@Test
	public void getAllCustomers() {
		//given
		var list = Arrays.asList(customer, new Customer());
		when(repository.findAll()).thenReturn(list);
		
		//when
		var dtoList = service.getAllCustomers();
		
		//then
		assertEquals(list.size(), dtoList.size());
	}
	
	@Test
	public void getCustomerById() {
		//given
		when(repository.findById(ID)).thenReturn(Optional.of(customer));
		
		//when
		var dtoOp = service.getCustomerById(ID);
		
		//then
		assertTrue(dtoOp.isPresent());
		var dto = dtoOp.get();
		assertEquals(FIRST_NAME, dto.getFirstName());
		assertEquals(LAST_NAME, dto.getLastName());
		assertEquals(CUSTOMER_URL, dto.getCustomerUrl());
	}
	
	@Test
	public void createNewCustomer() {
		//given
		when(repository.save(any())).thenReturn(customer);
		
		//when
		var data = service.createNewCustomer(dto);
		
		//then
		assertEquals(customer.getFirstName(), data.getFirstName());
		assertEquals(customer.getLastName(), data.getLastName());
		assertEquals(CUSTOMER_URL, data.getCustomerUrl());
	}
	
	@Test
	public void saveCustomerById() {
		//given
		when(repository.save(any())).thenReturn(customer);
		
		//when
		var data = service.saveCustomerById(ID, dto);
		
		//then
		assertEquals(customer.getFirstName(), data.getFirstName());
		assertEquals(customer.getLastName(), data.getLastName());
		assertEquals(CUSTOMER_URL, data.getCustomerUrl());
	}
	
	@Test
	public void deleteCustomerById() {
		service.deleteCustomerById(ID);
		
		verify(repository, times(1)).deleteById(ID);
	}
}