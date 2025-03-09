package model;

import java.util.LinkedList;
import java.util.List;

public class Lane {
    private final int number;
    private final List<Vehicle> vehiclesInQueue = new LinkedList<>();
    private final List<Vehicle> vehiclesInQueueOnIntersection = new LinkedList<>();

    public Lane(int number) {
        this.number = number;
    }

    public void addToWaiting(Vehicle vehicle) {
        vehiclesInQueue.remove(vehicle);
        vehiclesInQueueOnIntersection.add(vehicle);
    }

    public int sizeOfQueueOnIntersection() {
        return vehiclesInQueueOnIntersection.size();
    }

    public void addVehicle(Vehicle vehicle) {
        vehiclesInQueue.add(vehicle);
    }

    public int getNumberOfLane() {
        return number;
    }

    public int sizeOfQueue() {
        return vehiclesInQueue.size();
    }

    public Vehicle getFirstVehicle() {
        return vehiclesInQueue.getFirst();
    }

    public Vehicle getFirstVehicleFromMiddle() {
        return vehiclesInQueueOnIntersection.getFirst();
    }

    public void removeFirstVehicle() {
        vehiclesInQueue.removeFirst();
    }

    public void removeFirstVehicleFromMiddle() {
        vehiclesInQueueOnIntersection.removeFirst();
    }

    public boolean isEmpty() {
        return vehiclesInQueue.isEmpty();
    }
}
