package algorithms.astar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import agents.Explorer;
import repast.simphony.space.grid.GridPoint;
import utils.Coordinates;
import utils.Utils;

public class AStar {

	private Explorer agent;
	private Pathfinding pathfinding;
	private List<Coordinates> dynamicNotWalkable;

	public AStar(Explorer agent) {
		this.agent = agent;
		pathfinding = new Pathfinding(agent.getGrid().getDimensions().getWidth(),
				agent.getGrid().getDimensions().getHeight());
		dynamicNotWalkable = new ArrayList<>();
	}

	public List<Node> computePath(Coordinates sourceWorldPosition, Coordinates targetWorldPosition) {
		return pathfinding.FindPath(sourceWorldPosition, targetWorldPosition);
	}

	/**
	 * Returns the path to traverse in order to reach the exit.
	 * @return Path to exit or null if there is none.
	 */
	public List<Node> getPathToExit() {
		Coordinates exit = agent.getMatrix().getExit();
		if (exit != null) {
			GridPoint pt = agent.getGrid().getLocation(agent);
			return computePath(new Coordinates(pt.getX(), pt.getY()), exit);
		}
		return null;
	}
	
	public void addDynamicNotWalkable(Coordinates coordinates) {
		dynamicNotWalkable.add(coordinates);
		setNodeWalkable(coordinates, false);
	}
	
	public void resetDynamicNotWalkable() {
		for(Iterator<Coordinates> it = dynamicNotWalkable.iterator(); it.hasNext(); ) {
			Coordinates coordinates = it.next();
			setNodeWalkable(coordinates, true);
			it.remove();
		}
	}

	/**
	 * Returns the nearest coordinate that has not yet been discovered based on the
	 * agent's position.
	 * 
	 * @param currentPosition
	 * @return
	 */
	public List<Node> getNearestObstacle(GridPoint currentPosition) {
		Coordinates currCoordinates = utils.Utils.matrixFromWorldPoint(currentPosition,
				agent.getGrid().getDimensions().getHeight());
		Coordinates nearestUndiscovered = null;

		int matrixMaxIndexX = agent.getGrid().getDimensions().getWidth() - 1;
		int leftCellsAmount = matrixMaxIndexX - currCoordinates.getX();
		int rightCellsAmount = matrixMaxIndexX - (matrixMaxIndexX - currCoordinates.getX());
		int matrixMaxIndexY = agent.getGrid().getDimensions().getHeight() - 1;
		int upperCellsAmount = matrixMaxIndexY - currCoordinates.getY();
		int bottomCellsAmount = matrixMaxIndexY - (matrixMaxIndexY - currCoordinates.getY());

		int[] values = { leftCellsAmount, rightCellsAmount, upperCellsAmount, bottomCellsAmount };
		int maxDistance = utils.Utils.findMax(values); // Distance from the
														// current position to
														// the furthest edge of
														// the matrix.

		for (int radious = 2; radious <= maxDistance; radious++) {
			nearestUndiscovered = getObstacleInRadius(currCoordinates, radious);
			if (nearestUndiscovered != null) {
				List<Node> path = computePath(Coordinates.FromGridPoint(currentPosition), utils.Utils
						.worldPointFromMatrix(nearestUndiscovered, agent.getGrid().getDimensions().getHeight()));
				if (path != null)
					return path;
			}
		}

		return null;
	}

	/**
	 * Returns the first coordinate that has a zero on the matrix (is undiscovered)
	 * distancing 'radious' from the agent.
	 * 
	 * @param currCoordinates
	 * @param radious
	 * @return
	 */
	private Coordinates getObstacleInRadius(Coordinates currCoordinates, int radious) {
		Coordinates nearestCoordinate = null;
		float nearestDistance = Float.MAX_VALUE;

		for (int column = currCoordinates.getX() - radious; column <= currCoordinates.getX() + radious; column++) {
			for (int row = currCoordinates.getY() - radious; row <= currCoordinates.getY() + radious; row++) {
				// Are the points on the grid?
				if (column >= 0 && column < agent.getGrid().getDimensions().getWidth() && row >= 0
						&& row < agent.getGrid().getDimensions().getHeight()) {
					// Do the points not belong in the radious?
					if (column != currCoordinates.getX() - radious && column != currCoordinates.getX() + radious
							&& row != currCoordinates.getY() - radious && row != currCoordinates.getY() + radious)
						continue;

					Coordinates coordinates = new Coordinates(column, row);
					float distance = utils.Utils.getDistance(currCoordinates, coordinates);
					// Gets the cell which has an obstacle nearby (+/- 1 cell distance N/S/E/W )
					if ((	 (agent.getMatrix().getValue(row, column) != Utils.CODE_OBSTACLE_DOOR  && agent.getMatrix().getValueIfPossRow(row, column, +1) == Utils.CODE_OBSTACLE_DOOR) ||
							 (agent.getMatrix().getValue(row, column) != Utils.CODE_OBSTACLE_DOOR  && agent.getMatrix().getValueIfPossRow(row, column, -1) == Utils.CODE_OBSTACLE_DOOR) || 
							 (agent.getMatrix().getValue(row, column) != Utils.CODE_OBSTACLE_DOOR  && agent.getMatrix().getValueIfPossCol(row, column, -1) == Utils.CODE_OBSTACLE_DOOR) ||
							 (agent.getMatrix().getValue(row, column) != Utils.CODE_OBSTACLE_DOOR  && agent.getMatrix().getValueIfPossCol(row, column, +1) == Utils.CODE_OBSTACLE_DOOR)) 
							&& distance < nearestDistance) {
						nearestCoordinate = coordinates;
						nearestDistance = distance;
					}
				}
			}
		}
		
		System.out.println(nearestCoordinate);
		return nearestCoordinate;
		
	}

