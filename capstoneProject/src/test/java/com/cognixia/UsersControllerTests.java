package com.cognixia;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@AutoConfigureMockMvc
@SpringBootTest

public class UsersControllerTests {

	@Autowired
	MockMvc mvc;

	@Test
	public void getAllUsers() {
		try {
			mvc.perform(get("/users")).andExpect(status().is(200)).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getUsersByID() {

		Users t = new Users();
		ObjectMapper om = new ObjectMapper();
		om.registerModule(new JavaTimeModule());
		om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		try {
			mvc.perform(get("/users/user/1")).andExpect(status().is(200)).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void verfiyNotFoundUsers() {
		try {
			mvc.perform(get("/users/user/123")).andExpect(status().is(404)).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addUser() {
		Users user = new Users();
		user.setUserName("Testing");
		user.setPassword("password");
		user.setMobilePhone("123456");
		user.setEmail("test@gmail.com");
		user.setLastLogin(LocalDateTime.now());

		ObjectMapper om = new ObjectMapper();
		om.registerModule(new JavaTimeModule());
		om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		try {
			mvc.perform(
					post("/users/user").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(user)))
					.andExpect(status().is(201)).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
    public void getCurrentUserId() {
		Users t = new Users();
		ObjectMapper om = new ObjectMapper();
		om.registerModule(new JavaTimeModule());
		om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		try {
			mvc.perform(get("/users/current_user")).andExpect(status().is(200)).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
