package utils;

import agents.Explorer;
import behaviours.Exploration;
import entities.Entity;
import entities.Exit;
import entities.Obstacle;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

import java.io.Serializable;
import java.util.ArrayList;
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
		// System.out.println("Before merging matrix");
		// printMatrix();

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
		System.out.println("Agent name: " + name);
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
        List<GridCell<Object>> gridCells = nghCreator.getNeighborhood(true);

        for (GridCell<Object> gridCell : gridCells) {
			Coordinates targetCoordinates = Coordinates.FromGridPoint(gridCell.getPoint());
			Obstacle obstacleHit = RayTracing.trace(grid, center, targetCoordinates, true);
			if (obstacleHit != null) {
				Coordinates matrixCoordinates = Utils.matrixFromWorldPoint(grid.getLocation(obstacleHit), getNumRows());
				this.setValue(matrixCoordinates.getY(), matrixCoordinates.getX(), obstacleHit.getCode());
				System.out.println("Setting node not walkbale");
				behaviour.getAStar().setNodeWalkable(Coordinates.FromGridPoint(gridCell.getPoint()), false);
				behaviour.getAStar().printGrid();
				continue;
			}

            Iterator<Object> it = gridCell.items().iterator();
			if (!it.hasNext()) {
				Coordinates matrixCoordinates = Utils.matrixFromWorldPoint(gridCell.getPoint(), getNumRows());
				this.setValue(matrixCoordinates.getY(), matrixCoordinates.getX(), 1);
			} else {
				int value = 0;
				// If the cell has objects
				while(it.hasNext()) {
					Object obj = it.next();
					// If the object found is an Entity use it's value
					if(obj instanceof Exit) {
						Exit exit = (Exit) obj;
						value = exit.getCode();
						break;
					} else if (obj == null || obj instanceof Explorer)
						value = 1;
					else System.err.println("Matrix: Unidentified object, could't update matrix!");
				}
				// Updates the matrix with the new value
				Coordinates matrixCoordinates = Utils.matrixFromWorldPoint(gridCell.getPoint(), getNumRows());
				this.setValue(matrixCoordinates.getY(), matrixCoordinates.getX(), value);
			}
        }
        printMatrix();
    }
	
	public boolean hasUndiscoveredCells() {
		return undiscoveredCells > 0;
	}
	
	/**
	 * @param pt1 Coordinates of the first point
	 * @param pt2 Coordinates of the second point
	 * @return The distance of two points
	 */
	public double distanceTwoPoints(Coordinates pt1, Coordinates pt2)
	{
		return Math.sqrt(((pt2.getX() - pt1.getX())*(pt2.getX() - pt1.getX())) + ((pt2.getY()-pt1.getY())*(pt2.getY()-pt1.getY())));
	}
	
	/**
	 * @brief This function gets the coordinates of 
	 * the closest obstacle from the agent's position
	 * @param agentPosition The Coordinates of the agent
	 * @return The coordinates of the closest obstacle
	 */
	public Coordinates getNearestObstacle(Coordinates agentPosition) 
	{
		ArrayList<Coordinates> obstacleCoords = new ArrayList<Coordinates>();
		
		for(int row = 0; row < getNumRows(); row++) 
		{
			for(int column = 0; column < getNumColumns(); column++) 
			{
				if(getValue(row, column) == 3)
					obstacleCoords.add(utils.Utils.worldPointFromMatrix(new Coordinates(column, row), getNumRows()));
			}
		}
		
		Coordinates nearestObstacle = null;
		double distance = 99999;
		
		for (int i = 0; i < obstacleCoords.size();i++)
		{
			if ( distanceTwoPoints(agentPosition, obstacleCoords.get(i)) < distance )
			{
				distance = distanceTwoPoints(agentPosition, obstacleCoords.get(i));
				nearestObstacle = obstacleCoords.get(i);
			}
		}
		return nearestObstacle;
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
    
    public int getValueIfPossRow(int row, int column, int value) 
    {
    	if (value >= 0)
    	{
    		if ( row+value < 0 || row+value > matrix.length-1)
        		return -1;
    		else 
    			return matrix[row+value][column];
    	} else if ( row-value < 0 || row-value > matrix.length-1)
        		return -1;
    	else 
    		return matrix[row-value][column];
    }
    
    public int getValueIfPossCol(int row, int column, int value) 
    {
    	if (value >= 0) {
    		if ( column+value < 0 || column+value > matrix[0].length-1)
        		return -1;
    		else
    			return matrix[row][column+value];
    	} else if ( column-value < 0 || column-value > matrix[0].length-1)
    		return -1;
    	else
    		return matrix[row][column-value];
    }
    
    public int getValueIfPossBoth(int row, int column, int valueRow, int valueCol) 
    {
    	if ( getValueIfPossRow(row,column,valueRow) == -1 || getValueIfPossCol(row,column,valueCol) == -1)
    		return -1;
    	
    	if (valueRow > 0 && valueCol > 0)
    		return matrix[row+valueRow][column+valueCol];
    	else if (valueRow < 0 && valueCol < 0)
    		return matrix[row-valueRow][column-valueCol];
    	else if (valueRow < 0 && valueCol > 0)
    		return matrix[row-valueRow][column+valueCol];
    	else if (valueRow > 0 && valueCol < 0)
    		return matrix[row+valueRow][column-valueCol];
    	
    	return -1;
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
