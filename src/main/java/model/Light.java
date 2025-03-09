package model;


public enum Light {
    RED,
    GREEN,
    ORANGE,
    REDTOGREEN;

    public Light next() {
        return switch (this) {
            case RED -> REDTOGREEN;
            case REDTOGREEN -> GREEN;
            case GREEN -> ORANGE;
            case ORANGE -> RED;

        };

    }
}
