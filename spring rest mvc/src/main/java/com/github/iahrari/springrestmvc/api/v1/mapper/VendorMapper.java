package com.github.iahrari.springrestmvc.api.v1.mapper;

import com.github.iahrari.springrestmvc.api.v1.model.VendorDTO;
import com.github.iahrari.springrestmvc.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface VendorMapper extends Converter<VendorDTO, Vendor> {
	@Mapping(target = "id", ignore = true)
	Vendor convert(final VendorDTO dto);
}
