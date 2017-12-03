package behaviours;

import agents.Explorer;
import sajas.core.behaviours.CyclicBehaviour;

public class GoingExit extends CyclicBehaviour {

	private Explorer agent;
		
	public GoingExit(Explorer agent) {
		super(agent);
		this.agent = agent;
		agent.getAStar().goToExit();
	}
	
	@Override
	public void action() {
		agent.getAStar().run();
	}

}
