package com.github.iahrari.springrestmvc.service;

import com.github.iahrari.springrestmvc.api.v1.model.VendorDTO;

import java.util.List;
import java.util.Optional;

public interface VendorService {
	List<VendorDTO> getAllVendors();
	Optional<VendorDTO> getVendorById(Long id);
	
	VendorDTO createNewVendor(VendorDTO vendor);
	VendorDTO updateVendorById(Long id, VendorDTO vendor);
	Optional<VendorDTO> patchVendorById(Long id, VendorDTO vendor);
	
	void deleteVendorById(Long id);
}
