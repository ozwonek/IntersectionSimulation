package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LightTest {

    @Test
    void nextTest() {
        assertEquals(Light.GREEN, Light.REDTOGREEN.next());
        assertEquals(Light.RED, Light.ORANGE.next());
        assertEquals(Light.ORANGE, Light.GREEN.next());
        assertEquals(Light.REDTOGREEN, Light.RED.next());

    }
}