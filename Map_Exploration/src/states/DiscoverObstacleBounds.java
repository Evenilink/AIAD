package states;

import agents.Explorer;
import algorithms.pledge.NeighbourObstacles;
import algorithms.pledge.NeighbourPoints;
import algorithms.pledge.Pledge;
import behaviours.Exploration;
import entities.Obstacle;
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
    boolean pledging;
    Explorer agent;
    Grid grid;
    Coordinates initialCoordinates;
    boolean pledgeFinished;
    GridCell<Object> obstacle;
    Coordinates previousCoordinates;

    @Override
    public void enter(Exploration behaviour) {
        this.behaviour = behaviour;
        pledge = behaviour.getPledge();
        agent = behaviour.getAgent();
        grid = agent.getGrid();
        pledging = false;
        pledgeFinished = false;
        initialCoordinates = behaviour.getAgentCoordinates();
    }

    @Override
    public void execute() {
        if (!pledging) {
            NeighbourPoints pts = new NeighbourPoints(grid.getLocation(agent), NeighbourPoints.Direction.UPWARDS);
            NeighbourObstacles objs = new NeighbourObstacles(grid, pts);
            if (objs.hasObstacle()) {
                pledge.init();
                pledging = true;
            } else {
                GridPoint agentLoc = grid.getLocation(agent);
                GridCellNgh<Object> nghCreator = new GridCellNgh<>(grid, agentLoc, Object.class, agent.getRadious(), agent.getRadious());

                Coordinates obstacleCoords = Coordinates.FromGridPoint(obstacle.getPoint());
                int newX = agentLoc.getX(), newY = agentLoc.getY();

                if (obstacleCoords.getX() > agentLoc.getX())
                    newX++;
                else if (obstacleCoords.getX() < agentLoc.getX())
                    newX--;

                pts = new NeighbourPoints(new GridPoint(newX, newY), NeighbourPoints.Direction.UPWARDS);
                objs = new NeighbourObstacles(grid, pts);
                if (objs.hasObstacle()) {
                    agent.moveAgent(new Coordinates(newX, newY));
                } else {
                    if (obstacleCoords.getY() > agentLoc.getY())
                        newY++;
                    else if (obstacleCoords.getY() < agentLoc.getY())
                        newY--;
                    agent.moveAgent(new Coordinates(newX, newY));
                }
            }
        } else {
            if (pledge.hasFinished())
                pledgeFinished = true;
            else if (previousCoordinates != null && previousCoordinates.equals(behaviour.getAgentCoordinates()))
                pledgeFinished = true;

            if (pledgeFinished) {
                // Moves to initial coordinates
                Coordinates agentLoc = behaviour.getAgentCoordinates();
                int newX = agentLoc.getX(), newY = agentLoc.getY();
                if (initialCoordinates.getX() > newX)
                    newX++;
                else if (initialCoordinates.getX() < newX)
                    newX--;
                if (initialCoordinates.getY() > newY)
                    newY++;
                else if (initialCoordinates.getY() < newY)
                    newY--;

                if (agent.canMove(new Coordinates(newX, newY)))
                    agent.moveAgent(new Coordinates(newX, newY));
                else if (agent.canMove(new Coordinates(newX, agentLoc.getY())))
                    agent.moveAgent(new Coordinates(newX, agentLoc.getY()));
                else if (agent.canMove(new Coordinates(agentLoc.getX(), newY)))
                    agent.canMove(new Coordinates(agentLoc.getX(), newY));
                else initialCoordinates = behaviour.getAgentCoordinates();
            } else this.pledge.run();
        }
    }

    @Override
    public void exit() {
        // TODO Auto-generated method stub
    }

    public void setObstacle(GridCell<Object> obstacle) {
        this.obstacle = obstacle;
    }

    @Override
    public boolean canResume() {
        if (this.pledging) return pledgeFinished && behaviour.getAgentCoordinates().equals(initialCoordinates);
        else return false;
    }
}
