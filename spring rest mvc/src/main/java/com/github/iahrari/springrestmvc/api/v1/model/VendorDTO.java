package com.github.iahrari.springrestmvc.api.v1.model;

import com.github.iahrari.springrestmvc.controllers.v1.VendorController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO implements BaseDTO {
	public static final String dtoPluralName = "vendors";
	
	private String name;
	private String vendorUrl;
	
	public void setVendorUrl(String id){
		if(id == null) return;
		vendorUrl = VendorController.BASE_URL + "/" + id;
	}
}
