package com.ingenico.assignment.ingenicoassignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingenico.assignment.ingenicoassignment.controller.AccountService;
import com.ingenico.assignment.ingenicoassignment.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(AccountService.class)
public class AccountServiceTest {

	@Autowired
	private AccountService service;

	@Autowired
	private MockRestServiceServer server;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void test() throws Exception {
		String json = objectMapper.writeValueAsString(new LinkedList<Account>());

		this.server.expect(requestTo("/account/list"))
				.andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

		List<Account> list = this.service.getAllAccounts();
	}

}
