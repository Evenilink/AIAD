package utils;

public class Matrix {
	
    private int[][] matrix;
    private int undiscoveredCells;

    public Matrix (int rows, int columns) {
        this.matrix = new int[columns][rows];
        undiscoveredCells = matrix.length * matrix[0].length;
        
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
        	undiscoveredCells--;
        this.matrix[row][column] = val;
    }

    public final int[][] getMatrix() { return matrix; }

    public int length() { return  matrix.length; }
    
	public boolean hasUndiscoveredCells() {
		return undiscoveredCells > 0;
	}
	
	public int getNumRows() {
		return matrix.length;
	}
	
	public int getNumColumns() {
		return matrix[0].length;
	}
	
	/**
	 * Merges the matrix of the receiving agent with the matrix received
	 * from other agents inside the communication radius.
	 * @param receivedMatrix
	 */
	public void mergeMatrix(Matrix otherMatrix) {
		for (int row = 0; row < otherMatrix.getNumRows(); row++) {
			for (int column = 0; column < otherMatrix.getNumColumns(); column++) {
				if (otherMatrix.getValue(row, column) != 0 && getValue(column, row) == 0)
					setValue(column, row, otherMatrix.getValue(row, column));
			}
		}
	}
}
