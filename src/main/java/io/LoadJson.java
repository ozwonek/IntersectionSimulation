package io;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LoadJson {
    public static InputData parseFile(String filePath) {
        Gson gson = new Gson();
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("The file does not exist: " + filePath);
        }
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, InputData.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse this File");
        }

    }
}
