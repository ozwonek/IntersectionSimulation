package model;

import util.Light;

import java.util.LinkedList;
import java.util.List;

import static util.Turns.LEFT;
import static util.Turns.STRAIGHT;

public class Road {
    private final int numberOfLanes;
    private Light light;
    private boolean isRightArrowOn = false;
    private List<Lane> lanes = new LinkedList<>();

    public Road(int numberOfLanes, Light initialLightState) {
        this.light = initialLightState;
        this.numberOfLanes = numberOfLanes;
        for (int i = 0; i < numberOfLanes; i++) {
            lanes.add(new Lane(i));
        }
    }

    public boolean isRightArrowOn() {
        return isRightArrowOn;
    }

    public void changeRightArrow() {
        this.isRightArrowOn = !isRightArrowOn;
    }

    public void addVehicle(Vehicle vehicle) {
        if (vehicle.getTurn() == LEFT) {
            lanes.getFirst().addVehicle(vehicle);
        } else {
            lanes.get(theSmallestQueue()).addVehicle(vehicle);
        }
    }


    public boolean noVehicleGoingStraight() {
        return getLanes().stream().allMatch((lane -> lane.isEmpty() || lane.getFirstVehicle().getTurn() != STRAIGHT));
    }

    public boolean noVehicleGoing() {
        return getLanes().stream().allMatch((Lane::isEmpty));
    }

    public void processVehiclesWithoutCollisions(List<String> leavingIntersection) {
        for (Lane lane : lanes) {
            if (lane.isEmpty()) {
                continue;
            }
            Vehicle vehicle = lane.getFirstVehicle();
            if (vehicle.getTurn() != LEFT) {
                leavingIntersection.add(vehicle.getVehicleId());
                lane.removeFirstVehicle();
            }
        }

    }

    public void processLeftTurningVehicles(List<String> leavingIntersection) {
        Lane left = lanes.getFirst();
        Vehicle vehicle;
        if (left.sizeOfQueueOnIntersection() != 0) {
            vehicle = left.getFirstVehicleFromMiddle();
            left.removeFirstVehicleFromMiddle();
            leavingIntersection.add(vehicle.getVehicleId());
        } else if (left.sizeOfQueue() != 0) {
            vehicle = left.getFirstVehicle();
            left.removeFirstVehicle();
            leavingIntersection.add(vehicle.getVehicleId());
        }
        if (left.sizeOfQueue() != 0) {
            Vehicle vehicleToWait = left.getFirstVehicle();
            left.removeFirstVehicle();
            left.addToWaiting(vehicleToWait);
        }
    }

    public boolean noCollision() {
        Lane right = lanes.getLast();
        return right.isEmpty() || right.getFirstVehicle().getTurn() != STRAIGHT;
    }

    public void rideRight(Road potentialCollision, List<String> leavingIntersection) {
        if (potentialCollision.noCollision()) {
            Lane right = lanes.getLast();
            if (!right.isEmpty()) {
                Vehicle vehicle = right.getFirstVehicle();
                right.removeFirstVehicle();
                leavingIntersection.add(vehicle.getVehicleId());
            }

        }
    }

    public int theSmallestQueue() {
        int min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 1; i < lanes.size(); i++) {
            Lane lane = lanes.get(i);
            if (lane.sizeOfQueue() <= min) {
                min = lane.sizeOfQueue();
                index = i;
            }
        }
        return index;
    }

    public void updateLeftLane() {
        Lane left = lanes.getFirst();
        if (numberOfLanes > left.sizeOfQueueOnIntersection() && left.sizeOfQueue() > 0) {
            Vehicle vehicleToMove = left.getFirstVehicle();
            left.removeFirstVehicle();
            left.addToWaiting(vehicleToMove);
        }
    }

    public void nextLights() {
        light = light.next();
    }

    public Light color() {
        return light;
    }

    public List<Lane> getLanes() {
        return lanes;
    }
}
