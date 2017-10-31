package visualization;

import java.util.List;

import agents.Coordinates;
import agents.Explorer;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

public class ExplorerVis {

	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private Explorer explorerAgent;
	private int radious;

	private boolean foundExit = false;
	private int[][] matrix;
	
	public ExplorerVis(ContinuousSpace<Object> space, Grid<Object> grid, int gridDimensionsX, int gridDimensionsY, Explorer explorerAgent, int radious) {
		this.space = space;
		this.grid = grid;
		this.explorerAgent = explorerAgent;
		this.radious = radious;

		//System.out.println(this.grid.getDimensions().getWidth() + ", " + grid.getDimensions().getHeight() + ", " + grid.getDimensions().getDepth());
		// Error when accessing grid here!?
		
		matrix = new int[gridDimensionsX][gridDimensionsY];
		for(int i = 0; i < gridDimensionsX; i++) {
			for(int j = 0; j < gridDimensionsY; j++)
				matrix[i][j] = 0;
		}
		
		//this.explorerAgent.setLocation(pt.getX(), pt.getY());
		// Error if function is run here!?
	}
	
	private float calculateAngle(NdPoint source, NdPoint target) {
		float angle = (float) Math.toDegrees(Math.atan2(target.getY() - source.getY(), target.getX() - source.getX()));
		if(angle < 0)
			angle += 360;
		
		angle = (float) (angle * Math.PI / 360);
		
		return angle;
	}
	
	//@ScheduledMethod(start = 1, interval = 1)
	public void step() {
		GridPoint pt = grid.getLocation(this);
		if(matrix[pt.getX()][pt.getY()] == 0)
			matrix[pt.getX()][pt.getY()] = 1;
		
		//space.moveByDisplacement(this, 1, 0);
		//grid.moveTo(this, (int)pt.getX(), (int)pt.getY());
		
		GridCellNgh<ExplorerVis> nghCreator = new GridCellNgh<ExplorerVis>(grid, pt, ExplorerVis.class, radious, radious);
		List<GridCell<ExplorerVis>> gridCells = nghCreator.getNeighborhood(false);
		
		for(GridCell<ExplorerVis> cell : gridCells) {
			System.out.println("x: " + cell.getPoint().getX() + ", y = " + cell.getPoint().getY());
			
		}
		
		System.out.println("\n\n");
		
		/*Coordinates coordinates = explorerAgent.getNextLocation();
		GridPoint pt = grid.getLocation(this);
		NdPoint myPoint = space.getLocation(this);
		NdPoint otherPoint = new NdPoint(coordinates.getX(), coordinates.getY());
		double angle = SpatialMath.calcAngleFor2DMovement(space, myPoint, otherPoint);
		space.moveByVector(this, 1, angle, 0);
		myPoint = space.getLocation(this);
		grid.moveTo(this, (int)myPoint.getX(), (int)myPoint.getY());*/
		
	}
}
