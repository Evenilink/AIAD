package agents;

import behaviours.Exploration;
import behaviours.Messaging;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import sajas.core.Agent;
import sajas.domain.DFService;
import utils.Coordinates;

public class Explorer extends Agent {
	
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private int radious;
	private int communicationLimit;
	
	private int[][] matrix;
	private int discoveredCells;
	
	public Explorer(ContinuousSpace<Object> space, Grid<Object> grid, int radious, int communicationLimit) {
		this.space = space;
		this.grid = grid;
		this.radious = radious;
		this.communicationLimit = communicationLimit;
		
		matrix = new int[grid.getDimensions().getHeight()][grid.getDimensions().getWidth()];
		for(int row = 0; row < grid.getDimensions().getHeight(); row++) {
			for(int column = 0; column < grid.getDimensions().getWidth(); column++)
				matrix[row][column] = 0;
		}
	}
	
	@Override
	public void setup() {
		DFAgentDescription dfAgentDescription = new DFAgentDescription();
		dfAgentDescription.setName(getAID());
		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setName(getName());
		serviceDescription.setType("Explorer basic");
		dfAgentDescription.addServices(serviceDescription);
		
		try {
			DFService.register(this, dfAgentDescription);
		} catch(FIPAException e) {
			e.printStackTrace();
		}

		// Sets his initial position in the matrix.
		GridPoint initLocation = grid.getLocation(this);
		Exploration behaviour = new Exploration(this);
		setMatrixValue(initLocation.getX(), grid.getDimensions().getHeight() - 1 - initLocation.getY(), 1);		
		addBehaviour(behaviour);
		addBehaviour(new Messaging());
	}
	
	@Override
	public void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}
	
	public void moveAgent(GridPoint targetPoint) {
		if (space.moveTo(this, targetPoint.getX(), targetPoint.getY()))
			grid.moveTo(this, targetPoint.getX(), targetPoint.getY());
	}
	
	public void moveAgent(Coordinates targetCoordinates) {
		if (space.moveTo(this, targetCoordinates.getX(), targetCoordinates.getY()))
			grid.moveTo(this, targetCoordinates.getX(), targetCoordinates.getY());
	}
	
	public boolean mapFullyExplored() {
		return ((matrix.length * matrix[0].length) == discoveredCells);
	}
	
	public Grid<Object> getGrid() {
		return grid;
	}
	
	public int getRadious() {
		return radious;
	}
	
	public ContinuousSpace<Object> getSpace() {
		return space;
	}
	
	public int[][] getMatrix() {
		return matrix;
	}
	
	public int getMatrixValue(int column, int row) {
		return matrix[row][column];
	}
	
	public void setMatrixValue(int column, int row, int value) {
		matrix[row][column] = value;
		if(value != 0)
			discoveredCells++;
	}
}
