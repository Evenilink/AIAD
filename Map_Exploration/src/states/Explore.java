package states;

import java.util.List;

import agents.Explorer;
import behaviours.Exploration;
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
		GridCell<Object> targetCell = behaviour.getDFS().execute(neighborhoodCells);
		
		if(targetCell != null)
			behaviour.moveAgentToCoordinate(Coordinates.FromGridPoint(targetCell.getPoint()));
		else behaviour.changeState(new TravelNearestUndiscovered());
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

}
