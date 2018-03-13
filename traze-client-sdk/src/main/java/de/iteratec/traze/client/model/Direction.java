package de.iteratec.traze.client.model;

public enum Direction {

    N(0, 1),
    S(0, -1),
    W(-1, 0),
    E(1, 0);

    private final int deltaX;
    private final int deltaY;

    Direction(final int deltaX, final int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int getDeltaX() {
        return this.deltaX;
    }

    public int getDeltaY() {
        return this.deltaY;
    }
}