package org.coursera.algorithm.part1.week1.events.collision;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class BouncingBalls {
    public static void main(String[] args) {
        int N = 50;
        int size = 50;
        Ball[] balls = new Ball[N];

        for (int i = 0; i < N; i++) {
            double randomX = StdRandom.uniformDouble();
            double randomY = StdRandom.uniformDouble();
            double initialXSpeed = StdRandom.uniformDouble(0, 0.0050);
            double initialYSpeed = StdRandom.uniformDouble(0, 0.0050);
            balls[i] = new Ball(0.010, randomX, randomY, initialXSpeed, initialYSpeed);
        }
        while (true) {
            StdDraw.clear();
            for (int i = 0; i < N; i++) {
                balls[i].move(0.5);
                balls[i].draw();
            }
            StdDraw.enableDoubleBuffering();
            StdDraw.show();
        }
    }
}
