package com.github.iahrari.springrestmvc.api.v1.mapper;

import com.github.iahrari.springrestmvc.api.v1.model.CategoryDTO;
import com.github.iahrari.springrestmvc.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CategoryDTOMapper extends Converter<Category, CategoryDTO> {
	@Mapping(source = "id", target = "id")
	CategoryDTO convert(final Category category);
}
