package states;

import java.util.List;

import behaviours.Exploration;
import repast.simphony.query.space.grid.GridCell;
import utils.Coordinates;
import utils.Utils;

public class Explore implements IAgentState {

	private Exploration behaviour;
	
	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;
	}

	@Override
	public void execute() {
		List<GridCell<Object>> neighborhoodCells = behaviour.getNeighborhoodCells();
		List<GridCell<Object>> obstacles = Utils.getObstacleCells(neighborhoodCells);
		for (GridCell<Object> obstacle : obstacles) {
			if (!behaviour.getPledge().alreadyVisited(behaviour.getAgentCoordinates(), obstacle)) {
				DiscoverObstacleBounds state = new DiscoverObstacleBounds();
				state.setObstacle(obstacle);
				behaviour.changeState(state);
				return;
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
}
