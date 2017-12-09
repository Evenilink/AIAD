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

    private Coordinates getFirstLocation(NeighbourPoints pts, NeighbourObstacles objs, boolean displayMessages) {
        if (objs.right() != null && objs.right() instanceof Obstacle) {
            if (displayMessages) System.out.println("PLEDGE: Moving forward");
            if (objs.front() != null && objs.front() instanceof Obstacle) {
                if (displayMessages) System.out.println("PLEDGE: Can't move forward, going left");
                if (objs.left() != null && objs.left() instanceof Obstacle) {
                    if (displayMessages) System.out.println("PLEDGE: Can't move left, going back");
                    return pts.back();
                } else return pts.left();
            } else return pts.front();

        } else if (objs.front() != null && objs.front() instanceof Obstacle) {
            if (displayMessages) System.out.println("PLEDGE: Moving left");
            if (objs.left() != null && objs.left() instanceof Obstacle) {
                if (displayMessages) System.out.println("PLEDGE: Can't move left, going back");
                if (objs.back() != null && objs.back() instanceof Obstacle) {
                    if (displayMessages) System.out.println("PLEDGE: Can't move back, going right");
                    return pts.right();
                } else return pts.back();
            } else return pts.left();

        } else if (objs.left() != null && objs.left() instanceof Obstacle) {
            if (displayMessages) System.out.println("PLEDGE: Moving back");
            if (objs.back() != null && objs.back() instanceof Obstacle) {
                if (displayMessages) System.out.println("PLEDGE: Can't move back, going right");
                if (objs.right() != null && objs.right() instanceof Obstacle) {
                    if (displayMessages) System.out.println("PLEDGE: Can't move right, going forward");
                    return pts.front();
                } else return pts.right();
            } else return pts.back();

        } else if (objs.back() != null && objs.back() instanceof Obstacle) {
            if (displayMessages) System.out.println("PLEDGE: Moving right");
            if (objs.right() != null && objs.right() instanceof Obstacle) {
                if (displayMessages) System.out.println("PLEDGE: Can't move right, going forward");
                if (objs.front() != null && objs.front() instanceof Obstacle) {
                    if (displayMessages) System.out.println("PLEDGE: Can't move forward, going left");
                    return pts.left();
                } else return pts.front();
            } else return pts.right();
        }
        if (displayMessages) System.err.println("PLEDGE: Can't find a cell to move!");
        return null;
    }

    private boolean addVisitedCoordinates(Coordinates coordinates) {
        if (alreadyVisited(coordinates)) return false;
        visitedCoordinates.add(coordinates);
        return true;
    }

    public void init() {
        this.startingPoint = this.grid.getLocation(this.agent);
        this.previousPoint = this.startingPoint;
    }

    public boolean hasFinished() {
        return this.alreadyVisited(Coordinates.FromGridPoint(grid.getLocation(agent)));
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
            NeighbourObstacles objs = new NeighbourObstacles(this.grid, pts);
            nextLocation = this.getNextLocation(pts, objs, displayMessages);
        } else if (pt.getX() < this.previousPoint.getX()) {
            // Facing Left
            if (displayMessages) System.out.println("PLEDGE: Detecting direction - LEFT");
            NeighbourPoints pts = new NeighbourPoints(this.grid.getLocation(this.agent), NeighbourPoints.Direction.LEFT);
            NeighbourObstacles objs = new NeighbourObstacles(this.grid, pts);
            nextLocation = this.getNextLocation(pts, objs, displayMessages);
        } else if (pt.getY() > this.previousPoint.getY()) {
            // Facing Upwards
            if (displayMessages) System.out.println("PLEDGE: Detecting direction - Upwards");
            NeighbourPoints pts = new NeighbourPoints(this.grid.getLocation(this.agent), NeighbourPoints.Direction.UPWARDS);
            NeighbourObstacles objs = new NeighbourObstacles(this.grid, pts);
            nextLocation = this.getNextLocation(pts, objs, displayMessages);
        } else if (pt.getY() < this.previousPoint.getY()) {
            // Facing Downwards
            if (displayMessages) System.out.println("PLEDGE: Detecting direction - DOWNWARDS");
            NeighbourPoints pts = new NeighbourPoints(this.grid.getLocation(this.agent), NeighbourPoints.Direction.DOWNWARDS);
            NeighbourObstacles objs = new NeighbourObstacles(this.grid, pts);
            nextLocation = this.getNextLocation(pts, objs, displayMessages);
        } else {
            // First iteration, assuming one direction
            if (displayMessages) System.out.println("PLEDGE: Detecting first iteration");
            NeighbourPoints pts;
            NeighbourObstacles objs;
            int offset = 1;
            do {
                pts = new NeighbourPoints(this.grid.getLocation(agent), NeighbourPoints.Direction.UPWARDS, offset);
                objs = new NeighbourObstacles(this.grid, pts);
                nextLocation = this.getFirstLocation(pts, objs, displayMessages);
                if (nextLocation != null) break;
            } while ( ++offset <= this.agent.getRadious());
        }

        if(nextLocation != null && agent.canMove(nextLocation))
            addVisitedCoordinates(Coordinates.FromGridPoint(pt));
            previousPoint = pt;
            agent.moveAgent(nextLocation);
    }
    
    public boolean alreadyVisited(Coordinates coordinates) {
        for (Coordinates c : visitedCoordinates) {
            if (c.equals(coordinates)) return true;
        }
    	return false;
    }
}
