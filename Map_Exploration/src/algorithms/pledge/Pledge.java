package algorithms.pledge;

import java.util.ArrayList;
import java.util.List;

import agents.Explorer;
import entities.Obstacle;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import utils.Coordinates;

public class Pledge {
	
    private final Grid grid;
    private final Explorer agent;
    private GridPoint startingPoint;
    private GridPoint previousPoint;
    
    private List<Coordinates> visitedCoordinates;

    /**
     * Creates a new instance of the Pledge algorithm
     * @param agent The agent that's going to run the algorithm
     */
    public Pledge (Explorer agent) {
        this.agent = agent;
        this.grid = agent.getGrid();
        visitedCoordinates = new ArrayList<>();
    }

    /**
     * Returns the next location based of the near obstacles
     * @param pts The points around the explorer
     * @param objs The obstacles around the explorer
     * @param displayMessages Selects whether or not it should display debug messages
     * @return Coordinates the new location the agent should move
     */
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

    /**
     * Gets the location to move when it first enters the Pledge algorithm
     * @param pts The points around the explorer
     * @param objs The obstacles around the explorer
     * @param displayMessages Selects whether or not it should display debug messages
     * @return Coordinates the new location the agent should move
     */
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

    /**
     * Prepares for a new Pledge run. It should be called before every first run in a new obstacle
     */
    public void init() {
        this.startingPoint = this.grid.getLocation(this.agent);
        this.previousPoint = this.startingPoint;
    }

    /**
     * Adds a cell to the visited coordinates, so the agent knows where he already pledged.
     * If the coordinates already exists they won't be inserted
     * @param coordinates The coordinates to add
     * @return boolean If the value could be inserted or not
     */
    public boolean addVisitedCoordinates(Coordinates coordinates) {
        if (alreadyVisited(coordinates)) return false;
        visitedCoordinates.add(coordinates);
        return true;
    }

    /**
     * Whether or not the Pledge has finished going around the obstacle
     * @return boolean if the algorithm has finished
     */
    public boolean hasFinished() {
        return this.alreadyVisited(Coordinates.FromGridPoint(grid.getLocation(agent)));
    }

    /**
     * Runs the algorithm and moves the agent to the next location.
     * Hides debug messages
     */
    public void run () {
        this.run(false);
    }

    /**
     * Runs the algorithm and moves the agent to the next location.
     * @param displayMessages Whether it should display debug messages
     */
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

    /**
     * Verifies if the given coordinates were already visited by the agent while doing the Pledge
     * @param coordinates The coordinates to check
     * @return boolean If the coordinates were already visited
     */
    public boolean alreadyVisited(Coordinates coordinates) {
        for (Coordinates c : visitedCoordinates) {
            if (c.equals(coordinates)) return true;
        }

        NeighbourPoints pts = new NeighbourPoints(coordinates.toGridPoint(), NeighbourPoints.Direction.UPWARDS);
        NeighbourObstacles obj = new NeighbourObstacles(grid, pts);

        return false;
    }

    public boolean alreadyVisited(Coordinates coordinates, GridCell<Object> obstacle) {
        if (alreadyVisited(coordinates)) return true;

        Coordinates obstacleCoords = Coordinates.FromGridPoint(obstacle.getPoint());
        Coordinates explorerCoords = agent.getExploration().getAgentCoordinates();

        int newX = obstacleCoords.getX(), newY = obstacleCoords.getY();

        if (explorerCoords.getX() < newX) {
            newX--;
        } else if (explorerCoords.getX() > newX) {
            newX++;
        }
        if (explorerCoords.getY() < newY) {
            newY--;
        } else if (explorerCoords.getY() > newY) {
            newY++;
        }

        NeighbourPoints pts = new NeighbourPoints(new GridPoint(newX, newY), NeighbourPoints.Direction.UPWARDS);
        if (NeighbourPoints.validPoint(pts.front(), grid) && alreadyVisited(pts.front())) return true;
        if (NeighbourPoints.validPoint(pts.back(), grid) && alreadyVisited(pts.back())) return true;
        if (NeighbourPoints.validPoint(pts.left(), grid) && alreadyVisited(pts.left())) return true;
        if (NeighbourPoints.validPoint(pts.right(), grid) && alreadyVisited(pts.right())) return true;

        return false;
    }
}
