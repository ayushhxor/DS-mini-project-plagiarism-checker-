package utils;

import java.io.*;

public class FileHandler {

    public String readFile(String path) {

        StringBuilder content = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append(" ");
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return content.toString();
    }
}