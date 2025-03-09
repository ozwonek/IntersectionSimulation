package io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteJson {
    public static void writeToJson(List<List<String>> output, String filePath) {
        List<LeftVehicles> stepStatuses = new ArrayList<>();
        for (List<String> leftVehicles : output) {
            LeftVehicles left = new LeftVehicles(leftVehicles);
            stepStatuses.add(left);
        }
        StepStatuses steps = new StepStatuses(stepStatuses);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(steps);
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(json);
            System.out.println("JSON zapisany do pliku output.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
