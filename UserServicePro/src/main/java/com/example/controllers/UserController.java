package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.entities.Contact;
import com.example.entities.User;
import com.example.services.UserService;

@RestController
public class UserController 
{
	@Autowired
	private UserService service;
	@Autowired
	private RestTemplate template;
	
	
	@GetMapping("/user/{userid}")
	public User getUser(@PathVariable("userid") int userid)
	{
		User user=service.getUser(userid);
		// RestTemplate helps you to invoke one service from another service
		// the only drawback is here "localhost" is hardcoded
		// so if there are thousands of services running on different
		// servers it will become difficult to keep a track of 
		// server names
		List<?> contacts=template.getForObject("http://CONTACTSERVICE/contact/"+user.getId(),List.class);
		user.setContacts(contacts);
		return user;
	}
}
