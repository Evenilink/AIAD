package algorithms.astar;

import java.util.Collections;
import java.util.List;

import agents.Explorer;
import behaviours.Exploration;
import jade.util.leap.Collection;
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
		if(nearestUndiscovered != null)
			setPath(new Coordinates(pt.getX(), pt.getY()), nearestUndiscovered);
		else {
			System.out.println("Already explored the whole map\nCurrent position => x: " + pt.getX() + ", y: " + pt.getY());
			behaviour.changeState(ExplorerState.EXIT);
			//TODO: Exit must be found, implement coop algorithm. We already know all the places.
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
		
		int matrixMaxIndexX = agent.getGrid().getDimensions().getWidth() - 1;
		int leftCellsAmount = matrixMaxIndexX - currCoordinates.getX();
		int rightCellsAmount = matrixMaxIndexX - (matrixMaxIndexX - currCoordinates.getX());
		int matrixMaxIndexY = agent.getGrid().getDimensions().getHeight() - 1;
		int upperCellsAmount = matrixMaxIndexY - currCoordinates.getY();
		int bottomCellsAmount = matrixMaxIndexY - (matrixMaxIndexY - currCoordinates.getY());
		
		int[] values = {leftCellsAmount, rightCellsAmount, upperCellsAmount, bottomCellsAmount};
		int maxDistance = utils.Utils.findMax(values);		// Distance from the current position to the furthest edge of the matrix.

		for(int radious = 2; radious <= maxDistance; radious++) {
			nearestUndiscovered = getUndiscoveredInRadious(currCoordinates, radious);
			if(nearestUndiscovered != null)
				break;
		}
		
		if(nearestUndiscovered == null)
			return null;
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
