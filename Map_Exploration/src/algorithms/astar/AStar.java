package algorithms.astar;

import java.util.ArrayList;
import java.util.List;

import agents.Explorer;
import repast.simphony.space.grid.GridPoint;
import utils.Coordinates;

public class AStar {

	private Explorer agent;
	private Pathfinding pathfinding;

	public AStar(Explorer agent) {
		this.agent = agent;
		pathfinding = new Pathfinding(agent.getGrid().getDimensions().getWidth(),
				agent.getGrid().getDimensions().getHeight());
	}

	public List<Node> computePath(Coordinates sourceWorldPosition, Coordinates targetWorldPosition) {
		return pathfinding.FindPath(sourceWorldPosition, targetWorldPosition);
	}

	/**
	 * Sets the current path to traverse for the exit.
	 */
	public List<Node> getPathToExit() {
		Coordinates exit = agent.getMatrix().getExit();
		if (exit != null) {
			// System.out.println("Exit => x: " + exit.getX() + ", y: " +
			// exit.getY());
			GridPoint pt = agent.getGrid().getLocation(agent);
			return computePath(new Coordinates(pt.getX(), pt.getY()), exit);
		}
		return null;
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
					// Gets the cell which has an obstacle nearby (+/- 1 cell distance)
					if (((agent.getMatrix().getValue(row, column) == 1
							&& agent.getMatrix().getValueIfPossRow(row, column, +1) == 3)
							|| (agent.getMatrix().getValue(row, column) == 1
									&& agent.getMatrix().getValueIfPossRow(row, column, -1) == 3)
							|| (agent.getMatrix().getValue(row, column) == 1
									&& agent.getMatrix().getValueIfPossCol(row, column, -1) == 3)
							|| (agent.getMatrix().getValue(row, column) == 1
									&& agent.getMatrix().getValueIfPossCol(row, column, +1) == 3)
							|| (agent.getMatrix().getValue(row, column) == 1
									&& agent.getMatrix().getValueIfPossBoth(row, column, +1, +1) == 3)
							|| (agent.getMatrix().getValue(row, column) == 1
									&& agent.getMatrix().getValueIfPossBoth(row, column, -1, +1) == 3)
							|| (agent.getMatrix().getValue(row, column) == 1
									&& agent.getMatrix().getValueIfPossBoth(row, column, -1, -1) == 3)
							|| (agent.getMatrix().getValue(row, column) == 1
									&& agent.getMatrix().getValueIfPossBoth(row, column, +1, -1) == 3))
							&& distance < nearestDistance) {
						nearestCoordinate = coordinates;
						nearestDistance = distance;
					}
				}
			}
		}

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
