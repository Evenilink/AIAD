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

    private GridPoint getNextLocation(NeighbourPoints pts, NeighbourObjects objs, GridPoint defaultPoint) {
        if (objs.right() == null) {
            System.out.println("Pledge: Moving right");
            return pts.right();
        } else if (objs.front() == null && objs.right() instanceof Obstacle) {
            System.out.println("Pledge: Moving forward");
            return pts.front();
        } else if (objs.front() instanceof Obstacle && objs.right() instanceof Obstacle && objs.left() instanceof Obstacle) {
            System.out.println("Pledge: Moving back");
            return pts.back();
        } else if (objs.front() instanceof Obstacle && objs.right() instanceof Obstacle) {
            System.out.println("Pledge: Moving left");
            return pts.left();
        }
        System.err.println("Pledge: Can't find a cell to move!");
        return defaultPoint;
    }

    public Pledge (Grid grid, Explorer explorer) {
        this.grid = grid;
        this.agent = explorer;
    }

    public void start() {
        this.startingPoint = this.grid.getLocation(this.agent);
        this.previousPoint = this.startingPoint;
    }

    public boolean hasFinished() {
        return this.previousPoint != this.startingPoint && this.grid.getLocation(this.agent) == this.startingPoint;
    }

    public GridPoint nextLocation () {
        if (this.hasFinished()) return null;

        GridPoint pt = grid.getLocation(agent); // Current point
        GridPoint nextLocation;
        NeighbourPoints pts;

        if (pt.getX() > this.previousPoint.getX()) {
            // Facing Right
            System.out.println("Pledge: Detecting direction - RIGHT");
            pts = new NeighbourPoints(this.grid.getLocation(this.agent), NeighbourPoints.Direction.RIGHT);
            NeighbourObjects objs = new NeighbourObjects(this.grid, pts);
            nextLocation = this.getNextLocation(pts, objs, pt);
        } else if (pt.getX() < this.previousPoint.getX()) {
            // Facing Left
            System.out.println("Pledge: Detecting direction - LEFT");
            pts = new NeighbourPoints(this.grid.getLocation(this.agent), NeighbourPoints.Direction.LEFT);
            NeighbourObjects objs = new NeighbourObjects(this.grid, pts);
            nextLocation = this.getNextLocation(pts, objs, pt);
        } else if (pt.getY() > this.previousPoint.getY()) {
            // Facing Upwards
            System.out.println("Pledge: Detecting direction - Upwards");
            pts = new NeighbourPoints(this.grid.getLocation(this.agent), NeighbourPoints.Direction.UPWARDS);
            NeighbourObjects objs = new NeighbourObjects(this.grid, pts);
            nextLocation = this.getNextLocation(pts, objs, pt);
        } else if (pt.getY() < this.previousPoint.getY()) {
            // Facing Downwards
            System.out.println("Pledge: Detecting direction - DOWNWARDS");
            pts = new NeighbourPoints(this.grid.getLocation(this.agent), NeighbourPoints.Direction.DOWNWARDS);
            NeighbourObjects objs = new NeighbourObjects(this.grid, pts);
            nextLocation = this.getNextLocation(pts, objs, pt);
        } else {
            // First iteration assuming facing up
            System.out.println("Pledge: Detecting first iteration");
            int offset = 1;
            do {
                pts = new NeighbourPoints(this.grid.getLocation(agent), NeighbourPoints.Direction.UPWARDS, offset);
                NeighbourObjects objs = new NeighbourObjects(this.grid, pts);
                nextLocation = this.getNextLocation(pts, objs, pt);
                if (nextLocation != pt) break;
            } while ( ++offset <= this.agent.getRadious());
        }

        this.previousPoint = pt;
        return nextLocation;
    }
}
