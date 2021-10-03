package com.github.iahrari.springrestdocsexample.web.mapper;

import com.github.iahrari.springrestdocsexample.web.model.BeerDto;
import com.github.iahrari.springrestdocsexample.domain.Beer;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(uses = { DateMapper.class }, componentModel = "spring")
public interface BeerMapper extends Converter<BeerDto, Beer> {
    Beer convert(BeerDto beerDto);
}
