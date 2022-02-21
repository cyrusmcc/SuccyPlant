package com.cm.contentmanagementapp;

import com.cm.contentmanagementapp.controllers.AuthController;
import com.cm.contentmanagementapp.models.*;
import com.cm.contentmanagementapp.payload.request.SignupRequest;
import com.cm.contentmanagementapp.payload.response.MessageResponse;
import com.cm.contentmanagementapp.repositories.GalleryPostRepository;
import com.cm.contentmanagementapp.repositories.PostRepository;
import com.cm.contentmanagementapp.services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
	GalleryPostRepository gPRepo;

	@Autowired
	PostService postService;


	@Autowired
	ContentTagService contentTagService;

	@Autowired
	GalleryPostRepository gpRepo;

	@Autowired
	PostRepository postRepo;

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
	void tags() {

		contentTagService.newTag("House Plant", EnumTagCategory.TYPE);
		contentTagService.newTag("Succ", EnumTagCategory.TYPE);
		contentTagService.newTag("Cactus", EnumTagCategory.TYPE);
		contentTagService.newTag("Small", EnumTagCategory.SIZE);
		contentTagService.newTag("Medium", EnumTagCategory.SIZE);
		contentTagService.newTag("Large", EnumTagCategory.SIZE);
		contentTagService.newTag("Beginner Friendly", EnumTagCategory.DIFFICULTY);
		contentTagService.newTag("Advanced", EnumTagCategory.DIFFICULTY);
		contentTagService.newTag("Low", EnumTagCategory.LIGHT);
		contentTagService.newTag("Medium", EnumTagCategory.LIGHT);
		contentTagService.newTag("High", EnumTagCategory.LIGHT);
		contentTagService.newTag("Yes", EnumTagCategory.PET);
		contentTagService.newTag("No", EnumTagCategory.PET);

	}

	@Test
	@Transactional
	@Rollback(value = false)
	void galleryPostTest() {

		GalleryPost post = new GalleryPost();
		post.setTitle("Aloe jucunda");
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.GENUS,"Aloe"));

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
		tags.add(contentTagService.findByCategoryAndValue( EnumTagCategory.GENUS, "Aeonium"));

		List<GalleryPost> test = galleryPostService.findAllByContentTagsAndSearchTerm(0, 10,
				tags, "");

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

	@Test
	void findGalleryPostByPostTitleLike() {

		Pageable paging = PageRequest.of(0, 5, Sort.by("id").descending());
		Slice<GalleryPost> slice = gpRepo.findAllByPostTitleContainingIgnoreCase("aloe", paging);
		assert slice.getContent().size() == 1;

		slice = gpRepo.findAllByPostTitleContainingIgnoreCase("ale", paging);
		assert slice.getContent().size() == 0;

	}



}
