package simulation;

import io.InputData;
import io.LoadJson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import presenter.SimulationPresenter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SimulationApp extends Application {
    private Simulation simulation;

    public void init() {
        List<String> params = getParameters().getRaw();
        if (params.size() < 2) {
            System.out.println("Wrong Arguments");
            System.exit(1);
        }
        int numberOfLanes = 2;
        if (params.size() == 3){
            try {
                int number = Integer.parseInt(params.get(2));
                if (number < 2 || number > 4) {
                    throw new IllegalArgumentException("The number needs to be 2,3 or 4!");
                }
                else{
                    numberOfLanes = number;
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("It is not a number!", e);
            }
        }
        String inputFilePath = params.get(0);
        String outputFilePath = params.get(1);

        InputData inputData = LoadJson.parseFile(inputFilePath);
        this.simulation = new Simulation(inputData, outputFilePath, numberOfLanes);
    }

    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();
        configureStage(primaryStage, viewRoot);
        primaryStage.show();
        simulation.setPresenter(presenter);
        Thread thread = new Thread(simulation);
        thread.start();

    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}