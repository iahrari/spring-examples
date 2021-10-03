package com.github.iahrari.springrestmvc.api.v1.mapper;

import com.github.iahrari.springrestmvc.api.v1.model.CustomerDTO;
import com.github.iahrari.springrestmvc.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends Converter<CustomerDTO, Customer> {
	
	@Mapping(target = "id", ignore = true)
	Customer convert(final CustomerDTO dto);
}
