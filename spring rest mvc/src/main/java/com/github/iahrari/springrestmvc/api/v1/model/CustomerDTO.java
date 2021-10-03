package com.github.iahrari.springrestmvc.api.v1.model;

import com.github.iahrari.springrestmvc.controllers.v1.CustomerController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO implements BaseDTO {
	public static final String dtoPluralName = "customers";
	
	private String firstName;
	private String lastName;
	private String customerUrl;
	
	public void setCustomerUrl(String id){
		if (id == null) return;
		customerUrl = CustomerController.BASE_URL + "/" + id;
	}
}
