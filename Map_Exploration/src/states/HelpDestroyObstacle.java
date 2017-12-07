package states;

import java.util.List;
import behaviours.Exploration;
import communication.*;
import repast.simphony.query.space.grid.GridCell;
import sajas.core.AID;
import utils.Utils;

public class HelpDestroyObstacle implements IAgentState
{
	private Exploration behaviour;
	private int numberAgentsNeeded;

	@Override
	public void enter(Exploration behaviour)
	{
		//Get minimum number of agents to destroy obstacle
		//Check if around me there's that number of agents, that can help
		//if not, go to a random position on the map and recruit
		//Come back, destroy, and search the obstacle
	}

	@Override
	public void execute()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void exit()
	{
		// TODO Auto-generated method stub
		
	}

}
