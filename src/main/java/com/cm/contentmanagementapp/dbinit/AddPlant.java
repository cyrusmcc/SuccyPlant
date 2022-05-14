package com.cm.contentmanagementapp.dbinit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddPlant {

    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        String in = scan.nextLine();
        while (!in.equals("quit")) {
            System.out.println("add - Add plant to list for processing");
            System.out.println("initdb - Add detailed plant objects to DB");
            if (in.equals("add")) addPlant();
            if (in.equals("initdb")) initdb();
            in = scan.nextLine();
        }

    }

    private static void initdb() throws IOException {
        File file = new File("src/main/java/com/cm/contentmanagementapp/dbinit/plantDetails.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String in = br.readLine();
        while (in != null) {
            System.out.println(in);
            //plantsInFile.add(in);
            in = br.readLine();
        }


    }

    private static void addPlant() throws IOException {
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
}
