package com.cm.contentmanagementapp.dbinit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddPlant {
    public static void main(String[] args) throws IOException {

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

        Scanner scan = new Scanner(System.in);
        FileWriter writer = new FileWriter(file);
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
