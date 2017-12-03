package states;

import behaviours.Exploration;

public interface IAgentState {	
	void enter(Exploration behaviour);
	void execute();
	void exit();
}
