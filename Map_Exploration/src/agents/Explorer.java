package agents;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.INTERNAL;

import algorithms.astar.AStar;
import algorithms.astar.Pathfinding;
import algorithms.dfs.DFS;
import behaviours.AleatoryDFS;
import entities.Exit;
import entities.Obstacle;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.SimUtilities;
import sajas.core.AID;
import sajas.core.Agent;
import sajas.core.behaviours.CyclicBehaviour;
import sajas.domain.DFService;
import utils.Coordinates;
import utils.Utils.ExplorerState;

public class Explorer extends Agent {
	
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private int radious;
	private int communicationLimit;
	
	private AleatoryDFS behaviour;

	public Explorer(ContinuousSpace<Object> space, Grid<Object> grid, int radious, int communicationLimit) {
		this.space = space;
		this.grid = grid;
		this.radious = radious;
		this.communicationLimit = communicationLimit;
		behaviour = new AleatoryDFS(this);
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

		// Sets his initial position in the matrix.
		GridPoint initLocation = grid.getLocation(this);
		behaviour.setMatrixValue(initLocation.getX(), grid.getDimensions().getHeight() - 1 - initLocation.getY(), 1);		
		addBehaviour(behaviour);
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
	
	public Grid<Object> getGrid() {
		return grid;
	}
	
	public int getRadious() {
		return radious;
	}
	
	public ContinuousSpace<Object> getSpace() {
		return space;
	}
}
