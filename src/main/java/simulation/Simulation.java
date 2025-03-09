package simulation;

import io.Command;
import io.InputData;
import io.WriteJson;
import model.Intersection;
import model.Road;
import model.Vehicle;
import presenter.SimulationPresenter;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    public final static String stepCommand = "step";
    public final static String addCommand = "addVehicle";
    private final InputData inputData;
    private final List<List<String>> outputList = new ArrayList<>();
    private final Intersection intersection;
    private final String outputFilePath;
    private SimulationPresenter presenter;

    public Simulation(InputData inputData, String outputFilePath, int lanesCount) {
        this.inputData = inputData;
        this.outputFilePath = outputFilePath;
        this.intersection = new Intersection(lanesCount);
    }

    public void setPresenter(SimulationPresenter presenter) {
        this.presenter = presenter;
        this.presenter.setIntersection(this.intersection);
        this.presenter.setLaneNumber(this.intersection.getNumberOfLanes());
    }

    public void addVehicle(Vehicle vehicle) {
        Road road = intersection.getRoad(vehicle.getStartRoad());
        road.addVehicle(vehicle);
        presenter.mapChanged(intersection);
    }

    public void makeStep() {
        List<String> vehiclesThatLeft = intersection.extractVehiclesLeavingIntersection();
        outputList.add(vehiclesThatLeft);
        presenter.mapChanged(intersection);
    }

    @Override
    public void run() {
        for (Command command : inputData.getCommands()) {

            switch (command.getType()) {
                case (Simulation.stepCommand) -> makeStep();

                case (Simulation.addCommand) -> addVehicle(command.makeVehicle());
                default -> throw new IllegalArgumentException(command.getType() + "is not legal direction specified");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        WriteJson.writeToJson(outputList, outputFilePath);

    }

}

