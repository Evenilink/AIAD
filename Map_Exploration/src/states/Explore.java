package states;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import agents.Explorer;
import behaviours.Exploration;
import entities.Obstacle;
import repast.simphony.query.space.grid.GridCell;
import utils.Coordinates;

public class Explore implements IAgentState {

	private Exploration behaviour;
	
	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;
	}

	@Override
	public void execute() {
		List<GridCell<Object>> neighborhoodCells = behaviour.getNeighborhoodCells();
		
		List<Coordinates> obstaclesCcoordinates = existsNearObstacle(neighborhoodCells);
		if(!obstaclesCcoordinates.isEmpty()) {
			for (Coordinates coordinates : obstaclesCcoordinates) {
				if(!behaviour.getPledge().alreadyVisited(coordinates)) {
					behaviour.changeState(new DiscoverObstacleBounds());
					break;
				}
			}
		}

		GridCell<Object> targetCell = behaviour.getDFS().execute(neighborhoodCells);
		if(targetCell != null)
			behaviour.moveAgentToCoordinate(Coordinates.FromGridPoint(targetCell.getPoint()));
		else behaviour.changeState(new TravelNearestUndiscovered());
	}

	@Override
	public void exit() {
		
	}
	
	private List<Coordinates> existsNearObstacle(List<GridCell<Object>> neighborhoodCells) {
		List<Coordinates> obstaclesCcoordinates = new ArrayList<>();
		
		for (GridCell<Object> gridCell : neighborhoodCells) {
			Iterator it = gridCell.items().iterator();
			while(it.hasNext()) {
				Object obj = it.next();
				if(obj instanceof Obstacle)
					obstaclesCcoordinates.add(new Coordinates(gridCell.getPoint().getX(), gridCell.getPoint().getY()));
			}
		}
		
		return obstaclesCcoordinates;
	}

}
