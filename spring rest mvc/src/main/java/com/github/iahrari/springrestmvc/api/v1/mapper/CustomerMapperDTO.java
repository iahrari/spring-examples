package com.github.iahrari.springrestmvc.api.v1.mapper;

import com.github.iahrari.springrestmvc.api.v1.model.CustomerDTO;
import com.github.iahrari.springrestmvc.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CustomerMapperDTO extends Converter<Customer, CustomerDTO> {
	@Mapping(source = "id", target = "customerUrl")
	CustomerDTO convert(final Customer customer);
}
