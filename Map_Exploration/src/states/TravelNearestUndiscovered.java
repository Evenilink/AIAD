package states;

import java.util.List;

import algorithms.astar.Node;
import behaviours.Exploration;
import utils.Coordinates;

public class TravelNearestUndiscovered implements IAgentState {

	private Exploration behaviour;
	private List<Node> path;
	private int pathNode;

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
		path = behaviour.getAStar().getNearestUndiscoveredPlace(behaviour.getAgentPoint());

		if (path != null) {
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
		//System.out.println("Printing path");
		for (Node node : path) {
			//System.out.println("x: " + node.getWorldPosition().getX() + ", y: " + node.getWorldPosition().getY());
		}
		//System.out.println("End");
	}
}
