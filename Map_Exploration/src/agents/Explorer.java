package agents;

import behaviours.Exploration;
import behaviours.Messaging;
import behaviours.SharingInfo;
import communication.GroupMessage;
import communication.IndividualMessage;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
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
			
	/**
	 * Super agent constructor.
	 * @param space
	 * @param grid
	 * @param radious
	 */
	public Explorer(ContinuousSpace<Object> space, Grid<Object> grid, int radious) {
		this.space = space;
		this.grid = grid;
		this.radious = radious;
		agentType = AgentType.SUPER_AGENT;
	}
	
	/**
	 * Normal agent constructor.
	 * @param space
	 * @param grid
	 * @param radious
	 * @param communicationLimit
	 */
	public Explorer(ContinuousSpace<Object> space, Grid<Object> grid, int radious, int communicationLimit) {
		this.space = space;
		this.grid = grid;
		this.radious = radious;
		this.communicationLimit = communicationLimit;
		agentType = AgentType.NORMAL_AGENT;
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

		matrix = new Matrix(grid.getDimensions().getHeight(), grid.getDimensions().getWidth(), getName());
		
		// Sets his initial position in the matrix.
		GridPoint initLocation = grid.getLocation(this);
		matrix.setValue(initLocation.getX(), grid.getDimensions().getHeight() - 1 - initLocation.getY(), 1);		
		
		addBehaviour(new Exploration(this));
		addBehaviour(new Messaging(this));
		
		if(agentType == AgentType.SUPER_AGENT) {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Super Explorer");
			template.addServices(sd);
			try {
				DFAgentDescription[] result = DFService.search(this, template);
				System.out.println("result length: " + result.length);
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
	
	/*******************************/
	/*********** Messages **********/
	/*******************************/
	
	public void sendMessage(IndividualMessage message) {
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(message.getReceiver());
		msg.setContent("Ola mano!");
		send(msg);
	}
	
	public void sendMessage(GroupMessage message) {
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		for(int i = 0; i < message.getReceivers().size(); i++)
			msg.addReceiver(message.getReceivers().get(i));
		msg.setContent("Ola mano!");
		send(msg);
	}
	
	public ACLMessage receiveMessage() {
		return receive();
	}
	
	/*******************************/
	/***** Getters and setters *****/
	/*******************************/
	
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
