package com.github.iahrari.springrestmvc.service;

import com.github.iahrari.springrestmvc.api.v1.mapper.VendorDTOMapperImpl;
import com.github.iahrari.springrestmvc.api.v1.mapper.VendorMapperImpl;
import com.github.iahrari.springrestmvc.api.v1.model.VendorDTO;
import com.github.iahrari.springrestmvc.controllers.v1.VendorController;
import com.github.iahrari.springrestmvc.domain.Vendor;
import com.github.iahrari.springrestmvc.repository.VendorRepository;
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

class VendorServiceTest {
	private static final Long ID = 1L;
	private final String NAME = "Name";
	private final String VENDOR_URL = VendorController.BASE_URL + "/" + ID;
	@Mock
	private VendorRepository repository;
	
	private VendorService service;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		var conversation = new GenericConversionService();
		conversation.addConverter(new VendorMapperImpl());
		conversation.addConverter(new VendorDTOMapperImpl());
		service = new VendorServiceImpl(repository, conversation);
	}
	
	@Test
	void getAllVendors() {
		//given
		var list = Arrays.asList(new Vendor(), new Vendor(), new Vendor());
		when(repository.findAll()).thenReturn(list);
		
		//when
		var returnedList = service.getAllVendors();
		
		//then
		assertEquals(list.size(), returnedList.size());
	}
	
	@Test
	void getVendorById() {
		//given
		var vendor = new Vendor();
		vendor.setId(ID);
		vendor.setName(NAME);
		
		when(repository.findById(ID)).thenReturn(Optional.of(vendor));
		
		//when
		var returnedVendor = service.getVendorById(ID);
		
		//then
		assertTrue(returnedVendor.isPresent());
		assertEquals(VENDOR_URL, returnedVendor.get().getVendorUrl());
		assertEquals(NAME, returnedVendor.get().getName());
	}
	
	@Test
	void createNewVendor() {
		//when
		var vendor = new Vendor();
		vendor.setId(ID);
		vendor.setName(NAME);
		when(repository.save(any())).thenReturn(vendor);
		
		//when
		var returnedVendor = service.createNewVendor(new VendorDTO(NAME, null));
		
		//then
		assertEquals(NAME, returnedVendor.getName());
		assertEquals(VENDOR_URL, returnedVendor.getVendorUrl());
	}
	
	@Test
	void updateVendorById() {
		//when
		var vendor = new Vendor();
		vendor.setId(ID);
		vendor.setName(NAME);
		when(repository.save(any())).thenReturn(vendor);
		
		//when
		var returnedVendor = service.updateVendorById(
				ID, new VendorDTO(NAME, null)
		);
		
		//then
		assertEquals(NAME, returnedVendor.getName());
		assertEquals(VENDOR_URL, returnedVendor.getVendorUrl());
	}
	
	@Test
	void deleteVendorById() {
		service.deleteVendorById(ID);
		verify(repository, times(1)).deleteById(ID);
	}
}