package com.github.iahrari.springrestmvc.controllers.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iahrari.springrestmvc.controllers.RestResponseEntityExceptionHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public abstract class AbstractRestControllerTest {
	public static String asJson(final Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}
	
	public static MockMvc standAloneMockWithAdvice(final Object obj){
		return MockMvcBuilders.standaloneSetup(obj)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}
}
