package com.github.iahrari.springrestmvc.api.v1.model;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("category")
public class CategoryDTO implements BaseDTO {
	public static final String dtoPluralName = "categories";
	private Long id;
	private String name;
}
