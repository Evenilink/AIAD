package utils;

import entities.Obstacle;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.space.grid.GridPoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Utils {
	public static final double sqrt2 = Math.sqrt(2);

	public enum MessageType {
		MATRIX,
		HELP,
		OTHER_GUARDING,
		REACHED_EXIT,
		OBSTACLEDOOR_DESTROYED,
		WAITING_TO_BREAK,
		OBSTACLEDOOR_DESTROY
	}
	
	public enum AgentType {
		SUPER_AGENT,
		NORMAL_AGENT
	}
	
	// Not being used.
	public enum EntityCode {
		EXIT,
		OBSTACLE
	}
	
	public static final int CODE_UNDISCOVERED = 0;
	public static final int CODE_DISCOVERED = 1;
	public static final int CODE_OBSTACLE_CELL = 2;
	public static final int CODE_OBSTACLE_DOOR = 3;	
	public static final int CODE_EXIT = 4;

	/**
	 * Calculates the distance between two 2D points
	 * @param source The first point
	 * @param target The second point
	 * @return float Returns the distance between the two points
	 */
	public static float getDistance(Coordinates source, Coordinates target) {
		int distX = Math.abs(target.getX() - source.getX());
		int distY = Math.abs(target.getY() - source.getY());
		
		if(distX > distY)
			return (float)sqrt2 * distY + 1 * (distX - distY);
		return (float)sqrt2 * distX + 1 * (distY - distX);
	}

	/**
	 * Gets the highest value of an array
	 * @param values The int array to search
	 * @return int Returns the highest calue
	 */
	public static int findMax(int[] values) {
		int max = Integer.MIN_VALUE;
		
		for(int i = 0; i < values.length; i++) {
			if(values[i] > max)
				max = values[i];
		}
		
		return max;
	}
	
	/**
	 * Calculates the matrix coordinate from a world point.
	 * @param point The point to convert from
	 * @param gridSizeY The height dimension of the grid
	 * @return Coordinates Returns the matrix coordinates
	 */
	public static Coordinates matrixFromWorldPoint(GridPoint point, int gridSizeY) {
		return new Coordinates(point.getX(), gridSizeY - 1 - point.getY());
	}
	
	public static Coordinates matrixFromWorldPoint(Coordinates coordinates, int gridSizeY) {
		return new Coordinates(coordinates.getX(), gridSizeY - 1 - coordinates.getY());
	}

	/**
	 * Calculates the matrix coordinate from a world point.
	 * @param coordinates The coordinates to convert from
	 * @param gridSizeY The height dimension of the grid
	 * @return Coordinates returns the matrix coordinates
	 */
	public static Coordinates worldPointFromMatrix(Coordinates coordinates, int gridSizeY) {
		return new Coordinates(coordinates.getX(), gridSizeY - 1 - coordinates.getY());
	}

	public static boolean hasObstacle(List<GridCell<Object>> cells) {
		for (GridCell<Object> gridCell : cells) {
			Iterator it = gridCell.items().iterator();
			while (it.hasNext())
				if (it.next() instanceof Obstacle) return true;
		}
		return false;
	}

	public static List<GridCell<Object>> getObstacleCells(List<GridCell<Object>> cells) {
		List<GridCell<Object>> obstacleCells = new ArrayList<>();
		for (GridCell<Object> gridCell : cells) {
			Iterator it = gridCell.items().iterator();
			while (it.hasNext())
				if (it.next() instanceof Obstacle) obstacleCells.add(gridCell);
		}
		return obstacleCells;
	}
}
