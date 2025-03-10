package model;

import org.junit.jupiter.api.Test;
import util.Light;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static util.Directions.*;

class RoadTest {

    @Test
    void addVehicleTestToLeft(){
        Road road = new Road( 4, Light.GREEN);
        Vehicle vehicle = new Vehicle("vehicle3",SOUTH,WEST);
        road.addVehicle(vehicle);

        assertFalse(road.getLanes().getFirst().isEmpty());
        assertTrue(road.getLanes().get(1).isEmpty());

    }

    @Test
    void addVehicleTest() {
        Road road = new Road(3, Light.GREEN);
        List<Lane> lanes = road.getLanes();
        lanes.get(1).addVehicle(new Vehicle("vehicle3",NORTH,SOUTH));
        road.addVehicle(new Vehicle("vehicle4",NORTH,SOUTH));

        assertTrue(road.getLanes().getFirst().isEmpty());
        assertFalse(road.getLanes().get(2).isEmpty());
        assertEquals(1,road.getLanes().get(1).sizeOfQueue());

    }
    @Test
    void noVehicleGoingStraightTest(){
        Road road = new Road(3, Light.GREEN);
        List<Lane> lanes = road.getLanes();
        road.addVehicle(new Vehicle("vehicle1",SOUTH,WEST));
        road.addVehicle(new Vehicle("vehicle2",SOUTH,EAST));
        road.addVehicle(new Vehicle("vehicle3",SOUTH,EAST));
        assertTrue(road.noVehicleGoingStraight());
        lanes.getLast().removeFirstVehicle();
        road.addVehicle(new Vehicle("vehicle4",SOUTH,NORTH));
        assertFalse(road.noVehicleGoingStraight());

    }
}