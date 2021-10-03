package com.github.iahrari.springrestmvc.service;

import com.github.iahrari.springrestmvc.api.v1.model.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
	List<CustomerDTO> getAllCustomers();
	Optional<CustomerDTO> getCustomerById(Long id);
	CustomerDTO createNewCustomer(CustomerDTO customer);
	CustomerDTO saveCustomerById(Long id, CustomerDTO customer);
	Optional<CustomerDTO> patchCustomerById(Long id, CustomerDTO customer);
	void deleteCustomerById(Long id);
}
