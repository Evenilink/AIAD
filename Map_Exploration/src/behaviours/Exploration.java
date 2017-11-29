package behaviours;

import agents.Explorer;
import algorithms.astar.AStar;
import algorithms.dfs.DFS;
import algorithms.pledge.Pledge;
import repast.simphony.space.grid.GridPoint;
import sajas.core.behaviours.CyclicBehaviour;
import utils.Coordinates;
import utils.Utils.ExplorerState;

public class Exploration extends CyclicBehaviour {

	private Explorer agent;
	private ExplorerState state;
		
	private DFS dfs;
	private AStar astar;
	private Pledge pledge;
	
	public Exploration(Explorer agent) {
		super(agent);
		this.agent = agent;
		state = ExplorerState.DFS;
		
		dfs = new DFS(agent, this);
		astar = new AStar(agent, this);
		pledge = new Pledge(agent);
	}

	@Override
	public void action() {	
		switch(state) {
			case DFS:
				dfs.run();
				break;
			case A_STAR:
				astar.run();
				break;
			case PLEDGE:
				pledge.run();
				break;
			case EXIT:
				break;
			default: break;
		}
	}
	
	public void changeState(ExplorerState newState) {
		switch(newState) {
			case A_STAR:
				GridPoint pt = agent.getGrid().getLocation(agent);
				Coordinates nearestUndiscovered = getNearestUndiscoveredPlace(pt);
				if(nearestUndiscovered != null) {
					astar.setPath(new Coordinates(pt.getX(), pt.getY()), nearestUndiscovered);
				}
				else {
					//TODO: Exit must be found, implement coop algorithm
				}
				break;
			case PLEDGE:
				pledge.init();
				break;
			default: break;
		}
		
		state = newState;
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
					if(agent.getMatrix().getValue(column, row) == 0 && distance < nearestDistance) {
						nearestCoordinate = coordinates;
						nearestDistance = distance;
					}
				}
			}
		}
		
		return nearestCoordinate;
	}
}
