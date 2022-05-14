package com.cm.contentmanagementapp;

import com.cm.contentmanagementapp.dbinit.AddPlant;
import com.cm.contentmanagementapp.services.ContentTagService;
import com.cm.contentmanagementapp.services.FileStorageService;
import com.cm.contentmanagementapp.services.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class ContentManagementAppApplication implements CommandLineRunner {
	@Resource
	FileStorageService storageService;

	@Autowired
	PlantService plantService;

	@Autowired
	ContentTagService tagService;

	public static void main(String[] args) {
		SpringApplication.run(ContentManagementAppApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		storageService.init();
		//AddPlant addPlant = new AddPlant(plantService, tagService);
		//addPlant.initdb();
	}

}
