package states;

import java.util.List;

import algorithms.astar.Node;
import behaviours.Exploration;

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
}
