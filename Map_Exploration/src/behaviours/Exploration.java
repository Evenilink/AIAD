package behaviours;

import agents.Explorer;
import algorithms.astar.AStar;
import algorithms.dfs.DFS;
import algorithms.pledge.Pledge;
import sajas.core.behaviours.CyclicBehaviour;
import utils.Utils.ExplorerState;

public class Exploration extends CyclicBehaviour {

	private Explorer agent;
	private ExplorerState state;
		
	private DFS dfs;
	private AStar astar;
	private Pledge pledge;
	
	public Exploration(Explorer agent) {
		super(agent);
		this.agent = agent;
		state = ExplorerState.DFS;
		dfs = new DFS(agent, this);
		astar = new AStar(agent, this);
		pledge = new Pledge(agent);
	}

	@Override
	public void action() {
		switch(state) {
			case DFS:
				dfs.run();
				break;
			case A_STAR:
				astar.run();
				break;
			case PLEDGE:
				pledge.run();
				break;
			case EXIT:
				break;
			default: break;
		}
	}
	
	public void changeState(ExplorerState newState) {
		switch(newState) {
			case A_STAR:
				astar.init();
				break;
			case PLEDGE:
				pledge.init();
				break;
			default: break;
		}
		
		state = newState;
	}
}
