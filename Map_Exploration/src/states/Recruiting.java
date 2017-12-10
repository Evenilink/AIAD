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
	
	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;
		Coordinates exit = behaviour.getAgent().getMatrix().getExit();
		
		int column, row;
		while(true) {
			column = ThreadLocalRandom.current().nextInt(0, behaviour.getAgent().getGrid().getDimensions().getWidth() - 1);
			row = ThreadLocalRandom.current().nextInt(0, behaviour.getAgent().getGrid().getDimensions().getHeight() - 1);
			Iterator<Object> it = behaviour.getAgent().getGrid().getObjectsAt(column, row).iterator();
			boolean canReach = false;
			while(it.hasNext()) {
				Object obj = it.next();
				if(obj instanceof Obstacle || obj instanceof Explorer)
					canReach = true;
			}
			if(!canReach)
				break;			
		}
		
		Coordinates source = behaviour.getAgentCoordinates();
		if(source != null) {
			path = behaviour.getAStar().computePath(source, new Coordinates(column, row));
			System.out.println("Target hello => " + new Coordinates(column, row).toString());
			behaviour.getAgent().getMatrix().printMatrix();

			behaviour.getAStar().printGrid();
			printPath();
			pathNode = 0;	
		}
	}

	@Override
	public void execute() {
		if(behaviour.moveAgentToCoordinate(path.get(pathNode).getWorldPosition()))
			pathNode++;
		if(pathNode == path.size())
			behaviour.changeState(new TravelExit());		
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
