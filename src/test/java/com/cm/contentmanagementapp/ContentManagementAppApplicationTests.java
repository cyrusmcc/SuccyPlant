package com.cm.contentmanagementapp;

import com.cm.contentmanagementapp.controllers.AuthController;
import com.cm.contentmanagementapp.models.*;
import com.cm.contentmanagementapp.payload.request.SignupRequest;
import com.cm.contentmanagementapp.payload.response.MessageResponse;
import com.cm.contentmanagementapp.repositories.CommentBookRepository;
import com.cm.contentmanagementapp.repositories.PlantRepository;
import com.cm.contentmanagementapp.repositories.PostCommentRepository;
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
import org.springframework.security.core.parameters.P;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	PlantService plantService;

	@Autowired
    PlantRepository gPRepo;

	@Autowired
	PostService postService;

	@Autowired
	ContentTagService contentTagService;

	@Autowired
    PlantRepository gpRepo;

	@Autowired
	PostRepository postRepo;

	@Autowired
	PostCommentRepository commentRepository;

	@Autowired
	CommentBookRepository commentBookRepository;

	@Autowired
	CommentBookService commentBookService;

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
		contentTagService.newTag("Beginner", EnumTagCategory.DIFFICULTY);
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

		Plant post = new Plant();
		post.setTitle("Aeonium aureum");
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.GENUS,"Aeonium"));
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.TYPE,"House Plant"));
		post.getPost().addTag(contentTagService
				.findByCategoryAndValue(EnumTagCategory.DIFFICULTY,"Beginner"));
		plantService.save(post);

		post = new Plant();
		post.setTitle("Aloe Vera");
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.GENUS,"Aloe"));
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.TYPE,"Succ"));
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.LIGHT,"Low"));
		post.getPost().addTag(contentTagService
				.findByCategoryAndValue(EnumTagCategory.DIFFICULTY,"Advanced"));
		plantService.save(post);

		post = new Plant();
		post.setTitle("Ferocactus Fordii");
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.GENUS,"Ferocactus"));
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.TYPE,"Cactus"));
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.PET,"Yes"));
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.LIGHT,"Low"));
		plantService.save(post);


		post = new Plant();
		post.setTitle("Aeonium ciliatum");
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.GENUS,"Aeonium"));
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.TYPE,"House Plant"));
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.PET,"Yes"));
		post.getPost().addTag(contentTagService
				.findByCategoryAndValue(EnumTagCategory.DIFFICULTY,"Beginner"));
		plantService.save(post);

		post = new Plant();
		post.setTitle("Monstera dubia");
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.GENUS,"Monstera"));
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.TYPE,"House Plant"));
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.LIGHT,"Medium"));
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.PET,"Yes"));
		plantService.save(post);

		post = new Plant();
		post.setTitle("Monstera obliqua");
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.GENUS,"Monstera"));
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.TYPE,"House Plant"));
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.LIGHT,"Low"));
		post.getPost().addTag(contentTagService.findByCategoryAndValue(EnumTagCategory.PET,"No"));
		plantService.save(post);


	}

	@Test
	void findContentTagByCategoryAndValue() {

		/* Filter posts by exact match of tag list */
		List<ContentTag> tags = contentTagService
				.findContentTagsByCategoryAndValue(EnumTagCategory.TYPE, "House Plant");
		List<Long> ids = new ArrayList<>();
		List<Plant> t = plantService
				.findAllByContentTagsAndSearchTerm(0, 10, tags, "");
		for (Plant ta : t) ids.add(ta.getId());
		System.out.println(ids);


		/* Filter posts by posts containing any tags in tag */
		List<ContentTag> tagsTwo = contentTagService
				.findContentTagsByCategoryAndValue(EnumTagCategory.WATER, "Medium");
		List<Plant> posts = gpRepo.findPlantsByIdInAndPostContentTagsIn(ids, tagsTwo);
		for (Plant gp : posts) {
			System.out.println(gp.getPost().getTitle());
		}

	}

	@Test
	void findGalleryPostByContentTagValueAndCategory() {

		List<ContentTag> tags = new ArrayList<>();
		tags.add(contentTagService.findByCategoryAndValue( EnumTagCategory.GENUS, "Aeonium"));
		//tags.add(contentTagService.findByCategoryAndValue( EnumTagCategory.WATER, "Medium"));
		//Pageable paging = PageRequest.of(0, 5, Sort.by("id").descending());

		/*
		List<Post> test = postRepo.custom(tags, tags.size(), paging);

		for (Post o : test) System.out.println(o.getTitle());*/
		 List<Plant> test = plantService.findAllByContentTagsAndSearchTerm(0, 10,
				tags, "");

		/*
		List<GalleryPost> t = test.stream().filter(p -> p.getPost().getTitle().contains("aureum"))
				.collect(Collectors.toList());
		for (GalleryPost i : t) System.out.println(i.getPost().getTitle());

		System.out.println("-------");
		 */
		for (Plant gp : test) {
			System.out.println(gp.getPost().getTitle());
		}
	}

	@Test
	void findAllGalleryPosts() {

		List<Plant> test = plantService.findAllByAlphabetical(0, 10);

		for (Plant gp : test) {
			System.out.println(gp.getPost().getTitle());
		}
	}

	@Test
	void findPostContentTags() {

		Optional<Plant> gp = gpRepo.findById(1L);
		Plant g = gp.get();

		System.out.println(g.getPost().getTags());
	}

	@Test
	void findRelatedPlants() {
		List<Plant> plants = plantService.findAllRelated(0, 6, 1L);
		for (Plant p : plants) System.out.println(p.getCommonName() + " " + p.getGenus() + " " + p.getType());
	}

	@Test
	void findGalleryPostByPostTitleLike() {

		Pageable paging = PageRequest.of(0, 5, Sort.by("id").descending());
		Slice<Plant> slice = gpRepo.findAllByPostTitleContainingIgnoreCase("aloe", paging);
		assert slice.getContent().size() == 1;

		slice = gpRepo.findAllByPostTitleContainingIgnoreCase("ale", paging);
		assert slice.getContent().size() == 0;

	}

	@Test
	void createPostComments() {
		Post p1 = new Post();
		postService.save(p1);
		CommentBook cb1 = p1.setTitle("p1").getCommentBook();

		PostComment parentComment1 = new PostComment(null, "I am parent comment 1");
		PostComment childComment1 = new PostComment(parentComment1, "I am child comment 1");
		PostComment childComment2 = new PostComment(parentComment1, "I am child comment 2");
		PostComment childComment3 = new PostComment(parentComment1, "I am child comment 3");
		PostComment leafComment1 = new PostComment(childComment3, "I am child comment 3");

		cb1.addComment(parentComment1);
		postService.save(p1);

		List<PostComment> t1 = commentRepository.findAllByCommentBook(cb1);
		assert(t1.size() == 1 && t1.get(0).getContent().equals("I am parent comment 1"));

		List<PostComment> t2 = commentRepository.findAllByParentComment(parentComment1);
		assert(t2.size() == 3);

		List<PostComment> t3 = commentRepository.findAllByParentComment(leafComment1);
		assert(t3.size() == 1 && t3.get(0).getContent().equals("I am child comment 3"));
	}

}
