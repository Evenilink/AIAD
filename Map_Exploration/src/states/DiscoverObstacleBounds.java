package states;

import agents.Explorer;
import algorithms.pledge.NeighbourObstacles;
import algorithms.pledge.NeighbourPoints;
import algorithms.pledge.Pledge;
import behaviours.Exploration;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import utils.Coordinates;
import utils.Utils;

import java.util.List;

public class DiscoverObstacleBounds implements IAgentTemporaryState {
    Exploration behaviour;
    Pledge pledge;
    GridCell<Object> obstacle;
    boolean pledging;
    Explorer agent;
    Grid grid;

    @Override
    public void enter(Exploration behaviour) {
        this.behaviour = behaviour;
        pledge = this.behaviour.getPledge();
        agent = this.behaviour.getAgent();
        grid = agent.getGrid();
        pledging = false;
    }

    @Override
    public void execute() {
        if (!pledging) {
            NeighbourPoints pts = new NeighbourPoints(grid.getLocation(agent), NeighbourPoints.Direction.UPWARDS);
            NeighbourObstacles objs = new NeighbourObstacles(grid, pts);
            pts.toString();
            objs.toString();
            if (objs.hasObstacle()) {
                pledge.init();
                pledging = true;
            } else {
                GridPoint agentLoc = grid.getLocation(agent);
                GridCellNgh<Object> nghCreator = new GridCellNgh<>(grid, agentLoc, Object.class, agent.getRadious(), agent.getRadious());
                List<GridCell<Object>> neighborhood = nghCreator.getNeighborhood(false);
                GridCell<Object> cell = Utils.getFirstObstacleCell(neighborhood);

                Coordinates obstacleCoords = Coordinates.FromGridPoint(cell.getPoint());
                int newX = agentLoc.getX(), newY = agentLoc.getY();

                if (obstacleCoords.getX() > agentLoc.getX())
                    newX++;
                else if (obstacleCoords.getX() < agentLoc.getX())
                    newX--;

                pts = new NeighbourPoints(new GridPoint(newX, newY), NeighbourPoints.Direction.UPWARDS);
                objs = new NeighbourObstacles(grid, pts);
                if (objs.hasObstacle()) {
                    agent.moveAgent(new Coordinates(newX, newY));
                    pledge.init();
                    pledging = true;
                } else {
                    if (obstacleCoords.getY() > agentLoc.getY())
                        newY++;
                    else if (obstacleCoords.getY() < agentLoc.getY())
                        newY--;
                }
            }
        } else {
            this.pledge.run(true);
        }
    }

    @Override
    public void exit() {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean canResume() {
        if (this.pledging) return this.pledge.hasFinished();
        else return false;
    }

    public void setObstacle(GridCell<Object> obstacle) {
        this.obstacle = obstacle;
    }
}
