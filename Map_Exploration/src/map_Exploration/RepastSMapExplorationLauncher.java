package map_Exploration;

import sajas.sim.repasts.RepastSLauncher;

import java.util.ArrayList;

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
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.StrictBorders;
import sajas.wrapper.ContainerController;
import sajas.core.Runtime;

public class RepastSMapExplorationLauncher extends RepastSLauncher {

	private static int NUM_AGENTS = 5;
	private static int NUM_SUPER_AGENTS = 2;
	private static int COMMUNICATION_LIMIT = 10;
	private static int VISION_RADIOUS = 5;
	
	private ContainerController mainContainer;
	
	private ArrayList<Explorer> explorers;
	
	@Override
	public String getName() {
		return "Map Exploration Test";
	}

	@Override
	protected void launchJADE() {
		System.out.println("Correndo Jade...\n\n\n.......\n\n\n");
		Runtime runtime = Runtime.instance();
		Profile profile = new ProfileImpl();
		mainContainer = runtime.createMainContainer(profile);
		
		launchAgents();
	}
	
	private void launchAgents() {
		try {
			for(int i = 0; i < NUM_AGENTS; i++)
				mainContainer.acceptNewAgent("Explorer", explorers.get(i)).start();
		} catch(StaleProxyException e) {
			System.out.println("Esta a falhar aqui");
			e.printStackTrace();
		}
	}
	
	@Override
	public Context build(Context<Object> context) {
		NetworkBuilder<Object> netBuilder = new NetworkBuilder<Object>("Map Exploration Network", context, true);
		netBuilder.buildNetwork();
		
		explorers = new ArrayList<Explorer>();
		
		System.out.println("1");
		context.setId("Map Exploration");
		System.out.println("2");

		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace("space", context, new RandomCartesianAdder<Object>(), new repast.simphony.space.continuous.StrictBorders(), 50, 50);
		System.out.println("3");

		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
		Grid<Object> grid = gridFactory.createGrid("grid", context, 
				new GridBuilderParameters<Object>(
						new StrictBorders(), 
						new SimpleGridAdder<Object>(), 
						true, 50, 50));
		System.out.println("4");

		for(int i = 0; i < NUM_AGENTS; i++) {
			Explorer agent = new Explorer(space, grid);
			explorers.add(agent);
			context.add(agent);
		}
		System.out.println("5");

		for(Object obj : context) {
			NdPoint pt = space.getLocation(obj);
			grid.moveTo(obj, (int)pt.getX(), (int)pt.getY());
		}
		System.out.println("6");

		return super.build(context);
	}

}
