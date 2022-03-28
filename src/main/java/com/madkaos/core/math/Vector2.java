package com.madkaos.core.math;

public class Vector2 {
    private double x;
    private double y;

    public Vector2(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public Vector2 setX(final double x) {
        this.x = x;

        return this;
    }

    public Vector2 setY(final double y) {
        this.y = y;

        return this;
    }

    public Vector2 clone() {
        return new Vector2(this.getX() + 0, this.getX() + 0);
    }

    public Vector2 minus(final Vector2 vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        return this;
    }

    public Vector2 plus(final Vector2 vector) {
        this.x += vector.x;
        this.y += vector.y;
        return this;
    }


    public String toString() {
        return this.x + ", " + this.y;
    }
}
