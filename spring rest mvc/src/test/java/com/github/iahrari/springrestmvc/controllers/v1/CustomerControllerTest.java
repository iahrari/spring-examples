package com.github.iahrari.springrestmvc.controllers.v1;

import com.github.iahrari.springrestmvc.api.v1.model.CustomerDTO;
import com.github.iahrari.springrestmvc.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static com.github.iahrari.springrestmvc.controllers.v1.AbstractRestControllerTest.asJson;
import static com.github.iahrari.springrestmvc.controllers.v1.AbstractRestControllerTest.standAloneMockWithAdvice;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {
	public static final String JOHN = "John";
	private final Long ID = 1L;
	private final String FIRST_NAME = "Iman";
	private final String LAST_NAME = "Ahrari";
	private final String CUSTOMER_URL = CustomerController.BASE_URL + "/" + ID;
	
	@Mock
	private CustomerService service;
	
	@InjectMocks
	private CustomerController controller;
	
	private MockMvc mock;
	private CustomerDTO dto;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mock = standAloneMockWithAdvice(controller);
		
		dto = new CustomerDTO();
		dto.setFirstName(FIRST_NAME);
		dto.setLastName(LAST_NAME);
	}
	
	@Test
	void getAllCustomers() throws Exception {
		//given
		var list = Arrays.asList(new CustomerDTO(), new CustomerDTO());
		when(service.getAllCustomers())
				.thenReturn(list);
		
		mock.perform(get(CustomerController.BASE_URL + "/")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.customers", hasSize(2)));
		
	}
	
	@Test
	void getCustomerDTO() throws Exception {
		//given
		dto.setFirstName(JOHN);
		when(service.getCustomerById(anyLong())).thenReturn(Optional.of(dto));
		
		//when
		mock.perform(get(CustomerController.BASE_URL + "/1")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", equalTo(JOHN)));
	}
	
	@Test
	public void getCustomerByWrongId() throws Exception {
		when(service.getCustomerById(anyLong())).thenReturn(Optional.empty());
		
		//when
		mock.perform(get(CustomerController.BASE_URL + "/1")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error", containsString(CustomerController.CUSTOMER_NOT_FOUND)));
	}
	
	@Test
	void createNewCustomer() throws Exception {
		//given
		var returnedDto = new CustomerDTO(
				FIRST_NAME,
				LAST_NAME,
				CUSTOMER_URL
		);
		when(service.createNewCustomer(dto)).thenReturn(returnedDto);
		
		mock.perform(post(CustomerController.BASE_URL)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJson(dto)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
				.andExpect(jsonPath("$.customerUrl", equalTo(CUSTOMER_URL)));
	}
	
	@Test
	void saveCustomerById() throws Exception {
		//given
		var returnedDto = new CustomerDTO(
				FIRST_NAME,
				LAST_NAME,
				CUSTOMER_URL
		);
		when(service.saveCustomerById(ID, dto)).thenReturn(returnedDto);
		
		mock.perform(put(CustomerController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(asJson(dto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
				.andExpect(jsonPath("$.customerUrl", equalTo(CUSTOMER_URL)));
	}
	
	@Test
	void testPatchCustomer() throws Exception {
		//given
		var customer = new CustomerDTO();
		customer.setFirstName("Fred");
		
		var returnedDto = new CustomerDTO(
				customer.getFirstName(),
				LAST_NAME,
				CUSTOMER_URL
		);
		
		when(service.patchCustomerById(anyLong(), any()))
				.thenReturn(Optional.of(returnedDto));
		
		//when
		mock.perform(patch(CustomerController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(asJson(customer)))
				//then
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", equalTo("Fred")))
				.andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
				.andExpect(jsonPath("$.customerUrl", equalTo(CUSTOMER_URL)));
	}
	
	@Test
	public void patchCustomerByWrongId() throws Exception {
		var customer = new CustomerDTO();
		customer.setFirstName("Fred");
		when(service.patchCustomerById(anyLong(), any())).thenReturn(Optional.empty());
		
		//when
		mock.perform(patch(CustomerController.BASE_URL + "/1")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(asJson(customer)))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath(
						"$.error",
						containsString(CustomerController.CUSTOMER_NOT_FOUND)
				));
	}
	
	@Test
	public void deleteCustomerById() throws Exception {
		mock.perform(delete(CustomerController.BASE_URL + "/1"))
				.andExpect(status().isOk());
		
		verify(service, times(1)).deleteCustomerById(ID);
	}
}