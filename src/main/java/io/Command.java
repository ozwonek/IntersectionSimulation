package io;

import model.DirectionParser;
import model.Vehicle;

public class Command {
    private String type;
    private String vehicleId;
    private String startRoad;
    private String endRoad;

    public String getType() {
        return type;
    }

    public Vehicle makeVehicle() {
        return new Vehicle(vehicleId, DirectionParser.parse(startRoad), DirectionParser.parse(endRoad));
    }

    @Override
    public String toString() {
        return type;
    }
}
