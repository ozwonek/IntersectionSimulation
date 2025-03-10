package util;

public enum Turns {
    LEFT,
    RIGHT,
    STRAIGHT;

    public static Turns whatTurn(Directions startRoad, Directions endRoad) {
        return switch (startRoad) {
            case SOUTH -> (endRoad == Directions.WEST) ? LEFT : (endRoad == Directions.NORTH) ? STRAIGHT : RIGHT;
            case WEST -> (endRoad == Directions.NORTH) ? LEFT : (endRoad == Directions.EAST) ? STRAIGHT : RIGHT;
            case NORTH -> (endRoad == Directions.EAST) ? LEFT : (endRoad == Directions.SOUTH) ? STRAIGHT : RIGHT;
            case EAST -> (endRoad == Directions.SOUTH) ? LEFT : (endRoad == Directions.WEST) ? STRAIGHT : RIGHT;

        };
    }

    ;
}
