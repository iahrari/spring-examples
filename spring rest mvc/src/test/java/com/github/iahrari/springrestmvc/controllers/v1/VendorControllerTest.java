package com.github.iahrari.springrestmvc.controllers.v1;

import com.github.iahrari.springrestmvc.api.v1.model.VendorDTO;
import com.github.iahrari.springrestmvc.service.VendorService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VendorControllerTest {
	private static final Long ID = 1L;
	private final String NAME = "Name";
	private final String VENDOR_URL = VendorController.BASE_URL + "/" + ID;
	
	@Mock
	private VendorService service;
	
	@InjectMocks
	private VendorController controller;
	
	MockMvc mock;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mock = standAloneMockWithAdvice(controller);
	}
	
	@Test
	void getAllVendors() throws Exception {
		//given
		var list = Arrays.asList(new VendorDTO(), new VendorDTO());
		when(service.getAllVendors())
				.thenReturn(list);
		
		mock.perform(get(VendorController.BASE_URL + "/")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.vendors", hasSize(2)));
	}
	
	@Test
	void getVendorById() throws Exception {
		var dto = new VendorDTO(NAME, VENDOR_URL);
		when(service.getVendorById(ID))
				.thenReturn(Optional.of(dto));
		
		mock.perform(get(VendorController.BASE_URL + "/1")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo(NAME)));
		
	}
	
	@Test
	void getVendorByWrongId() throws Exception {
		when(service.getVendorById(ID))
				.thenReturn(Optional.empty());
		
		mock.perform(get(VendorController.BASE_URL + "/1")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error", containsString(VendorController.VENDOR_NOT_FOUND)));
	}
	
	@Test
	void createNewVendor() throws Exception {
		var dto = new VendorDTO(NAME, null);
		var returnedDto = new VendorDTO(
				NAME,
				VENDOR_URL
		);
		when(service.createNewVendor(dto))
				.thenReturn(returnedDto);
		
		mock.perform(post(VendorController.BASE_URL)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(asJson(dto)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", equalTo(NAME)))
				.andExpect(jsonPath("$.vendorUrl", equalTo(VENDOR_URL)));
		
	}
	
	@Test
	void updateVendorById() throws Exception {
		var dto = new VendorDTO(NAME, null);
		var returnedDto = new VendorDTO(
				NAME,
				VENDOR_URL
		);
		when(service.updateVendorById(ID, dto))
				.thenReturn(returnedDto);
		
		mock.perform(put(VendorController.BASE_URL + "/1")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(asJson(dto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo(NAME)))
				.andExpect(jsonPath("$.vendorUrl", equalTo(VENDOR_URL)));
		
	}
	
	@Test
	void patchVendorById() throws Exception {
		var dto = new VendorDTO(NAME, null);
		var returnedDto = new VendorDTO(
				NAME,
				VENDOR_URL
		);
		when(service.patchVendorById(ID, dto))
				.thenReturn(Optional.of(returnedDto));
		
		mock.perform(patch(VendorController.BASE_URL + "/1")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(asJson(dto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo(NAME)))
				.andExpect(jsonPath("$.vendorUrl", equalTo(VENDOR_URL)));
		
	}
	
	@Test
	void patchVendorByWrongId() throws Exception {
		var dto = new VendorDTO(NAME, null);
		when(service.patchVendorById(ID, dto))
				.thenReturn(Optional.empty());
		
		mock.perform(patch(VendorController.BASE_URL + "/1")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(asJson(dto)))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath(
						"$.error",
						containsString(VendorController.VENDOR_NOT_FOUND)));
	}
	
	@Test
	void deleteVendorById() throws Exception {
		mock.perform(delete(VendorController.BASE_URL + "/" + ID))
				.andExpect(status().isOk());
		
		verify(service, times(1)).deleteVendorById(ID);
	}
}