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
		
		if(behaviour.getAgent().getMatrix().hasUndiscoveredCells()) {
			GridPoint sourcePoint = behaviour.getAgentPoint();
			Coordinates nearestUndiscovered = behaviour.getAStar().getNearestUndiscoveredPlace(sourcePoint);
			if(nearestUndiscovered != null) {
				path = behaviour.getAStar().computePath(new Coordinates(sourcePoint.getX(), sourcePoint.getY()), nearestUndiscovered);
				pathNode = 0;
			} else System.err.println("There should be an undiscovered cell.");
		} else {
			System.out.println("Map is fully explored.");
			behaviour.changeState(new TravelExit());
		}
	}

	@Override
	public void execute() {
		Coordinates target = new Coordinates(path.get(pathNode).getWorldPosition().getX(), path.get(pathNode).getWorldPosition().getY());
		behaviour.moveAgentToCoordinate(target);
		pathNode++;
		
		if(pathNode == path.size()) {
			Coordinates source = behaviour.getAgentCoordinates();
			if(!behaviour.getAgent().getMatrix().hasUndiscoveredCells() && behaviour.getAgent().getMatrix().getExit().equals(source)) {
				// TODO: agent has explored all the map and has reached the exit.
				
			} else behaviour.changeState(new Explore());			
		}
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

}
