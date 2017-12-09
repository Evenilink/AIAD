package algorithms.pledge;

import java.util.ArrayList;
import java.util.List;

import agents.Explorer;
import entities.Obstacle;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import utils.Coordinates;

public class Pledge {
	
    private final Grid grid;
    private final Explorer agent;
    private GridPoint startingPoint;
    private GridPoint previousPoint;
    
    private List<Coordinates> visitedCoordinates;

    public Pledge (Explorer agent) {
        this.agent = agent;
        this.grid = agent.getGrid();
        visitedCoordinates = new ArrayList<>();
    }
    
    private Coordinates getNextLocation(NeighbourPoints pts, NeighbourObstacles objs, boolean displayMessages) {
        if (objs.right() == null || !(objs.right() instanceof Obstacle)) {
            if (displayMessages) System.out.println("PLEDGE: Moving right");
            return pts.right();
        } else if ((objs.front() == null || !(objs.front() instanceof  Obstacle)) && objs.right() instanceof Obstacle) {
            if (displayMessages) System.out.println("PLEDGE: Moving forward");
            return pts.front();
        } else if (objs.front() instanceof Obstacle && objs.right() instanceof Obstacle && objs.left() instanceof Obstacle) {
            if (displayMessages) System.out.println("PLEDGE: Moving back");
            return pts.back();
        } else if (objs.front() instanceof Obstacle && objs.right() instanceof Obstacle) {
            if (displayMessages) System.out.println("PLEDGE: Moving left");
            return pts.left();
        }
        if (displayMessages) System.err.println("PLEDGE: Can't find a cell to move!");
        return null;
    }

    private boolean addVisitedCoordinates(Coordinates coordinates) {
        if (visitedCoordinates.contains(coordinates)) return false;
        visitedCoordinates.add(coordinates);
        return true;
    }

    public void init() {
        this.startingPoint = this.grid.getLocation(this.agent);
        this.previousPoint = this.startingPoint;
        this.addVisitedCoordinates(Coordinates.FromGridPoint(this.startingPoint));
    }

    public boolean hasFinished() {
        return (!this.previousPoint.equals(this.startingPoint)) && this.grid.getLocation(this.agent).equals(this.startingPoint);
    }

    public void run () {
        this.run(false);
    }

    public void run (boolean displayMessages) {
        if (this.hasFinished()) {
            if (displayMessages) System.out.println("PLEDGE: Algorithm already ended, skipping...");
            return;
        }
        if (this.previousPoint == null) {
            this.init();
        }

        GridPoint pt = grid.getLocation(agent); // Current point
        Coordinates nextLocation;

        if (pt.getX() > this.previousPoint.getX()) {
            // Facing Right
            if (displayMessages) System.out.println("PLEDGE: Detecting direction - RIGHT");
            NeighbourPoints pts = new NeighbourPoints(this.grid.getLocation(this.agent), NeighbourPoints.Direction.RIGHT);
            System.out.println(pts.toString());
            NeighbourObstacles objs = new NeighbourObstacles(this.grid, pts);
            System.out.println(objs.toString());
            nextLocation = this.getNextLocation(pts, objs, displayMessages);
        } else if (pt.getX() < this.previousPoint.getX()) {
            // Facing Left
            if (displayMessages) System.out.println("PLEDGE: Detecting direction - LEFT");
            NeighbourPoints pts = new NeighbourPoints(this.grid.getLocation(this.agent), NeighbourPoints.Direction.LEFT);
            System.out.println(pts.toString());
            NeighbourObstacles objs = new NeighbourObstacles(this.grid, pts);
            System.out.println(objs.toString());
            nextLocation = this.getNextLocation(pts, objs, displayMessages);
        } else if (pt.getY() > this.previousPoint.getY()) {
            // Facing Upwards
            if (displayMessages) System.out.println("PLEDGE: Detecting direction - Upwards");
            NeighbourPoints pts = new NeighbourPoints(this.grid.getLocation(this.agent), NeighbourPoints.Direction.UPWARDS);
            System.out.println(pts.toString());
            NeighbourObstacles objs = new NeighbourObstacles(this.grid, pts);
            System.out.println(objs.toString());
            nextLocation = this.getNextLocation(pts, objs, displayMessages);
        } else if (pt.getY() < this.previousPoint.getY()) {
            // Facing Downwards
            if (displayMessages) System.out.println("PLEDGE: Detecting direction - DOWNWARDS");
            NeighbourPoints pts = new NeighbourPoints(this.grid.getLocation(this.agent), NeighbourPoints.Direction.DOWNWARDS);
            System.out.println(pts.toString());
            NeighbourObstacles objs = new NeighbourObstacles(this.grid, pts);
            System.out.println(objs.toString());
            nextLocation = this.getNextLocation(pts, objs, displayMessages);
        } else {
            // First iteration, assuming one direction
            if (displayMessages) System.out.println("PLEDGE: Detecting first iteration");
            NeighbourPoints pts;
            NeighbourObstacles objs;
            int offset = 1;
            do {
                pts = new NeighbourPoints(this.grid.getLocation(agent), NeighbourPoints.Direction.UPWARDS, offset);
                System.out.println(pts.toString());
                objs = new NeighbourObstacles(this.grid, pts);
                System.out.println(objs.toString());
                nextLocation = this.getNextLocation(pts, objs, displayMessages);
                if (nextLocation != null) break;
            } while ( ++offset <= this.agent.getRadious());
        }

        addVisitedCoordinates(Coordinates.FromGridPoint(pt));

        if(nextLocation != null && this.agent.moveAgent(nextLocation))
            this.previousPoint = pt;
        else
            this.agent.moveAgent(Coordinates.FromGridPoint(pt));
    }
    
    public boolean alreadyVisited(Coordinates coordinates) {
        for (Coordinates c : visitedCoordinates) {
            if (c.equals(coordinates)) return true;
        }
    	return false;
    }
}
