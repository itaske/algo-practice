import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;


public class Board {

    private int[][] tiles;

    private int zeroRow = 0;
    private int zeroColumn = 0;
    private int manhattanDistance;
    private int hammingDistance;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = new int[tiles.length][tiles.length];
        this.manhattanDistance = 0;
        int count =1;
        this.hammingDistance = 0;
        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column < tiles[row].length; column++) {
                this.tiles[row][column] = tiles[row][column];
                if (this.tiles[row][column] == 0) {
                    zeroRow = row;
                    zeroColumn = column;
                }
                int value = tiles[row][column];
                if (value != 0) {
                    int actualRow = (value - 1)/dimension();
                    int actualColumn = (value - 1) % dimension();
                    int distance = Math.abs(actualRow - row) + Math.abs(actualColumn - column);
                    manhattanDistance += distance;
                }

                if (row == tiles.length - 1 && column == tiles.length && tiles[row][column] != 0) {
                    hammingDistance++;
                } else if (tiles[row][column] != count && tiles[row][column] != 0) {
                    hammingDistance++;
                }
                count++;
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringJoiner builder = new StringJoiner("\n");
        for (int row = 0; row < tiles.length; row++) {
            StringJoiner joiner = new StringJoiner(" ");

            int rowMaxLength = String.valueOf(tiles[tiles.length-1][0]).length();
            for (int column = 0; column < tiles[row].length; column++) {
                joiner.add("%"+rowMaxLength+"s");
            }
            String[] values = new String[tiles[row].length];

            for (int i = 0; i < values.length; i++) {
                values[i] = String.valueOf(tiles[row][i]);
            }
            String rowString = String.format(joiner.toString(), (Object[]) values);
            builder.add(rowString);
        }
        return dimension()+"\n"+builder;
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        return hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattanDistance == 0;
    }


    // does this board equal y?
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Board board = (Board) other;

        if (board.dimension() != dimension()) {
            return false;
        }
        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column < tiles[row].length; column++) {
                if (board.tiles[row][column] != tiles[row][column])
                    return false;
            }
        }
        return true;
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();

        if (zeroRow - 1 >= 0) {
            int[][] moveZeroUpTiles = copyTiles(tiles);
            int currentValue = moveZeroUpTiles[zeroRow][zeroColumn];
            moveZeroUpTiles[zeroRow][zeroColumn] = moveZeroUpTiles[zeroRow-1][zeroColumn];
            moveZeroUpTiles[zeroRow-1][zeroColumn] = currentValue;
            Board moveZeroUpBoard = new Board(moveZeroUpTiles);

            neighbors.add(moveZeroUpBoard);
        }

        if (zeroRow + 1 < dimension()) {
            int[][] moveZeroDownTiles = copyTiles(tiles);
            int currentValue = moveZeroDownTiles[zeroRow][zeroColumn];
            moveZeroDownTiles[zeroRow][zeroColumn] = moveZeroDownTiles[zeroRow+1][zeroColumn];
            moveZeroDownTiles[zeroRow+1][zeroColumn] = currentValue;
            Board moveZeroDownBoard = new Board(moveZeroDownTiles);

            neighbors.add(moveZeroDownBoard);
        }

        if (zeroColumn - 1 >= 0) {
            int[][] moveZeroLeftTiles = copyTiles(tiles);
            int currentValue = moveZeroLeftTiles[zeroRow][zeroColumn];
            moveZeroLeftTiles[zeroRow][zeroColumn] = moveZeroLeftTiles[zeroRow][zeroColumn-1];
            moveZeroLeftTiles[zeroRow][zeroColumn-1] = currentValue;
            Board moveZeroLeftBoard = new Board(moveZeroLeftTiles);

            neighbors.add(moveZeroLeftBoard);
        }

        if (zeroColumn + 1 < dimension()) {
            int[][] moveZeroRightTiles = copyTiles(tiles);
            int currentValue = moveZeroRightTiles[zeroRow][zeroColumn];
            moveZeroRightTiles[zeroRow][zeroColumn] = moveZeroRightTiles[zeroRow][zeroColumn+1];
            moveZeroRightTiles[zeroRow][zeroColumn+1] = currentValue;
            Board moveZeroRightBoard = new Board(moveZeroRightTiles);

            neighbors.add(moveZeroRightBoard);
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[] exRows = new int[2];
        int[] exCols = new int[2];
        int count = 0;
        int n = dimension();
        for (int i = 0; i < n*n; i++) {
            int row = i / n;
            int col = i % n;
            if (tiles[row][col] != 0) {
                exRows[count] = row;
                exCols[count] = col;
                count++;
                if (count == 2) {
                    break;
                }
            }
        }
        int[][] twinBlocks = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++)
            twinBlocks[i] = tiles[i].clone();
        int tmp = twinBlocks[exRows[0]][exCols[0]];
        twinBlocks[exRows[0]][exCols[0]] = twinBlocks[exRows[1]][exCols[1]];
        twinBlocks[exRows[1]][exCols[1]] = tmp;
        Board twinBoard = new Board(twinBlocks);
        return twinBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        int n = StdIn.readInt();
        int[][] boardTiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                boardTiles[i][j] = StdIn.readInt();
        Board initial = new Board(boardTiles);
        System.out.println(initial.hamming());
        System.out.println(initial.manhattan());
//        initial.neighbors().forEach(System.out::println);
//        StdOut.printf("Hamming - %d, Manhattan = %d", initial.hamming(), initial.manhattan());

    }

    private int[][] copyTiles(int[][] tilesToCopy) {
        int[][] tilesCopy = new int[tilesToCopy.length][tilesToCopy.length];
        for (int row = 0; row < tilesToCopy.length; row++) {
            for (int column = 0; column < tilesToCopy[row].length; column++) {
                tilesCopy[row][column] = tilesToCopy[row][column];
            }
        }
        return tilesCopy;
    }
}

