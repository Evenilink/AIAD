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
		System.out.println("Hello from travelling to obstacle");
		this.behaviour = behaviour;
		path = behaviour.getAStar().getPathToNearestObstacle();
		pathNode = 0;
	}

	@Override
	public void execute()
	{
		behaviour.moveAgentToCoordinate(path.get(pathNode).getWorldPosition());
		pathNode++;
		
		if(pathNode == path.size()) 
		{
			//TODO
		} 
	}

	@Override
	public void exit()
	{
		// TODO Auto-generated method stub
		
	}

}
