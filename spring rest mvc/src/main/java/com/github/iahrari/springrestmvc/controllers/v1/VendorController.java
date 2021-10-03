package com.github.iahrari.springrestmvc.controllers.v1;

import com.github.iahrari.springrestmvc.api.v1.model.ListDTO;
import com.github.iahrari.springrestmvc.api.v1.model.VendorDTO;
import com.github.iahrari.springrestmvc.service.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping(VendorController.BASE_URL)
public record VendorController(VendorService service) {
	public static final String BASE_URL = "/api/v1/vendors";
	public static final String VENDOR_NOT_FOUND = "Vendor doesn't exists";
	
	@GetMapping({"", "/"})
	@ResponseStatus(HttpStatus.OK)
	public ListDTO<VendorDTO> getAllVendors() {
		return new ListDTO<>(
				service.getAllVendors(),
				VendorDTO.dtoPluralName
		);
	}
	
	@GetMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO getVendorById(@PathVariable("id") Long id) {
		return service.getVendorById(id)
				.orElseThrow(() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						VENDOR_NOT_FOUND
				));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VendorDTO createNewVendor(@RequestBody VendorDTO dto){
		return service.createNewVendor(dto);
	}
	
	@PutMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO updateVendorById(
			@PathVariable("id") Long id,
			@RequestBody VendorDTO dto
	){
		return service.updateVendorById(id, dto);
	}
	
	@PatchMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO patchVendorById(
			@PathVariable("id") Long id,
			@RequestBody VendorDTO dto
	){
		return service.patchVendorById(id, dto)
				.orElseThrow(() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						VENDOR_NOT_FOUND
				));
	}
	
	@DeleteMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public void deleteVendorById(@PathVariable("id") Long id){
		service.deleteVendorById(id);
	}
}
