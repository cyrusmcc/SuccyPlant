package com.cm.contentmanagementapp;

import com.cm.contentmanagementapp.controllers.AuthController;
import com.cm.contentmanagementapp.models.*;
import com.cm.contentmanagementapp.payload.request.SignupRequest;
import com.cm.contentmanagementapp.payload.response.MessageResponse;
import com.cm.contentmanagementapp.services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class ContentManagementAppApplicationTests {

	private MockMvc mockMvc;

	private final UserService userService;

	@Autowired
	public  ContentManagementAppApplicationTests(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	AuthController authController;

	@Autowired
    BlogPostService blogPostService;

	@Autowired
	GalleryPostService galleryPostService;

	@Autowired
	PostService postService;


	@Autowired
	ContentTagService contentTagService;

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

	@Test
	void authApiSignupTest() {

		ResponseEntity<?> responseEntity = ResponseEntity.ok(new MessageResponse("User registered successfully."));

		SignupRequest  request = new SignupRequest();
		request.setUsername("signupTest");
		request.setPassword("pass");

		assert (authController.registerUser(request) == responseEntity);

	}

	@Test
	void blogSliceTest() {

		List<BlogPost> list = blogPostService.findAllBlogPosts(0,5);

		System.out.println(list.size());

		for (BlogPost b : list) {
			System.out.println(b.getId());
		}

	}

	@Test
	void newTag() {

		contentTagService.newTag(EnumTags.BLACK);

	}

	@Test
	@Transactional
	void postTagsTest() {

		Post post = new Post();
		ContentTag tag = contentTagService.findByValue(EnumTags.BLACK);

		post.setTitle("gallpostasdtest");

		postService.save(post);

		postService.addTag(post, tag);
		contentTagService.addPostToTag(post, tag);

		GalleryPost gp = new GalleryPost(post);
		galleryPostService.save(gp);

	}

}
