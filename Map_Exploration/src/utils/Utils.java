package utils;

import repast.simphony.space.grid.GridPoint;

public class Utils {
	
	public static final double sqrt2 = Math.sqrt(2);
	
	public enum ExplorerState {
		DFS,
		A_STAR,
		PLEDGE,
		EXIT
		// What if we have an IDLE state??????
	}
	
	public enum MessageType {
		MATRIX,
		HELP
	}
	
	public static float getDistance(Coordinates source, Coordinates target) {
		int distX = Math.abs(target.getX() - source.getX());
		int distY = Math.abs(target.getY() - source.getY());
		
		if(distX > distY)
			return (float)sqrt2 * distY + 1 * (distX - distY);
		return (float)sqrt2 + 1 * (distY - distX);
	}
	
	/**
	 * Calculates the matrix coordinate from a world point.
	 * @param point
	 * @return
	 */
	public static Coordinates matrixFromWorldPoint(GridPoint point, int gridSizeY) {
		return new Coordinates(point.getX(), gridSizeY - 1 - point.getY());
	}
	
	public static Coordinates worldPointFromMatrix(Coordinates coordinates, int gridSizeY) {
		return new Coordinates(coordinates.getX(), gridSizeY - 1 - coordinates.getY());
	}
}
