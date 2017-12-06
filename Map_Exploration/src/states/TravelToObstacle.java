package states;

import java.util.List;

import algorithms.astar.Node;
import behaviours.Exploration;
import repast.simphony.space.grid.GridPoint;
import utils.Coordinates;

public class TravelToObstacle implements IAgentState
{
	private Exploration behaviour;
	private List<Node> path;
	private int pathNode;
	
	@Override
	public void enter(Exploration behaviour)
	{
		this.behaviour = behaviour;
		path = behaviour.getAStar().getNearestObstacle(behaviour.getAgentPoint());
		
		if (path == null) {
			System.err.println("Path was null...travelling to exit");
			behaviour.changeState(new TravelExit());
		}
		
		//System.out.println(path);
		pathNode = 0;
	}

	@Override
	public void execute()
	{
		Coordinates target = new Coordinates(path.get(pathNode).getWorldPosition().getX(), path.get(pathNode).getWorldPosition().getY());
		if (behaviour.moveAgentToCoordinate(target))
			pathNode++;
		
		if(pathNode == path.size()) 
		{
			behaviour.changeState(new HelpDestroyObstacle());
		} 
	}

	@Override
	public void exit()
	{
		// TODO Auto-generated method stub
		
	}

}
