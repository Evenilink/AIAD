package utils.raytrace;

import entities.Obstacle;
import utils.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class TracedPath {
    List<Coordinates> traveledCoordinates;
    Coordinates hitCoordinates;
    Obstacle hitObstacle;

    public TracedPath() {
        traveledCoordinates = new ArrayList<>();
        hitCoordinates = null;
        hitObstacle = null;
    }

    public void addTraveled(Coordinates coordinates) {
        if (!traveledCoordinates.contains(coordinates))
            traveledCoordinates.add(coordinates);
    }

    public List<Coordinates> getTraveled() {
        return traveledCoordinates;
    }

    public void setHit(Coordinates coordinates, Obstacle obstacle) {
        hitCoordinates = coordinates;
        hitObstacle = obstacle;
    }

    public Coordinates getHitCoordinates() {
        return hitCoordinates;
    }

    public Obstacle getHitObstacle() {
        return hitObstacle;
    }
}
