package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionsTest {

    @Test
    void oppositeTest() {
        assertEquals(Directions.NORTH, Directions.SOUTH.opposite());
        assertEquals(Directions.SOUTH, Directions.NORTH.opposite());
        assertEquals(Directions.WEST, Directions.EAST.opposite());
        assertEquals(Directions.EAST, Directions.WEST.opposite());
    }

    @Test
    void rightCollisionTest() {
        assertEquals(Directions.NORTH, Directions.WEST.rightCollision());
        assertEquals(Directions.SOUTH, Directions.EAST.rightCollision());
        assertEquals(Directions.WEST, Directions.SOUTH.rightCollision());
        assertEquals(Directions.EAST, Directions.NORTH.rightCollision());

    }
}