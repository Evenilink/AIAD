package states;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import algorithms.astar.Node;
import behaviours.Exploration;
import utils.Coordinates;

public class Recruiting implements IAgentState {

	private Exploration behaviour;
	private List<Node> path;
	private int pathNode;
	
	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;
		int column = ThreadLocalRandom.current().nextInt(0, behaviour.getAgent().getGrid().getDimensions().getWidth() - 1);
		int row = ThreadLocalRandom.current().nextInt(0, behaviour.getAgent().getGrid().getDimensions().getHeight() - 1);
		path = behaviour.getAStar().computePath(behaviour.getAgentCoordinates(), new Coordinates(column, row));
		pathNode = 0;
	}

	@Override
	public void execute() {
		behaviour.moveAgentToCoordinate(path.get(pathNode).getWorldPosition());
		pathNode++;
		
		if(pathNode == path.size())
			behaviour.changeState(new TravelExit());		
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

}
