package com.cm.contentmanagementapp.dbinit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.cm.contentmanagementapp.models.EnumTagCategory;
import com.cm.contentmanagementapp.models.Plant;
import com.cm.contentmanagementapp.models.Post;
import com.cm.contentmanagementapp.services.ContentTagService;
import com.cm.contentmanagementapp.services.PlantService;
import org.json.JSONObject;

public class AddPlant {

    PlantService plantService;

    ContentTagService tagService;

    private Scanner scan = new Scanner(System.in);

    public AddPlant(PlantService plantService, ContentTagService tagService) {
        this.plantService = plantService;
        this.tagService = tagService;
    }

    public void initdb() throws IOException {

        // If tags don't exist, init in DB
        createTags();

        // Read in JSON plant objects, parse & create new plant entry in DB
        File file = new File("src/main/java/com/cm/contentmanagementapp/dbinit/plantDetails.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String in = br.readLine();
        while (in != null) {
            JSONObject obj = new JSONObject(in);

            if (!plantService.existsByScienceName(obj.getString("scienceName"))) {
                Plant plant = new Plant();
                Post plantPost = plant.getPost();

                String size = obj.getString("size");
                size = size.substring(0,1).toUpperCase() + size.substring(1).toLowerCase();

                String petsafe = obj.getString("petsafe");
                petsafe = petsafe.substring(0,1).toUpperCase() + petsafe.substring(1).toLowerCase();

                String difficulty = obj.getString("difficulty");
                difficulty = difficulty.substring(0,1).toUpperCase() + difficulty.substring(1).toLowerCase();


                // Set title of plant's post
                plant.setTitle(obj.getString("scienceName"));
                plant.setScientificName(obj.getString("scienceName"));
                plant.setCommonName(obj.getString("commonName"));
                plant.setDescription(obj.getString("desc"));
                plant.setLightDesc(obj.getString("lightDesc"));
                plant.setWaterDesc(obj.getString("waterDesc"));
                plant.setPetDesc(obj.getString("petDesc"));
                plant.setSizeDesc(obj.getString("sizeDesc"));
                plantPost.addTag(tagService.findByCategoryAndValue(EnumTagCategory.GENUS, obj.getString("genus")));
                plantPost.addTag(tagService.findByCategoryAndValue(EnumTagCategory.DIFFICULTY, difficulty));
                plantPost.addTag(tagService.findByCategoryAndValue(EnumTagCategory.SIZE, size));
                plantPost.addTag(tagService.findByCategoryAndValue(EnumTagCategory.PET, petsafe));
                plantPost.addTag(tagService.findByCategoryAndValue(EnumTagCategory.TYPE, getType(obj)));
                plantPost.addTag(tagService.findByCategoryAndValue(EnumTagCategory.WATER, getWater(obj)));
                plantPost.addTag(tagService.findByCategoryAndValue(EnumTagCategory.LIGHT, getLight(obj)));
                plantService.save(plant);

            }
            in = br.readLine();
        }
    }

    private void addPlant() throws IOException {
        File file = new File("src/main/java/com/cm/contentmanagementapp/dbinit/plants.txt");
        List<String> plantsInFile = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(file));
        String in = br.readLine();
        while (in != null) {
            in = in.toLowerCase();
            plantsInFile.add(in);
            in = br.readLine();
        }

        for (String s : plantsInFile) System.out.println(s);

        scan = new Scanner(System.in);
        FileWriter writer = new FileWriter(file, true);
        String line = scan.nextLine();

        while(!line.equals("quit")) {

            line = line.toLowerCase();
            line = line.strip();
            if (plantsInFile.contains(line) || line.equals(" ") || line.isEmpty()) System.out.println("already in file");
            else {
                writer.write(line + "\n");
                plantsInFile.add(line);
                System.out.println("Wrote " + line + " to file.");
            }
            line = scan.nextLine();
        }

        writer.close();
    }

    private void createTags() {
        tagService.newTag("House Plant", EnumTagCategory.TYPE);
        tagService.newTag("Succ", EnumTagCategory.TYPE);
        tagService.newTag("Cactus", EnumTagCategory.TYPE);
        tagService.newTag("Small", EnumTagCategory.SIZE);
        tagService.newTag("Medium", EnumTagCategory.SIZE);
        tagService.newTag("Large", EnumTagCategory.SIZE);
        tagService.newTag("Beginner", EnumTagCategory.DIFFICULTY);
        tagService.newTag("Advanced", EnumTagCategory.DIFFICULTY);
        tagService.newTag("Low", EnumTagCategory.LIGHT);
        tagService.newTag("Medium", EnumTagCategory.LIGHT);
        tagService.newTag("High", EnumTagCategory.LIGHT);
        tagService.newTag("Yes", EnumTagCategory.PET);
        tagService.newTag("No", EnumTagCategory.PET);
    }

    private String getType(JSONObject obj) {
        String type = "";

        switch (obj.getString("type")) {
            case ("hp"):
                type = "House Plant";
                break;
            case ("succ"):
                type = "Succ";
                break;
            case ("cactus"):
                type = "Cactus";
            default:
                break;
        }

        return type;
    }

    private String getWater(JSONObject obj) {
        String water = "";

        switch (obj.getString("water")) {
            case ("WD"):
                water = "Low";
                break;
            case ("THD"):
                water = "Medium";
                break;
            case ("SM"):
                water = "High";
            default:
                break;
        }

        return water;
    }

    private String getLight(JSONObject obj) {
        String light = "";

        switch (obj.getString("light")) {
            case ("AL"):
                light = "Low";
                break;
            case ("IPS"):
            case ("BND"):
                light = "Medium";
                break;
            case ("BPSO"):
                light = "High";
            default:
                break;
        }

        return light;
    }
}
