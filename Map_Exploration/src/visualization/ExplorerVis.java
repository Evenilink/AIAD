package visualization;

import agents.Explorer;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

public class ExplorerVis {

	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private Explorer explorerAgent;
	private boolean foundExit = false;
	
	public ExplorerVis(ContinuousSpace<Object> space, Grid<Object> grid, Explorer explorerAgent) {
		this.space = space;
		this.grid = grid;
		this.explorerAgent = explorerAgent;
	}
	
	@ScheduledMethod(start = 1, interval = 1)
	public void step() {
		GridPoint pt = grid.getLocation(this);
		System.out.println("Position x: " + (int)pt.getX() + ", position y: " + (int)pt.getY());
		
		NdPoint myPoint = space.getLocation(this);
		NdPoint otherPoint = new NdPoint(pt.getX()+1, pt.getY());
		double angle = SpatialMath.calcAngleFor2DMovement(space, myPoint, otherPoint);
		space.moveByVector(this, 1, angle, 0);
		myPoint = space.getLocation(this);
		grid.moveTo(this, (int)myPoint.getX(), (int)myPoint.getY());
	}
}
