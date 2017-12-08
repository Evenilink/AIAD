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

		if (behaviour.getAgent().getMatrix().hasUndiscoveredCells()) {
			path = behaviour.getAStar().getNearestUndiscoveredPlace(behaviour.getAgentPoint());
			if (path != null) {
				pathNode = 0;
				System.out.println("Going to nearest zero: " + path.get(path.size() - 1).getWorldPosition().getX()
						+ " and " + path.get(path.size() - 1).getWorldPosition().getY());
				printPath();
			} else {
				System.err.println("Going to travel obstacle");
				behaviour.changeState(new TravelToObstacle());
			}
		} else {
			System.out.println("Map is fully explored.");
			behaviour.changeState(new TravelExit());
		}
	}

	@Override
	public void execute() {
		Coordinates target = new Coordinates(path.get(pathNode).getWorldPosition().getX(),
				path.get(pathNode).getWorldPosition().getY());
		if (behaviour.moveAgentToCoordinate(target))
			pathNode++;

		if (pathNode == path.size())
			behaviour.changeState(new Explore());
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

	private void printPath() {
		System.out.println("Printing path");
		for (Node node : path) {
			System.out.println("x: " + node.getWorldPosition().getX() + ", y: " + node.getWorldPosition().getY());
		}
		System.out.println("End");
	}
}
