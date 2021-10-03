package com.github.iahrari.springrestmvc.controllers.v1;

import com.github.iahrari.springrestmvc.api.v1.model.CustomerDTO;
import com.github.iahrari.springrestmvc.api.v1.model.ListDTO;
import com.github.iahrari.springrestmvc.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public record CustomerController(
	CustomerService customerService
) {
	public static final String BASE_URL = "/api/v1/customers";
	public static final String CUSTOMER_NOT_FOUND = "Customer doesn't exists";
	
	@GetMapping({"", "/"})
	@ResponseStatus(HttpStatus.OK)
	public ListDTO<CustomerDTO> getAllCustomers() {
		return new ListDTO<>(customerService.getAllCustomers(), CustomerDTO.dtoPluralName);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO getCustomerDTO(@PathVariable Long id) {
		return customerService.getCustomerById(id)
				.orElseThrow(() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						CUSTOMER_NOT_FOUND
				));
	}
	
	@PostMapping({"", "/"})
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customer){
		return customerService.createNewCustomer(customer);
	}
	
	@PutMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO saveCustomerById(
			@RequestBody CustomerDTO customer,
			@PathVariable Long id
	){
		return customerService.saveCustomerById(id, customer);
	}
	
	@PatchMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO patchCustomerById(
			@RequestBody CustomerDTO customer,
			@PathVariable Long id
	){
		return customerService
				.patchCustomerById(id, customer)
				.orElseThrow(() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						CUSTOMER_NOT_FOUND
				));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteCustomerById(@PathVariable Long id) {
		customerService.deleteCustomerById(id);
	}
}
