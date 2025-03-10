package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnsTest {

    @Test
    void testWhatTurnFromSouth() {

        assertEquals(Turns.LEFT, Turns.whatTurn(Directions.SOUTH, Directions.WEST));
        assertEquals(Turns.STRAIGHT, Turns.whatTurn(Directions.SOUTH, Directions.NORTH));
        assertEquals(Turns.RIGHT, Turns.whatTurn(Directions.SOUTH, Directions.EAST));
    }

    @Test
    void testWhatTurnFromWest() {

        assertEquals(Turns.LEFT, Turns.whatTurn(Directions.WEST, Directions.NORTH));
        assertEquals(Turns.STRAIGHT, Turns.whatTurn(Directions.WEST, Directions.EAST));
        assertEquals(Turns.RIGHT, Turns.whatTurn(Directions.WEST, Directions.SOUTH));
    }

    @Test
    void testWhatTurnFromNorth() {

        assertEquals(Turns.LEFT, Turns.whatTurn(Directions.NORTH, Directions.EAST));
        assertEquals(Turns.STRAIGHT, Turns.whatTurn(Directions.NORTH, Directions.SOUTH));
        assertEquals(Turns.RIGHT, Turns.whatTurn(Directions.NORTH, Directions.WEST));
    }

    @Test
    void testWhatTurnFromEast() {

        assertEquals(Turns.LEFT, Turns.whatTurn(Directions.EAST, Directions.SOUTH));
        assertEquals(Turns.STRAIGHT, Turns.whatTurn(Directions.EAST, Directions.WEST));
        assertEquals(Turns.RIGHT, Turns.whatTurn(Directions.EAST, Directions.NORTH));
    }
}