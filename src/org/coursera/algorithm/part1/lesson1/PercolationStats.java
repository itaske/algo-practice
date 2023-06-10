package org.coursera.algorithm.part1.lesson1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] means;
    private final int trials;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        this.trials = trials;
        means = new double[trials];
        Percolation percolation;
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniformInt(1, n+1);
                int col = StdRandom.uniformInt(1, n+1);

                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            means[i] = (double) percolation.numberOfOpenSites() / (n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(means);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(means);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
       PercolationStats percolationStats = new PercolationStats(n, t);
        StdOut.printf("%-23s = %f%n", "mean", percolationStats.mean());
        StdOut.printf("%-23s = %f%n", "stddev", percolationStats.stddev());
        StdOut.println("95% confidence interval = ["+percolationStats.confidenceLo()+", "+ percolationStats.confidenceHi()+"]");

    }
}
