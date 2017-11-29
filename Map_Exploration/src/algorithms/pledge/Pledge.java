package algorithms.pledge;

import agents.Explorer;
import entities.Obstacle;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

public class Pledge {
    private final Grid grid;
    private final Explorer agent;
    private GridPoint startingPoint;
    private GridPoint previousPoint;

    private GridPoint getNextLocation(NeighbourPoints pts, NeighbourObjects objs, boolean displayMessages) {
        if (objs.right() == null) {
            if (displayMessages) System.out.println("PLEDGE: Moving right");
            return pts.right();
        } else if (objs.front() == null && objs.right() instanceof Obstacle) {
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

    public Pledge (Explorer agent) {
        this.agent = agent;
        this.grid = agent.getGrid();
    }

    public void start() {
        this.startingPoint = this.grid.getLocation(this.agent);
        this.previousPoint = this.startingPoint;
    }

    public boolean hasFinished() {
        return this.previousPoint != this.startingPoint && this.grid.getLocation(this.agent) == this.startingPoint;
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
            this.start();
        }

        GridPoint pt = grid.getLocation(agent); // Current point
        GridPoint nextLocation;

        if (pt.getX() > this.previousPoint.getX()) {
            // Facing Right
            if (displayMessages) System.out.println("PLEDGE: Detecting direction - RIGHT");
            NeighbourPoints pts = new NeighbourPoints(this.grid.getLocation(this.agent), NeighbourPoints.Direction.RIGHT);
            NeighbourObjects objs = new NeighbourObjects(this.grid, pts);
            nextLocation = this.getNextLocation(pts, objs, displayMessages);
        } else if (pt.getX() < this.previousPoint.getX()) {
            // Facing Left
            if (displayMessages) System.out.println("PLEDGE: Detecting direction - LEFT");
            NeighbourPoints pts = new NeighbourPoints(this.grid.getLocation(this.agent), NeighbourPoints.Direction.LEFT);
            NeighbourObjects objs = new NeighbourObjects(this.grid, pts);
            nextLocation = this.getNextLocation(pts, objs, displayMessages);
        } else if (pt.getY() > this.previousPoint.getY()) {
            // Facing Upwards
            if (displayMessages) System.out.println("PLEDGE: Detecting direction - Upwards");
            NeighbourPoints pts = new NeighbourPoints(this.grid.getLocation(this.agent), NeighbourPoints.Direction.UPWARDS);
            NeighbourObjects objs = new NeighbourObjects(this.grid, pts);
            nextLocation = this.getNextLocation(pts, objs, displayMessages);
        } else if (pt.getY() < this.previousPoint.getY()) {
            // Facing Downwards
            if (displayMessages) System.out.println("PLEDGE: Detecting direction - DOWNWARDS");
            NeighbourPoints pts = new NeighbourPoints(this.grid.getLocation(this.agent), NeighbourPoints.Direction.DOWNWARDS);
            NeighbourObjects objs = new NeighbourObjects(this.grid, pts);
            nextLocation = this.getNextLocation(pts, objs, displayMessages);
        } else {
            // First iteration, assuming one direction
            if (displayMessages) System.out.println("PLEDGE: Detecting first iteration");
            NeighbourPoints pts;
            NeighbourObjects objs;
            int offset = 1;
            do {
                pts = new NeighbourPoints(this.grid.getLocation(agent), NeighbourPoints.Direction.RIGHT, offset);
                objs = new NeighbourObjects(this.grid, pts);
                nextLocation = this.getNextLocation(pts, objs, displayMessages);
                if (nextLocation != null) break;
            } while ( ++offset <= this.agent.getRadious());
        }

        this.previousPoint = pt;
        this.agent.moveAgent(nextLocation != null ? nextLocation : pt);
    }
}
