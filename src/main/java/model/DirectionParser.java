package model;

import util.Directions;

import static util.Directions.*;

public class DirectionParser {
    public static Directions parse(String direction) {
        return switch (direction) {
            case "north" -> NORTH;
            case "south" -> SOUTH;
            case "east" -> EAST;
            case "west" -> WEST;
            default -> throw new IllegalArgumentException(direction + "is not legal direction specified");
        };

    }
}
