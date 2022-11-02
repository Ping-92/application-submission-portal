package com.cognixia;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class ApplicationControllerTests {

	@Autowired
	MockMvc mvc;

	@Test
	public void getAllApplications() {
		try {
			mvc.perform(get("/application")).andExpect(status().is(200)).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void verfiyNotFoundUsers() {
		try {
			mvc.perform(get("/applications")).andExpect(status().is(404)).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getApplicationById() {
		try {
			mvc.perform(get("/application/applicant/1")).andExpect(status().is(200)).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void verfiyNotFoundApplicationId() {
		try {
			mvc.perform(get("/application/applicant/232")).andExpect(status().is(404)).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
