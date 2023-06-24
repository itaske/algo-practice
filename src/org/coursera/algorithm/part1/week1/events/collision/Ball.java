package org.coursera.algorithm.part1.week1.events.collision;

import edu.princeton.cs.algs4.StdDraw;

public class Ball {
    private double rx, ry; //position
    private double vx, vy; //velocity
    private final double radius; //radius
    public Ball(double radius, double initialXPosition, double initialYPosition, double initialXSpeed, double initialYSpeed){
        this.radius = radius;
        this.rx = initialXPosition;
        this.ry = initialYPosition;
        this.vx = initialXSpeed;
        this.vy = initialYSpeed;
    }
    public void move(double dt) {
        if ((rx + vx * dt < radius) || (rx + vx * dt > 1.0 - radius)) {
            vx = -vx;
        }
        if ((ry + vy * dt < radius) || (ry + vy * dt > 1.0 - radius)) {
            vy = -vy;
        }

        rx = rx + vx * dt;
        ry = ry + vy * dt;
    }

    public void draw() {
        StdDraw.filledCircle(rx, ry, radius);
    }
}
