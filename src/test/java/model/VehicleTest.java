package model;

import org.junit.jupiter.api.Test;
import util.Directions;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void testVehicleConstructor() {
        Vehicle vehicle = new Vehicle("vehicle1", DirectionParser.parse("south"),DirectionParser.parse("north"));
        assertNotNull(vehicle);
        assertEquals("vehicle1",vehicle.getVehicleId());
        assertEquals(Directions.SOUTH,vehicle.getStartRoad());
        assertEquals(Directions.NORTH,vehicle.getEndRoad());
    }

}