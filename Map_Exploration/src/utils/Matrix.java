package utils;

import agents.Explorer;
import entities.Entity;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class Matrix implements Serializable {
	
    private int[][] matrix;
    private int undiscoveredCells;

    public Matrix (int rows, int columns) {
        this.matrix = new int[columns][rows];
        
		for(int row = 0; row < rows; row++) {
			for(int column = 0; column < columns; column++)
				setValue(row, column, 0);
		}

        undiscoveredCells = getNumColumns() * getNumRows();		
		printMatrix();
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
		
		printMatrix();
	}
	
	private void printMatrix() {		
		for(int row = 0; row < matrix.length; row++) {
			for(int column = 0; column < matrix[row].length; column++)
				System.out.print(matrix[row][column] + " | ");
			System.out.println(" " + (getNumRows() - 1 - row));
		}
		
		for(int column = 0; column < matrix[0].length; column++)
			System.out.print(column + "   ");
		
		System.out.println("\n");
	}
	
	public void updateMatrix(Grid<Object> grid, Coordinates center, int radius) {
        GridPoint centerPoint = new GridPoint(center.getX(), center.getY());
        GridCellNgh<Object> nghCreator = new GridCellNgh<Object>(grid, centerPoint, Object.class, radius, radius);
        List<GridCell<Object>> gridCells = nghCreator.getNeighborhood(true);

        for (GridCell<Object> gridCell : gridCells) {
            Iterator<Object> it = gridCell.items().iterator();
            if (!it.hasNext()) {
                Coordinates matrixCoordinates = Utils.matrixFromWorldPoint(gridCell.getPoint(), getNumRows());
                this.setValue(matrixCoordinates.getY(), matrixCoordinates.getX(), 1);
            }

            while(it.hasNext()) {
                int value = 0;
                Object obj = it.next();

                // if Explorer is standing there, the cell must be empty (value = 1)
                if (obj == null || obj instanceof Explorer) value = 1;
                else if (obj instanceof Entity) {
                    Entity entity = (Entity) obj;
                    value = entity.getCode();
                } else {
                    System.err.println("Matrix: Unidentified object, could't update matrix!");
                }
                Coordinates matrixCoordinates = Utils.matrixFromWorldPoint(gridCell.getPoint(), getNumRows());
                this.setValue(matrixCoordinates.getY(), matrixCoordinates.getX(), value);
            }
        }
        this.printMatrix();
    }

    /*public void printMatrix() {
        //public static void printMatrix(int[][] matrix, Consumer<int[]> rowPrinter) {
        Consumer<int[]> pipeDelimiter = (row) -> {
            Arrays.stream(row).forEach((el) -> System.out.print("| " + el + " "));
            System.out.println("|");
        };
            Arrays.stream(matrix)
                    .forEach((row) -> pipeDelimiter.accept(row));
    }*/

	/*******************************/
	/***** Getters and setters *****/
	/*******************************/
	
    public final int[][] getMatrix() { return matrix; }
	
    public int getValue(int row, int column) {
    	return matrix[row][column];
    }
    
    public void setValue(int row, int column, int val) {
        if(matrix[row][column] == 0)
        	undiscoveredCells--;
        this.matrix[row][column] = val;
    }

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
}