	// TODO: move this function to utils.
	/**
	 * Returns the nearest coordinate that has not yet been discovered based on the
	 * agent's position.
	 * 
	 * @param currentPosition
	 * @return
	 */
	public List<Node> getNearestUndiscoveredPlace(GridPoint currentPosition) {
		Coordinates currCoordinates = utils.Utils.matrixFromWorldPoint(currentPosition,
				agent.getGrid().getDimensions().getHeight());
		Coordinates nearestUndiscovered = null;

		int matrixMaxIndexX = agent.getGrid().getDimensions().getWidth() - 1;
		int leftCellsAmount = matrixMaxIndexX - currCoordinates.getX();
		int rightCellsAmount = matrixMaxIndexX - (matrixMaxIndexX - currCoordinates.getX());
		int matrixMaxIndexY = agent.getGrid().getDimensions().getHeight() - 1;
		int upperCellsAmount = matrixMaxIndexY - currCoordinates.getY();
		int bottomCellsAmount = matrixMaxIndexY - (matrixMaxIndexY - currCoordinates.getY());

		int[] values = { leftCellsAmount, rightCellsAmount, upperCellsAmount, bottomCellsAmount };
		int maxDistance = utils.Utils.findMax(values); // Distance from the
														// current position to
														// the furthest edge of
														// the matrix.

		for (int radious = 2; radious <= maxDistance; radious++) {
			nearestUndiscovered = getEntityInRadius(currCoordinates, radious, 0);
			if (nearestUndiscovered != null) {
				List<Node> path = computePath(Coordinates.FromGridPoint(currentPosition), utils.Utils
						.worldPointFromMatrix(nearestUndiscovered, agent.getGrid().getDimensions().getHeight()));
				if (path != null)
					return path;
			}
		}

		return null;
	}

	/**
	 * Returns the first coordinate that has a zero on the matrix (is undiscovered)
	 * distancing 'radious' from the agent.
	 * 
	 * @param currCoordinates
	 * @param radious
	 * @param cellValue 
	 * @return
	 */
	private Coordinates getEntityInRadius(Coordinates currCoordinates, int radious, int cellValue) {
		Coordinates nearestCoordinate = null;
		float nearestDistance = Float.MAX_VALUE;

		for (int column = currCoordinates.getX() - radious; column <= currCoordinates.getX() + radious; column++) {
			for (int row = currCoordinates.getY() - radious; row <= currCoordinates.getY() + radious; row++) {
				// Are the points on the grid?
				if (column >= 0 && column < agent.getGrid().getDimensions().getWidth() && row >= 0
						&& row < agent.getGrid().getDimensions().getHeight()) {
					// Do the points not belong in the radious?
					if (column != currCoordinates.getX() - radious && column != currCoordinates.getX() + radious
							&& row != currCoordinates.getY() - radious && row != currCoordinates.getY() + radious)
						continue;

					Coordinates coordinates = new Coordinates(column, row);
					float distance = utils.Utils.getDistance(currCoordinates, coordinates);
					if (agent.getMatrix().getValue(row, column) == cellValue && distance < nearestDistance) {
						nearestCoordinate = coordinates;
						nearestDistance = distance;
					}
				}
			}
		}
		return nearestCoordinate;
	}

	public void setNodeWalkable(Coordinates coordinates, boolean newWalkable) {
		pathfinding.setNodeWalkable(coordinates, newWalkable);
	}

	public void printGrid() {
		pathfinding.printGrid();
	}
}
