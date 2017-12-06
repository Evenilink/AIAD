package states;

import behaviours.Exploration;

public class HelpDestroyObstacle implements IAgentState
{

	@Override
	public void enter(Exploration behaviour)
	{
		System.out.println("Hello From HelpDestroyObstacle");
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
