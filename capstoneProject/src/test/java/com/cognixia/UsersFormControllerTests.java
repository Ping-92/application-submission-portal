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
public class UsersFormControllerTests {
	@Autowired
	MockMvc mvc;

	@Test
	public void showUserForm() {
		try {
			mvc.perform(get("/users/userpage")).andExpect(status().is(200)).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void showLoginForm() {
		try {
			mvc.perform(get("/users/login")).andExpect(status().is(200)).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
