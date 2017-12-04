package utils;

import agents.Explorer;
import behaviours.Exploration;
import entities.Entity;
import entities.Obstacle;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import states.Guarding;
import states.TravelExit;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class Matrix implements Serializable {
	
    private int[][] matrix;
    private int undiscoveredCells;
    
    private String name;

    public Matrix (int rows, int columns, String name) {
        this.matrix = new int[columns][rows];
        
		for(int row = 0; row < rows; row++) {
			for(int column = 0; column < columns; column++)
				setValue(row, column, 0);
		}

        undiscoveredCells = getNumColumns() * getNumRows();	
		this.name = name;
    }

	/**
	 * Merges the matrix of the receiving agent with the matrix received
	 * from other agents inside the communication radius.
	 * @param otherMatrix
	 */
	public void mergeMatrix(Matrix otherMatrix) {
		System.out.println("Before merging matrix");
		printMatrix();

		for (int row = 0; row < otherMatrix.getNumRows(); row++) {
			for (int column = 0; column < otherMatrix.getNumColumns(); column++) {
				if (otherMatrix.getValue(row, column) != 0 && getValue(row, column) == 0)
					setValue(row, column, otherMatrix.getValue(row, column));
			}
		}
		
		System.out.println("After merging matrix");
		printMatrix();
	}
	
	public void printMatrix() {
		System.out.println("Name: " + name);
		for(int row = 0; row < matrix.length; row++) {
			for(int column = 0; column < matrix[row].length; column++)
				System.out.print(matrix[row][column] + " | ");
			System.out.println(" " + (getNumRows() - 1 - row));
		}
		
		for(int column = 0; column < matrix[0].length; column++)
			System.out.print(column + "   ");
		
		System.out.println("\n");
	}
	
	public void updateMatrix(Exploration behaviour, Grid<Object> grid, Coordinates center, int radius) {
        GridPoint centerPoint = new GridPoint(center.getX(), center.getY());
        GridCellNgh<Object> nghCreator = new GridCellNgh<Object>(grid, centerPoint, Object.class, radius, radius);
        List<GridCell<Object>> gridCells = nghCreator.getNeighborhood(false);

        for (GridCell<Object> gridCell : gridCells) {
            Iterator<Object> it = gridCell.items().iterator();

			/* Coordinates targetCoordinates = Coordinates.FromGridPoint(gridCell.getPoint());
			Obstacle obstacleHit = RayTracing.trace(grid, center, targetCoordinates, true);
			if (obstacleHit != null) {
				Coordinates matrixCoordinates = Utils.matrixFromWorldPoint(grid.getLocation(obstacleHit), getNumRows());
				this.setValue(matrixCoordinates.getY(), matrixCoordinates.getX(), obstacleHit.getCode());
				continue;

			} */

            if (!it.hasNext()) {
                Coordinates matrixCoordinates = Utils.matrixFromWorldPoint(gridCell.getPoint(), getNumRows());
                this.setValue(matrixCoordinates.getY(), matrixCoordinates.getX(), 1);
            }

            while(it.hasNext()) {
                int value = 0;
                Object obj = it.next();

                if(obj instanceof Entity) {
                	Entity entity = (Entity) obj;
                	value = entity.getCode();
                } else if (obj == null || obj instanceof Explorer) value = 1;
                
                // if Explorer is standing there, the cell must be empty (value = 1)
                // TODO: false, it can also be 2 (exit).
                /*if (obj == null || obj instanceof Explorer) value = 1;
                else if (obj instanceof Entity) {
                    Entity entity = (Entity) obj;
                    value = entity.getCode();
                }*/ else
                	System.err.println("Matrix: Unidentified object, could't update matrix!");
                Coordinates matrixCoordinates = Utils.matrixFromWorldPoint(gridCell.getPoint(), getNumRows());
                this.setValue(matrixCoordinates.getY(), matrixCoordinates.getX(), value);
                if(value == 2) break;
            }
        }
        printMatrix();
    }
	
	public boolean hasUndiscoveredCells() {
		return undiscoveredCells > 0;
	}
	
	/**
	 * Returns the exit coordinates.
	 * @return Exit coordinates or null.
	 */
	public Coordinates getExit() {
		// TODO Use the setValue to see when the exit is found and save it into a variable (no need to read the matrix)
		for(int row = 0; row < getNumRows(); row++) {
			for(int column = 0; column < getNumColumns(); column++) {
				System.out.print(getValue(row, column));
				if(getValue(row, column) == 2)
					return utils.Utils.worldPointFromMatrix(new Coordinates(column, row), getNumRows());
			}
		}
		return null;
	}

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
	
	public int getNumRows() {
		return matrix.length;
	}
	
	public int getNumColumns() {
		return matrix[0].length;
	}
}
