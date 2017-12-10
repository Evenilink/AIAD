package states;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import agents.Explorer;
import algorithms.astar.Node;
import behaviours.Exploration;
import entities.Obstacle;
import utils.Coordinates;

public class Recruiting implements IAgentState {

	private Exploration behaviour;
	private List<Node> path;
	private int pathNode;
	private Coordinates target;
	
	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;
		Coordinates exit = behaviour.getAgent().getMatrix().getExit();
		
		int column, row;
		while(true) {
			column = ThreadLocalRandom.current().nextInt(0, behaviour.getAgent().getGrid().getDimensions().getWidth() - 1);
			row = ThreadLocalRandom.current().nextInt(0, behaviour.getAgent().getGrid().getDimensions().getHeight() - 1);
			Iterator<Object> it = behaviour.getAgent().getGrid().getObjectsAt(column, row).iterator();
			boolean canReach = true;
			while(it.hasNext()) {
				Object obj = it.next();
				if(obj instanceof Obstacle || obj instanceof Explorer)
					canReach = false;
			}
			if(canReach) {
				target = new Coordinates(column, row);
				break;
			}
		}
	}

	@Override
	public void execute() {
		path = behaviour.getAStar().computePath(behaviour.getAgentCoordinates(), target);
		if(path != null) {
			System.err.println("Going to " + path.get(0).getWorldPosition().toString());
			if(behaviour.moveAgentToCoordinate(path.get(0).getWorldPosition()))
			if(path.size() == 1)
				behaviour.changeState(new TravelExit());	
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
