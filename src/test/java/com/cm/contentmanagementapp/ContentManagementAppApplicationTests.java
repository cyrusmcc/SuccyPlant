package com.cm.contentmanagementapp;

import com.cm.contentmanagementapp.user.User;
import com.cm.contentmanagementapp.user.UserService;
import com.cm.contentmanagementapp.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ContentManagementAppApplicationTests {

	private final UserService userService;

	@Autowired
	public  ContentManagementAppApplicationTests(UserService userService) {
		this.userService = userService;
	}

	@Test
	void contextLoads() {
	}

	@Test
	void addUserTest() {

		User testUser = new User();

		testUser.setUsername("testUsername");
		testUser.setPassword("password");

		userService.save(testUser);

		assert (userService.findByUsername("testUsername").equals(testUser));
	}

}
