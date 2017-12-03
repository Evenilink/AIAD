package states;

import java.util.List;

import algorithms.astar.Node;
import behaviours.Exploration;
import utils.Coordinates;

public class TravelExit implements IAgentState {

	private Exploration behaviour;
	private List<Node> path;
	private int pathNode;
	
	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;
		path = behaviour.getAStar().getPathToExit();
		pathNode = 0;
		if(path == null)
			System.err.println("The exit should have been found already.");
	}

	@Override
	public void execute() {
		Coordinates target = new Coordinates(path.get(pathNode).getWorldPosition().getX(), path.get(pathNode).getWorldPosition().getY());
		behaviour.moveAgentToCoordinate(target);
		pathNode++;
		
		if(pathNode == path.size()) {
			System.out.println("Agent has reached the exit and is idle.");
			behaviour.changeState(new Idle());
		}
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

	
}
