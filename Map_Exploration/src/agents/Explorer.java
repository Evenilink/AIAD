package agents;

import behaviours.Exploration;
import behaviours.Messaging;
import behaviours.SharingInfo;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import sajas.core.Agent;
import sajas.domain.DFService;
import utils.Coordinates;
import utils.Matrix;
import utils.Utils.AgentType;

public class Explorer extends Agent {
	
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private int radious;
	private int communicationLimit;
	private AgentType agentType;
	private Matrix matrix;
			
	public Explorer(ContinuousSpace<Object> space, Grid<Object> grid, int radious) {
		this.space = space;
		this.grid = grid;
		this.radious = radious;
		this.communicationLimit = 20;		//CHANGE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		agentType = AgentType.SUPER_AGENT;
		matrix = new Matrix(grid.getDimensions().getHeight(), grid.getDimensions().getWidth());
	}
	
	public Explorer(ContinuousSpace<Object> space, Grid<Object> grid, int radious, int communicationLimit) {
		this.space = space;
		this.grid = grid;
		this.radious = radious;
		this.communicationLimit = communicationLimit;
		agentType = AgentType.NORMAL_AGENT;
		matrix = new Matrix(grid.getDimensions().getHeight(), grid.getDimensions().getWidth());
	}
	
	@Override
	public void setup() {
		DFAgentDescription dfAgentDescription = new DFAgentDescription();
		dfAgentDescription.setName(getAID());
		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setName(getName());
		if(agentType == AgentType.NORMAL_AGENT)
			serviceDescription.setType("Normal Explorer");
		else serviceDescription.setType("Super Explorer");
		dfAgentDescription.addServices(serviceDescription);
		
		try {
			DFService.register(this, dfAgentDescription);
		} catch(FIPAException e) {
			e.printStackTrace();
		}

		// Sets his initial position in the matrix.
		GridPoint initLocation = grid.getLocation(this);
		Exploration behaviour = new Exploration(this);
		matrix.setValue(initLocation.getX(), grid.getDimensions().getHeight() - 1 - initLocation.getY(), 1);		
		addBehaviour(behaviour);
		addBehaviour(new Messaging(this));
		
		if(agentType == AgentType.SUPER_AGENT) {			
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Super Explorer");
			template.addServices(sd);
			try {
				DFAgentDescription[] result = DFService.search(this, template);
				addBehaviour(new SharingInfo(this, result));
			} catch (FIPAException e) {
				e.printStackTrace();
			}
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
	
	public void moveAgent(Coordinates targetCoordinates) {
		if (space.moveTo(this, targetCoordinates.getX(), targetCoordinates.getY())) {
			grid.moveTo(this, targetCoordinates.getX(), targetCoordinates.getY());
			getMatrix().updateMatrix(getGrid(), targetCoordinates, getRadious());
		}
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
	
	public Matrix getMatrix() {
		return matrix;
	}
}
