package com.github.iahrari.springrestmvc.service;

import com.github.iahrari.springrestmvc.api.v1.model.CustomerDTO;
import com.github.iahrari.springrestmvc.domain.Customer;
import com.github.iahrari.springrestmvc.repository.CustomerRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public record CustomerServiceImpl(
		CustomerRepository customerRepository,
		ConversionService converter
) implements CustomerService {
	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository.findAll().stream()
				.map((customer) -> converter.convert(customer, CustomerDTO.class))
				.toList();
	}
	
	@Override
	public Optional<CustomerDTO> getCustomerById(Long id) {
		return customerRepository.findById(id)
				.map((customer) -> converter.convert(customer, CustomerDTO.class));
	}
	
	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customer) {
		return saveAndReturnCustomer(Objects.requireNonNull(
				converter.convert(customer, Customer.class)
		));
	}
	
	private CustomerDTO saveAndReturnCustomer(Customer customer) {
		var savedCustomer = customerRepository.save(customer);
		return converter.convert(savedCustomer, CustomerDTO.class);
	}
	
	@Override
	public CustomerDTO saveCustomerById(Long id, CustomerDTO customer) {
		var customerRegular = Objects.requireNonNull(
				converter.convert(customer, Customer.class));
		customerRegular.setId(id);
		return saveAndReturnCustomer(customerRegular);
	}
	
	@Override
	public Optional<CustomerDTO> patchCustomerById(Long id, CustomerDTO customer) {
		return customerRepository.findById(id).map((c) -> {
			c.setLastName((customer.getLastName() != null)?
					customer.getLastName(): c.getLastName());
			c.setFirstName((customer.getFirstName() != null)?
					customer.getFirstName(): c.getFirstName());
			return saveAndReturnCustomer(c);
		});
	}
	
	@Override
	public void deleteCustomerById(Long id) {
		customerRepository.deleteById(id);
	}
}
