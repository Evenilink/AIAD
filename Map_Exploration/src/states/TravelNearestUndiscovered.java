package states;

import java.util.List;

import algorithms.astar.Node;
import behaviours.Exploration;
import repast.simphony.space.grid.GridPoint;
import utils.Coordinates;

public class TravelNearestUndiscovered implements IAgentState {

	private Exploration behaviour;
	private List<Node> path;
	private int pathNode;

	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;

		if (behaviour.getAgent().getMatrix().hasUndiscoveredCells()) {
			GridPoint sourcePoint = behaviour.getAgentPoint();
			Coordinates nearestUndiscovered = behaviour.getAStar().getNearestUndiscoveredPlace(sourcePoint);
			if (nearestUndiscovered != null) {
				path = behaviour.getAStar().computePath(new Coordinates(sourcePoint.getX(), sourcePoint.getY()),
						nearestUndiscovered);
				if(path != null) {
					pathNode = 0;
					System.out.println("Going here");
					// printPath();
				} else {
					System.err.println("Going to travel obstacle");
					behaviour.changeState(new TravelToObstacle());
				}
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

		if (pathNode == path.size()) {
			Coordinates source = behaviour.getAgentCoordinates();
			behaviour.changeState(new Explore());
		}
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}
}
