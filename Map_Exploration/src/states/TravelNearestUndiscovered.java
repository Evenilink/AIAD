package states;

import java.util.List;

import algorithms.astar.Node;
import behaviours.Exploration;
import repast.simphony.query.space.grid.GridCell;
import utils.Utils;

public class TravelNearestUndiscovered implements IAgentState {

	private Exploration behaviour;
	private List<Node> path;

	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;
		if (!behaviour.getAgent().getMatrix().hasUndiscoveredCells()) {
			System.out.println("Map is fully explored.");
			behaviour.changeState(new TravelExit());
		}
	}

	@Override
	public void execute() {
		List<GridCell<Object>> neighborhoodCells = behaviour.getNeighborhoodCells();
		if (Utils.hasObstacle(neighborhoodCells)) {
			if (!behaviour.getPledge().alreadyVisited(behaviour.getAgentCoordinates())) {
				DiscoverObstacleBounds state = new DiscoverObstacleBounds();
				state.setObstacle(Utils.getFirstObstacleCell(neighborhoodCells));
				behaviour.changeState(state);
				return;
			}
		}
		
		path = behaviour.getAStar().getNearestUndiscoveredPlace(behaviour.getAgentPoint());
		if (path != null) {
			//printPath();
			behaviour.moveAgentToCoordinate(path.get(0).getWorldPosition());
			if(path.size() == 1)
				behaviour.changeState(new Explore());
		} else {
			System.err.println("Going to travel obstacle");
			behaviour.changeState(new TravelToObstacle());
		}
	}

	@Override
	public void exit() {
		
	}
	
	private void printPath() {
		System.out.println("Path");
		for(int i = 0; i < path.size(); i++) {
			System.out.println("   " + (i + 1) + ": " + path.get(i).getWorldPosition().toString());
		}
		System.out.println("End path");
		System.out.println();
	}
}
