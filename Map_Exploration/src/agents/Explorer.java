package agents;

import java.util.List;

import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import sajas.core.Agent;
import sajas.core.behaviours.CyclicBehaviour;
import sajas.domain.DFService;

public class Explorer extends Agent {
	
	private boolean foundExit = false;
	public Coordinates coordinates;
	private int[][] matrix;
	
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private int radious;

	
	public Explorer(ContinuousSpace<Object> space, Grid<Object> grid, int radious, int gridDimensionsX, int gridDimensionsY) {
		this.space = space;
		this.grid = grid;
		this.radious = radious;
		//coordinates = new Coordinates(0, 0);
		
		/*matrix = new int[gridDimensionsX][gridDimensionsY];
		for(int i = 0; i < gridDimensionsX; i++) {
			for(int j = 0; j < gridDimensionsY; j++)
				matrix[i][j] = 0;
		}*/
	}
	
	@Override
	public void setup() {
		DFAgentDescription dfAgentDescription = new DFAgentDescription();
		dfAgentDescription.setName(getAID());
		
		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setName(getName());
		serviceDescription.setType("Explorer");
		
		dfAgentDescription.addServices(serviceDescription);
		
		try {
			DFService.register(this, dfAgentDescription);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
		
		addBehaviour(new UpdateVisualization(this));
	}
	
	class UpdateVisualization extends CyclicBehaviour {
		
		private Agent agent;
		
		public UpdateVisualization(Agent agent) {
			super(agent);
			this.agent = agent;
		}
		
		@Override
		public void action() {
			//System.out.println(myAgent.getName());
			
			GridPoint pt = grid.getLocation(agent);
			/*if(matrix[pt.getX()][pt.getY()] == 0)
				matrix[pt.getX()][pt.getY()] = 1;*/
			
			space.moveByDisplacement(agent, 1, 0);
			grid.moveTo(agent, (int)pt.getX()+1, (int)pt.getY());
			
			System.out.println("x: " + pt.getX() + ", y: " + pt.getY());
		}
	}
	
	@Override
	public void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}
}
