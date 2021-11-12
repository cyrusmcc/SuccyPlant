package com.cm.contentmanagementapp;

import com.cm.contentmanagementapp.controllers.AuthController;
import com.cm.contentmanagementapp.models.*;
import com.cm.contentmanagementapp.payload.request.SignupRequest;
import com.cm.contentmanagementapp.payload.response.MessageResponse;
import com.cm.contentmanagementapp.repositories.GalleryPostRepository;
import com.cm.contentmanagementapp.services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

	@Autowired
	GalleryPostRepository gpRepo;

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

		contentTagService.newTag("Aloe", EnumTagCategory.GENUS);

	}

	@Test
	@Transactional
	@Rollback(value = false)
	void galleryPostTest() {

		GalleryPost post = new GalleryPost();
		post.setTitle("Aloe jucunda");
		post.getPost().addTag(contentTagService.findByValueAndCategory("Aloe", EnumTagCategory.GENUS));

		galleryPostService.save(post);

	}

	@Test
	void findContentTagByCategoryAndValue() {

		List<ContentTag> tags = contentTagService.findContentTagsByCategoryAndValue(EnumTagCategory.GENUS, "Aeonium");

		for (ContentTag t : tags) {
			System.out.println(t.getValue());
		}

	}

	@Test
	void findGalleryPostByContentTagValueAndCategory() {

		List<ContentTag> tags = new ArrayList<>();
		tags.add(contentTagService.findByValueAndCategory("Aeonium", EnumTagCategory.GENUS));

		List<GalleryPost> test = galleryPostService.findAllByContentTags(0, 10,
				tags);

		for (GalleryPost gp : test) {
			System.out.println(gp.getPost().getTitle());
		}

	}

	@Test
	void findAllGalleryPosts() {

		List<GalleryPost> test = galleryPostService.findAllByAlphabetical(0, 10);

		for (GalleryPost gp : test) {
			System.out.println(gp.getPost().getTitle());
		}
	}

}
