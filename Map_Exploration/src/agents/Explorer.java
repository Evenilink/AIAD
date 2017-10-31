package agents;

import java.util.List;

import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.SimUtilities;
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
		
		matrix = new int[gridDimensionsX][gridDimensionsY];
		for(int i = 0; i < gridDimensionsX; i++) {
			for(int j = 0; j < gridDimensionsY; j++)
				matrix[i][j] = 0;
		}
		printMatrix();
	}
	
	@Override
	public void setup() {
		coordinates = new Coordinates(grid.getLocation(this).getX(), grid.getLocation(this).getY());

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
	
	private void printMatrix() {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[i].length; j++)
				System.out.print(matrix[i][j] + " | ");
			System.out.println();
		}
	}
	
	class UpdateVisualization extends CyclicBehaviour {
		
		private Agent agent;
		
		public UpdateVisualization(Agent agent) {
			super(agent);
			this.agent = agent;
		}
		
		@Override
		public void action() {		
			GridPoint pt = grid.getLocation(agent);

			GridCellNgh<Object> nghCreator = new GridCellNgh<Object>(grid, pt, Object.class, radious, radious);
			List<GridCell<Object>> gridCells = nghCreator.getNeighborhood(false);
			SimUtilities.shuffle(gridCells, RandomHelper.getUniform());
			
			GridCell<Object> cell = gridCells.get(0);
			GridPoint targetPoint = cell.getPoint();
			
			NdPoint origin = space.getLocation(agent);
			NdPoint target = new NdPoint(targetPoint.getX(), targetPoint.getY());
			double angle = SpatialMath.calcAngleFor2DMovement(space, origin, target);
			space.moveByVector(agent, 1, angle, 0);
			origin = space.getLocation(agent);
			grid.moveTo(agent, (int)origin.getX(), (int)origin.getY());
			
			matrix[(int) origin.getX()][(int) origin.getY()] = 1;
			
			printMatrix();
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
