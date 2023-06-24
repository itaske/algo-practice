package org.coursera.algorithm.part1.week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationWithBackWash {

    private final WeightedQuickUnionUF grid;
    private final boolean[][] openedGrid;
    private int sitesOpened;
    private final int dimension;
    private final int virtualTop;
    private final int virtualBottom;
    // creates n-by-n grid, with all sites initially blocked
    public PercolationWithBackWash(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n should be greater 0");
        this.dimension = n;
        this.grid = new WeightedQuickUnionUF((n*n)+2);
        this.openedGrid = new boolean[n][n];
        this.sitesOpened = 0;
        this.virtualBottom = (n * n) + 1;
        this.virtualTop = 0;

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        if (!isValid(row, col))
            throw new IllegalArgumentException(String.format("Row %d Column %s not in grid", row, col));

        if (isOpen(row, col))
            return;
        int index = getGridIndex(row, col);

        // processUp
        if (isValid(row-1, col) && isOpen(row-1, col)) {
            grid.union(index, getGridIndex(row-1, col));
        }

        // processDown
        if (isValid(row+1, col) && isOpen(row+1, col)) {
            grid.union(index, getGridIndex(row+1, col));
        }

        // processLeft
        if (isValid(row, col-1) && isOpen(row, col-1)) {
            grid.union(index, getGridIndex(row, col-1));
        }

        // processRight
        if (isValid(row, col+1) && isOpen(row, col+1)) {
            grid.union(index, getGridIndex(row, col+1));
        }

        if (row == 1) {
            grid.union(virtualTop, getGridIndex(row, col));
        }

        if (row == dimension) {
            grid.union(virtualBottom, getGridIndex(row, col));
        }

        openedGrid[row-1][col-1] = true;
        sitesOpened++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException(String.format("Row %d Column %s not in grid", row, col));

        return openedGrid[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException(String.format("Row %d Column %s not in grid", row, col));
        if (!isOpen(row, col))
            return false;

        int topParent = grid.find(virtualTop);
        int index = getGridIndex(row, col);
        int indexParent = grid.find(index);

        return topParent == indexParent;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return sitesOpened;
    }

    // does the system percolate?
    public boolean percolates() {
        if (dimension == 1 && isOpen(1, 1))
            return true;
        if (sitesOpened == 0)
            return false;

        return grid.find(virtualBottom) == grid.find(virtualTop);
    }

    private int getGridIndex(int row, int column) {
        return (dimension * (row-1)) + column;
    }

    private boolean isValid(int row, int column) {
        if (row < 1 || row > dimension)
            return false;

        return column >= 1 && column <= dimension;

    }

    // test client (optional)
    public static void main(String[] args) {

    }

}
