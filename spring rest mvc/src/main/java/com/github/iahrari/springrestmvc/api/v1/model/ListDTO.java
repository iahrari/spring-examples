package com.github.iahrari.springrestmvc.api.v1.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public record ListDTO<T extends BaseDTO>(
	@JacksonXmlElementWrapper(useWrapping = true)
		@JsonIgnore List<T> list,
		@JsonIgnore String name
) {
	
	@JsonAnyGetter
	public Map<String, List<T>> returnData(){
		return Collections.singletonMap(name, list);
	}
}
