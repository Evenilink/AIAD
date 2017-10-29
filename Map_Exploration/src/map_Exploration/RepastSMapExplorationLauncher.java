package map_Exploration;

import sajas.sim.repasts.RepastSLauncher;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.StaleProxyException;
import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;
import sajas.wrapper.ContainerController;
import sajas.core.Runtime;

public class RepastSMapExplorationLauncher extends RepastSLauncher {

	private static int NUM_ZOMBIES = 2;
	private static int NUM_HUMANS = 3;
	
	private ContainerController mainContainer;
	
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	
	@Override
	public String getName() {
		return "SAJaS Repast Test";
	}

	@Override
	protected void launchJADE() {
		Runtime runtime = Runtime.instance();
		Profile profile = new ProfileImpl();
		mainContainer = runtime.createMainContainer(profile);
		
		launchAgents();
	}
	
	private void launchAgents() {
		try {
			for(int i = 0; i < NUM_ZOMBIES; i++) {
				MyAgent myAgent = new MyAgent();
				mainContainer.acceptNewAgent("MyAgent", myAgent).start();
			}
		} catch(StaleProxyException e) {
			System.out.println("Esta a falhar aqui");
			e.printStackTrace();
		}
		
		/*try {
			for(int i = 0; i < NUM_ZOMBIES; i++) {
				Zombie zombie = new Zombie(space, grid);
				mainContainer.acceptNewAgent("Zombie", zombie).start();
			}
		} catch(StaleProxyException e) {
			e.printStackTrace();
		}*/
	}
	
	@Override
	public Context build(Context<Object> context) {
		//context.setId("aiad");
		
		/*ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		space = spaceFactory.createContinuousSpace("space", context, new RandomCartesianAdder<Object>(), new repast.simphony.space.continuous.WrapAroundBorders(), 50, 50);
		
		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
		grid = gridFactory.createGrid("grid", context, 
				new GridBuilderParameters<Object>(new WrapAroundBorders(), 
						new SimpleGridAdder<Object>(), 
						true, 50, 50))*/
		
		NetworkBuilder<Object> netBuilder = new NetworkBuilder<Object>("Exploration Network Sajas", context, true);
		netBuilder.buildNetwork();
		
		return super.build(context);
	}

}
