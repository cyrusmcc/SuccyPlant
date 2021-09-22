package com.cm.contentmanagementapp;

import com.cm.contentmanagementapp.services.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class ContentManagementAppApplication /*implements CommandLineRunner*/ {

	/*
	@Resource
	FileStorageService storageService;
	*/

	public static void main(String[] args) {
		SpringApplication.run(ContentManagementAppApplication.class, args);
	}

	/*
	@Override
	public void run(String... arg) throws Exception {
		storageService.init();
	}
	*/
}
