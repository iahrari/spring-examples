package com.github.iahrari.springrestmvc.api.v1.mapper;

import com.github.iahrari.springrestmvc.api.v1.model.CategoryDTO;
import com.github.iahrari.springrestmvc.api.v1.model.CustomerDTO;
import com.github.iahrari.springrestmvc.api.v1.model.VendorDTO;
import com.github.iahrari.springrestmvc.controllers.v1.CustomerController;
import com.github.iahrari.springrestmvc.controllers.v1.VendorController;
import com.github.iahrari.springrestmvc.domain.Category;
import com.github.iahrari.springrestmvc.domain.Customer;
import com.github.iahrari.springrestmvc.domain.Vendor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MappersTest {
	private final Long ID = 1L;
	
	private final String FIRST_NAME = "Iman";
	private final String LAST_NAME = "Ahrari";
	public static final String CATEGORY = "Something";
	
	private final String CUSTOMER_URL = CustomerController.BASE_URL + "/" + ID;
	private final String VENDOR_URL = VendorController.BASE_URL + "/" + ID;
	
	private ConversionService conversion;
	
	@BeforeEach
	void setUp() {
		conversion = new GenericConversionService();
		((GenericConversionService) conversion)
				.addConverter(new CategoryDTOMapperImpl());
		((GenericConversionService) conversion)
				.addConverter(new CustomerMapperDTOImpl());
		((GenericConversionService) conversion)
				.addConverter(new CustomerMapperImpl());
		((GenericConversionService) conversion)
				.addConverter(new VendorDTOMapperImpl());
		((GenericConversionService) conversion)
				.addConverter(new VendorMapperImpl());
	}
	
	@Test
	void categoryToDtoMapper() {
		var category = new Category();
		category.setId(ID);
		category.setName(CATEGORY);
		
		CategoryDTO dto = conversion.convert(category, CategoryDTO.class);
		
		assert dto != null;
		assertEquals(ID, dto.getId());
		assertEquals(CATEGORY, dto.getName());
	}
	
	@Test
	public void customerToDtoMapper() {
		//given
		var customer = new Customer();
		customer.setId(ID);
		customer.setFirstName(FIRST_NAME);
		customer.setLastName(LAST_NAME);
		
		//when
		var dto = conversion.convert(customer, CustomerDTO.class);
		
		//then
		assert dto != null;
		assertEquals(FIRST_NAME, dto.getFirstName());
		assertEquals(LAST_NAME, dto.getLastName());
		assertEquals(CUSTOMER_URL, dto.getCustomerUrl());
	}
	
	@Test
	public void dtoToCustomerMapper() {
		//given
		var dto = new CustomerDTO(
				FIRST_NAME,
				LAST_NAME,
				CUSTOMER_URL
		);
		
		//when
		var customer = conversion.convert(dto, Customer.class);
		
		//then
		assert customer != null;
		assertEquals(FIRST_NAME, customer.getFirstName());
		assertEquals(LAST_NAME, customer.getLastName());
		assertNull(customer.getId());
	}
	
	@Test
	public void vendorToDtoMapper(){
		//given
		var vendor = new Vendor(FIRST_NAME);
		vendor.setId(ID);
		
		//when
		var dto = conversion.convert(vendor, VendorDTO.class);
		
		//then
		assert dto != null;
		assertEquals(FIRST_NAME, dto.getName());
		assertEquals(VENDOR_URL, dto.getVendorUrl());
	}
	
	@Test
	public void dtoToVendorMapper() {
		//given
		var dto = new VendorDTO(
				FIRST_NAME,
				VENDOR_URL
		);
		
		//when
		var vendor = conversion.convert(dto, Vendor.class);
		
		//then
		assert vendor != null;
		assertEquals(FIRST_NAME, vendor.getName());
		assertNull(vendor.getId());
	}
}
