package com.github.iahrari.springrestdocsexample.web.mapper;

import com.github.iahrari.springrestdocsexample.web.model.BeerDto;
import com.github.iahrari.springrestdocsexample.domain.Beer;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring", uses = { DateMapper.class })
public interface BeerDtoMapper extends Converter<Beer, BeerDto> {
    BeerDto convert(Beer beer);
}

