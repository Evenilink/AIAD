package algorithms.astar;

import java.util.List;

import agents.Explorer;
import behaviours.Exploration;
import repast.simphony.space.grid.GridPoint;
import utils.Coordinates;
import utils.Utils.ExplorerState;

public class AStar {

	private Explorer agent;
	private Exploration behaviour;
	
	private Pathfinding pathfinding;
	private List<Node> path; 
	private int pathNode;
	
	public AStar(Explorer agent, Exploration behaviour) {
		this.agent = agent;
		this.behaviour = behaviour;
		pathfinding = new Pathfinding(agent.getGrid().getDimensions().getWidth(), agent.getGrid().getDimensions().getHeight());
		pathNode = 0;
	}
	
	public void init() {
		GridPoint pt = agent.getGrid().getLocation(agent);
		Coordinates nearestUndiscovered = getNearestUndiscoveredPlace(pt);
		System.out.println("Current point => x: " + pt.getX() + ", y: " + pt.getY() + "\nNearest unde => x: " + nearestUndiscovered.getX() + ", y: " + nearestUndiscovered.getY());
		if(nearestUndiscovered != null)
			setPath(new Coordinates(pt.getX(), pt.getY()), nearestUndiscovered);
		else {
			//TODO: Exit must be found, implement coop algorithm
		}
	}
	
	public void run() {
		agent.moveAgent(new Coordinates(path.get(pathNode).getWorldPosition().getX(), path.get(pathNode).getWorldPosition().getY()));
		pathNode++;
		
		if(pathNode == path.size()) {
			behaviour.changeState(ExplorerState.DFS);
			path = null;
			pathNode = 0;
		}
	}
	
	public void setPath(Coordinates sourceWorldPosition, Coordinates targetWorldPosition) {
		path = pathfinding.FindPath(sourceWorldPosition, targetWorldPosition);
	}
	
	/**
	 * Returns the nearest coordinate that has not yet been discovered based on the agent's position.
	 * @param currentPosition
	 * @return
	 */
	private Coordinates getNearestUndiscoveredPlace(GridPoint currentPosition) {
		Coordinates currCoordinates = utils.Utils.matrixFromWorldPoint(currentPosition, agent.getGrid().getDimensions().getHeight());
		Coordinates nearestUndiscovered = null;
		
		int maxDistance;	// Distance from the current position to the furthest edge of the matrix.
		if(agent.getGrid().getDimensions().getWidth() > agent.getGrid().getDimensions().getHeight()) {
			int matrixMaxIndexX = agent.getGrid().getDimensions().getWidth() - 1;
			int leftCellsAmount = matrixMaxIndexX - currCoordinates.getX();
			int rightCellsAmount = matrixMaxIndexX - (matrixMaxIndexX - currCoordinates.getX());
			maxDistance = (leftCellsAmount > rightCellsAmount) ? leftCellsAmount : rightCellsAmount;
		} else {
			int matrixMaxIndexY = agent.getGrid().getDimensions().getHeight() - 1;
			int leftCellsAmount = matrixMaxIndexY - currCoordinates.getY();
			int rightCellsAmount = matrixMaxIndexY - (matrixMaxIndexY - currCoordinates.getY());
			maxDistance = (leftCellsAmount > rightCellsAmount) ? leftCellsAmount : rightCellsAmount;
		}
					
		for(int radious = 2; radious < maxDistance; radious++) {
			nearestUndiscovered = getUndiscoveredInRadious(currCoordinates, radious);
			if(nearestUndiscovered != null)
				break;
		}
		
		return utils.Utils.worldPointFromMatrix(nearestUndiscovered, agent.getGrid().getDimensions().getHeight());
	}
	
	/**
	 * Returns the first coordinate that has a zero on the matrix (is undiscovered) distancing 'radious' from the agent.
	 * @param currCoordinates
	 * @param radious
	 * @return
	 */
	private Coordinates getUndiscoveredInRadious(Coordinates currCoordinates, int radious) {
		Coordinates nearestCoordinate = null;
		float nearestDistance = Float.MAX_VALUE;
		
		for(int column = currCoordinates.getX() - radious; column <= currCoordinates.getX() + radious; column++) {
			for(int row = currCoordinates.getY() - radious; row <= currCoordinates.getY() + radious; row++) {
				// Are the points on the grid?
				if(column >= 0 && column < agent.getGrid().getDimensions().getWidth() && row >= 0 && row < agent.getGrid().getDimensions().getHeight()) {
					// Do the points not belong in the radious?
					if(column != currCoordinates.getX() - radious && column != currCoordinates.getX() + radious && row != currCoordinates.getY() - radious && row != currCoordinates.getY() + radious)
						continue;
					
					Coordinates coordinates = new Coordinates(column, row);
					float distance = utils.Utils.getDistance(currCoordinates, coordinates);
					if(agent.getMatrix().getValue(row, column) == 0 && distance < nearestDistance) {
						nearestCoordinate = coordinates;
						nearestDistance = distance;
					}
				}
			}
		}
		
		return nearestCoordinate;
	}
}
