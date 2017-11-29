package utils;

public class Matrix {
	
    private int[][] matrix;
    private int discoveredCells;

    public Matrix (int rows, int columns) {
        this.matrix = new int[columns][rows];
        discoveredCells = 0;
        
		for(int row = 0; row < rows; row++) {
			for(int column = 0; column < columns; column++)
				setValue(row, column, 0);
		}
    }

    public int getValue(int row, int column) {
    	return matrix[row][column];
    }
    
    public void setValue(int row, int column, int val) {
        if(matrix[row][column] == 0)
        	discoveredCells++;
        this.matrix[row][column] = val;
    }

    public final int[][] getMatrix() { return matrix; }

    public int length() { return  matrix.length; }
    
	public boolean mapFullyExplored() {
		return ((matrix.length * matrix[0].length) == discoveredCells);
	}
}
