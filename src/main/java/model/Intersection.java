package model;

import java.util.ArrayList;
import java.util.List;

import static model.Light.GREEN;
import static model.Light.RED;

public class Intersection {
    private final Road northernRoad;
    private final Road easternRoad;
    private final Road southernRoad;
    private final Road westernRoad;
    private final int numberOfLanes;
    private final int orangeStateDuration = 2;
    private final int greenRedStateDuration = 4;
    private int step;
    private boolean running = true;

    public Intersection(int numberOfLanes) {
        this.numberOfLanes = numberOfLanes;
        this.northernRoad = new Road(numberOfLanes, GREEN);
        this.easternRoad = new Road(numberOfLanes, RED);
        this.southernRoad = new Road(numberOfLanes, GREEN);
        this.westernRoad = new Road(numberOfLanes, RED);
        this.step = 0;
    }

    public List<String> extractVehiclesLeavingIntersection() {
        List<String> leftIntersection = new ArrayList<>();
        for (Directions direction : Directions.values()) {
            Road road = getRoad(direction);
            Road oppositeRoad = getRoad(direction.leftCollision());
            if (road.color() == GREEN) {
                road.processVehiclesWithoutCollisions(leftIntersection);
                if (oppositeRoad.noVehicleGoingStraight()) {
                    road.processLeftTurningVehicles(leftIntersection);
                } else {
                    road.updateLeftLane();
                }
            }
            if (road.color() == Light.ORANGE) {
                Lane firstLane = road.getLanes().getFirst();
                if (firstLane.sizeOfQueueOnIntersection() != 0) {
                    road.processLeftTurningVehicles(leftIntersection);
                }
            }
            if (road.isRightArrowOn()) {
                Road leftRoad = getRoad(direction.rightCollision());
                road.rideRight(leftRoad, leftIntersection);
            }
        }
        if (running) {

            if (step == 2) {
                toggleRightArrow();
            }
            if (step == 3) {
                toggleRightArrow();
            }
        }
        step += 1;
        if (step >= (running ? greenRedStateDuration : orangeStateDuration)) {
            step = 0;
            changeToNextLightsPhase();
            running = !running;
        }
        return leftIntersection;

    }


    private void toggleRightArrow() {
        for (Directions direction : Directions.values()) {
            if (getRoad(direction).color() == Light.RED) {
                getRoad(direction).changeRightArrow();
            }
        }
    }


    private void changeToNextLightsPhase() {
        for (Directions direction : Directions.values()) {
            getRoad(direction).nextLights();
        }
    }

    public Road getRoad(Directions direction) {
        return switch (direction) {
            case EAST -> easternRoad;
            case WEST -> westernRoad;
            case NORTH -> northernRoad;
            case SOUTH -> southernRoad;
        };
    }

    public int getNumberOfLanes() {
        return numberOfLanes;
    }
}
