package com.github.iahrari.springrestmvc.api.v1.mapper;

import com.github.iahrari.springrestmvc.api.v1.model.VendorDTO;
import com.github.iahrari.springrestmvc.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface VendorDTOMapper extends Converter<Vendor, VendorDTO> {
	@Mapping(source = "id", target = "vendorUrl")
	VendorDTO convert(final Vendor vendor);
}
