package build;

import sajas.sim.repasts.RepastSLauncher;

import java.util.ArrayList;

import agents.Explorer;
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
import repast.simphony.space.grid.WrapAroundBorders;
import sajas.wrapper.ContainerController;
import visualization.ExplorerVis;
import sajas.core.Runtime;

public class RepastSMapExplorationLauncher extends RepastSLauncher {

	private static int NUM_AGENTS = 1;
	private static int NUM_SUPER_AGENTS = 2;
	private static int COMMUNICATION_LIMIT = 10;
	private static int VISION_RADIOUS = 5;
	
	private ContainerController mainContainer;
	
	private ArrayList<ExplorerVis> explorersVis;
	private ArrayList<Explorer> explorersAgent;
	
	@Override
	public String getName() {
		return "Map Exploration Test";
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
			for(int i = 0; i < NUM_AGENTS; i++)
				mainContainer.acceptNewAgent("Explorer_" + i, explorersAgent.get(i)).start();				
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Context build(Context<Object> context) {
		NetworkBuilder<Object> netBuilder = new NetworkBuilder<Object>("Map Exploration Network", context, true);
		netBuilder.buildNetwork();
				
		explorersVis = new ArrayList<ExplorerVis>();
		explorersAgent = new ArrayList<Explorer>();
		
		context.setId("Map Exploration");

		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace("space", context, new RandomCartesianAdder<Object>(), new repast.simphony.space.continuous.WrapAroundBorders(), 50, 50);

		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
		Grid<Object> grid = gridFactory.createGrid("grid", context, 
				new GridBuilderParameters<Object>(
						new WrapAroundBorders(), 
						new SimpleGridAdder<Object>(), 
						true, 50, 50));

		for(int i = 0; i < NUM_AGENTS; i++) {
			Explorer explorerAgent = new Explorer(0, 0);
			explorersAgent.add(explorerAgent);
			
			ExplorerVis explorerVis = new ExplorerVis(space, grid, explorerAgent);
			explorersVis.add(explorerVis);
			context.add(explorerVis);
		}

		for(Object obj : context) {
			NdPoint pt = space.getLocation(obj);
			grid.moveTo(obj, (int)pt.getX(), (int)pt.getY());
		}

		return super.build(context);
	}

}
