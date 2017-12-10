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
		if (Utils.hasObstacle(neighborhoodCells)) {
			if (!behaviour.getPledge().alreadyVisited(behaviour.getAgentCoordinates())) {
				DiscoverObstacleBounds state = new DiscoverObstacleBounds();
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
