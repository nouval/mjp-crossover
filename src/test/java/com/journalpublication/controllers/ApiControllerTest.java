package com.journalpublication.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.jayway.jsonpath.JsonPath;
import com.journalpublication.JournalPublicationApplication;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {JournalPublicationApplication.class})
@WebAppConfiguration
public class ApiControllerTest {

	private MockMvc mockMvc;
	
	private MediaType contentTypeJSON = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
	
	private String invalidToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdWJAZ21haWwuY29tIiwiZXhwIjoxNDYwOTI1Njk0fQ.__bk1Ig-PGoi69XnDl3HF9WyuS7G5BBprdU1ByKz9sn-T9wceVtD1wcb5HlvQppesN1lXy0HJNtqifN-jMJ5Kw";

	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void test_Login_success() throws Exception {

		String username = "sub@gmail.com", password = "12345", 
				apiKey = "N2ZkNjQwY2ItZGE4ZC00NjBmLThjODQtMzhkZjMyM2Q2YTFl";

		mockMvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", username)
				.param("password", password)
				.param("apiKey", apiKey))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentTypeJSON))
		.andExpect(jsonPath("$.username", is(username)))
		.andExpect(jsonPath("$.token", notNullValue()));
	}
	
	@Test
	public void test_Login_failed_wrong_email_and_or_password() throws Exception {
		
		String username = "sub@gmail.com", wrongPassword = "123", 
				apiKey = "N2ZkNjQwY2ItZGE4ZC00NjBmLThjODQtMzhkZjMyM2Q2YTFl";
		
		mockMvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", username)
				.param("password", wrongPassword)
				.param("apiKey", apiKey))
		.andExpect(status().isNotFound())
		.andExpect(content().contentType(contentTypeJSON))
		.andExpect(jsonPath("$.username", is("sub@gmail.com")))
		.andExpect(jsonPath("$.token", nullValue()));
	}
	
	private String[] loginAndGetToken() throws Exception {

		String username = "sub@gmail.com", password = "12345", 
				apiKey = "N2ZkNjQwY2ItZGE4ZC00NjBmLThjODQtMzhkZjMyM2Q2YTFl";
		
		MvcResult result = mockMvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", username)
				.param("password", password)
				.param("apiKey", apiKey))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentTypeJSON))
				.andExpect(jsonPath("$.username", is(username)))
				.andExpect(jsonPath("$.token", notNullValue()))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		String token = JsonPath.read(content, "$.token");
		assertNotNull(token);
		
		return new String[] {username, token};
	}
	
	@Test
	public void test_Subscrition_success() throws Exception {
		
		String[] usernameAndToken = this.loginAndGetToken();
		
		mockMvc.perform(get("/api/subscriptions")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-AUTH-TOKEN", usernameAndToken[1]))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(content().contentType(contentTypeJSON));
	}
	
	@Test
	public void test_Subscrition_failed_invalid_token() throws Exception {

		mockMvc.perform(get("/api/subscriptions")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-AUTH-TOKEN", invalidToken))
				.andExpect(status().isUnauthorized());
	}
	
	
	@Test
	public void test_Journal_success() throws Exception {

		String[] usernameAndToken = this.loginAndGetToken();
		
		MvcResult result = mockMvc.perform(get("/api/subscriptions")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-AUTH-TOKEN", usernameAndToken[1]))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(content().contentType(contentTypeJSON))
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		int journalId = JsonPath.read(content, "$[0].id");

		result = mockMvc.perform(get("/api/journal/" + journalId)
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-AUTH-TOKEN", usernameAndToken[1]))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.filename", notNullValue()))
				.andReturn();
	}
	
	@Test
	public void test_Journal_failed_invalid_token() throws Exception {

		mockMvc.perform(get("/api/subscriptions")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-AUTH-TOKEN", invalidToken))
				.andExpect(status().isUnauthorized());
	}

	
	@Test
	public void test_Validate_success() throws Exception {

		String[] usernameAndToken = this.loginAndGetToken();

		mockMvc.perform(get("/api/validate")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-AUTH-TOKEN", usernameAndToken[1]))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email", is(usernameAndToken[0])))
				.andExpect(jsonPath("$.type", is("sub")));
	}
	
	@Test
	public void test_Validate_failed_invalid_token() throws Exception {

		mockMvc.perform(get("/api/validate")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-AUTH-TOKEN", invalidToken))
				.andExpect(status().isUnauthorized());
	}
}
