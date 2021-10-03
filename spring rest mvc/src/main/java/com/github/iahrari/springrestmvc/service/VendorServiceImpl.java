package com.github.iahrari.springrestmvc.service;

import com.github.iahrari.springrestmvc.api.v1.model.VendorDTO;
import com.github.iahrari.springrestmvc.domain.Vendor;
import com.github.iahrari.springrestmvc.repository.VendorRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public record VendorServiceImpl(
		VendorRepository repository,
		ConversionService converter
) implements VendorService {
	@Override
	public List<VendorDTO> getAllVendors() {
		return repository.findAll().stream()
				.map((vendor) -> converter.convert(vendor, VendorDTO.class))
				.toList();
	}
	
	@Override
	public Optional<VendorDTO> getVendorById(Long id) {
		return repository.findById(id)
				.map((vendor) -> converter.convert(vendor, VendorDTO.class));
	}
	
	public VendorDTO saveAndReturn(Vendor vendor){
		return converter.convert(
				repository.save(vendor),
				VendorDTO.class
		);
	}
	
	@Override
	public VendorDTO createNewVendor(VendorDTO vendor) {
		return saveAndReturn(
				converter.convert(vendor, Vendor.class)
		);
	}
	
	@Override
	public VendorDTO updateVendorById(Long id, VendorDTO vendor) {
		var rVendor = Objects.requireNonNull(
				converter.convert(vendor, Vendor.class));
		rVendor.setId(id);
		return saveAndReturn(rVendor);
	}
	
	@Override
	public Optional<VendorDTO> patchVendorById(Long id, VendorDTO vendor) {
		return Optional.of(updateVendorById(id, vendor));
	}
	
	@Override
	public void deleteVendorById(Long id) {
		repository.deleteById(id);
	}
}
