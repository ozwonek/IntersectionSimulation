package model;

import util.Directions;
import util.Light;

import java.util.LinkedList;
import java.util.List;

import static util.Light.GREEN;
import static util.Light.RED;

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
        List<String> leftIntersection = new LinkedList<>();
        for (Directions direction : Directions.values()) {
            Road road = getRoad(direction);
            Road oppositeRoad = getRoad(direction.opposite());
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
        processArrow();
        processSignalisation();
        return leftIntersection;

    }

    private void processArrow() {
        if (running) {
            if (step == 1) {
                toggleRightArrow();
            }
            if (step == greenRedStateDuration - 1) {
                toggleRightArrow();
            }
        }
    }

    private void processSignalisation() {
        step += 1;
        if (step >= (running ? greenRedStateDuration : orangeStateDuration)) {
            step = 0;
            if (running) {
                for (Directions directions : Directions.values()) {
                    if (getRoad(directions).color() == RED) {
                        if (!getRoad(directions).noVehicleGoing() || !getRoad(directions.opposite()).noVehicleGoing()) {
                            changeToNextLightsPhase();
                            running = !running;
                            break;
                        }
                    }
                }
            } else {
                changeToNextLightsPhase();
                running = !running;
            }

        }
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
