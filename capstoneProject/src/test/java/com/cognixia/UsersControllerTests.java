package com.cognixia;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
	public void getAllUsers()
	{
		try {
			mvc.perform(get("/users"))
			.andExpect(status().is(200))
		.andDo(print());
		} catch (Exception e) {
		e.printStackTrace();
		}
	}
	

	@Test
	public void getUsersByID()
	{
	try {
			mvc.perform(get("/users/user/1"))
			.andExpect(status().is(200))
			.andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void verfiyNotFoundUser()
	{
		try {
			mvc.perform(get("/users/user/232"))
			.andExpect(status().is(404))
			.andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void addUsers()
	{
		
		Users u = new Users();
		u.setUserName("Testing");
		u.setPassword("Password");
		u.setMobilePhone("111111");
		u.setEmail("test@gmail.com");
		u.setLastLogin(LocalDateTime.now());
		
		
		ObjectMapper om = new ObjectMapper();
		om.registerModule(new JavaTimeModule());
		om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		try {
			mvc.perform(post("/users/user").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(u)))
			.andExpect(status().is(201))
			.andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}


}}