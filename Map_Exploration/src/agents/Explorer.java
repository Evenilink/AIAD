package agents;

import java.util.Vector;

import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import sajas.core.Agent;
import sajas.core.behaviours.CyclicBehaviour;
import sajas.domain.DFService;

public class Explorer extends Agent {
	
	private boolean foundExit = false;
	public Coordinates coordinates;
	private int[][] matrix;

	
	public Explorer(int posX, int posY, int gridMaxX, int gridMaxY) {
		coordinates = new Coordinates(posX, posY);
		System.out.println("Beging");
		System.out.println("End");
	}
	
	public void setLocation(int posX, int posY) {
		System.out.println("Agent location -> x: " + posX + ", y: " + posY);
		coordinates.updateCoordinates(posX, posY);
		matrix[coordinates.getX()][coordinates.getY()] = 1;
	}
	
	public Coordinates getNextLocation() {
		if(matrix[coordinates.getX()+1][coordinates.getY()] == 0)
			return new Coordinates(coordinates.getX()+1, coordinates.getY());
		return new Coordinates(coordinates.getX(), coordinates.getY()+1);			
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
		
		addBehaviour(new A());
	}
	
	class A extends CyclicBehaviour {
		@Override
		public void action() {
			// TODO Auto-generated method stub
			System.out.println(myAgent.getName());
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
