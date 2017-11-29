package utils;

public class Matrix {
    private int[][] matrix;

    public Matrix (int rows, int cols) {
        this.matrix = new int[cols][rows];
    }

    public void setValue(int row, int col, int val) {
        this.matrix[row][col] = val;
    }

    public final int[][] getMatrix() { return matrix; }

    public int length() { return  matrix.length; }
}
