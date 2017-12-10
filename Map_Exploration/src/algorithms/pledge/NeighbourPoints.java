package algorithms.pledge;

import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import utils.Coordinates;

public class NeighbourPoints {
    public enum Direction {
        UPWARDS,
        DOWNWARDS,
        LEFT,
        RIGHT
    }

    private GridPoint frontPoint;
    private GridPoint backPoint;
    private GridPoint leftPoint;
    private GridPoint rightPoint;
    private int offset;

    public static boolean validPoint(Coordinates point, Grid<Object> grid) {
        int x = grid.getDimensions().getWidth(), y = grid.getDimensions().getHeight();

        if (point.getX() < 0 || point.getX() > x) return false;
        if (point.getY() < 0 || point.getY() > y) return false;

        return true;
    }

    private void NeighbourPoints(GridPoint centralPoint, Direction direction, int offset) {
        this.offset = offset;
        if (direction == Direction.RIGHT) {
            // Facing Right
            frontPoint = new GridPoint(centralPoint.getX() + offset, centralPoint.getY());
            backPoint = new GridPoint(centralPoint.getX() - offset, centralPoint.getY());
            leftPoint = new GridPoint(centralPoint.getX(), centralPoint.getY() + offset);
            rightPoint = new GridPoint(centralPoint.getX(), centralPoint.getY() - offset);
        } else if (direction == Direction.LEFT) {
            // Facing Left
            frontPoint = new GridPoint(centralPoint.getX() - offset, centralPoint.getY());
            backPoint = new GridPoint(centralPoint.getX() + offset, centralPoint.getY());
            leftPoint = new GridPoint(centralPoint.getX(), centralPoint.getY() - offset);
            rightPoint = new GridPoint(centralPoint.getX(), centralPoint.getY() + offset);
        } else if (direction == Direction.UPWARDS) {
            // Facing Upwards
            frontPoint = new GridPoint(centralPoint.getX(), centralPoint.getY() + offset);
            backPoint = new GridPoint(centralPoint.getX(), centralPoint.getY() - offset);
            leftPoint = new GridPoint(centralPoint.getX() - offset, centralPoint.getY());
            rightPoint = new GridPoint(centralPoint.getX() + offset, centralPoint.getY());
        } else if (direction == Direction.DOWNWARDS) {
            // Facing Downwards
            frontPoint = new GridPoint(centralPoint.getX(), centralPoint.getY() - offset);
            backPoint = new GridPoint(centralPoint.getX(), centralPoint.getY() + offset);
            leftPoint = new GridPoint(centralPoint.getX() + offset, centralPoint.getY());
            rightPoint = new GridPoint(centralPoint.getX() - offset, centralPoint.getY());
        }
    }

    public NeighbourPoints(GridPoint frontPoint, GridPoint backPoint, GridPoint leftPoint, GridPoint rightPoint) {
        this.frontPoint = frontPoint;
        this.backPoint = backPoint;
        this.leftPoint = leftPoint;
        this.rightPoint = rightPoint;
    }

    public NeighbourPoints(GridPoint centralPoint, Direction direction, int offset) {
        this.NeighbourPoints(centralPoint, direction, offset);
    }

    public NeighbourPoints(GridPoint centralPoint, Direction direction) {
        this.NeighbourPoints(centralPoint, direction, 1);
    }


    public Coordinates front() {
        return Coordinates.FromGridPoint(frontPoint);
    }

    public void front(GridPoint frontPoint) {
        this.frontPoint = frontPoint;
    }

    public Coordinates back() {
        return Coordinates.FromGridPoint(backPoint);
    }

    public void back(GridPoint backPoint) {
        this.backPoint = backPoint;
    }

    public Coordinates left() {
        return Coordinates.FromGridPoint(leftPoint);
    }

    public void left(GridPoint leftPoint) {
        this.leftPoint = leftPoint;
    }

    public Coordinates right() {
        return Coordinates.FromGridPoint(rightPoint);
    }

    public void right(GridPoint rightPoint) {
        this.rightPoint = rightPoint;
    }

    public int frontX() {
        return frontPoint.getX();
    }

    public int frontY() {
        return frontPoint.getY();
    }

    public int backX() {
        return backPoint.getX();
    }

    public int backY() {
        return backPoint.getY();
    }

    public int leftX() {
        return leftPoint.getX();
    }

    public int leftY() {
        return leftPoint.getY();
    }

    public int rightX() {
        return rightPoint.getX();
    }

    public int rightY() {
        return rightPoint.getY();
    }

    public boolean vaildPoints(Grid<Object> grid) {
        int x = grid.getDimensions().getWidth(), y = grid.getDimensions().getHeight();

        if (frontX() < 0 || frontX() > x) return false;
        if (frontY() < 0 || frontY() > y) return false;

        if (backX() < 0 || backX() > x) return false;
        if (backY() < 0 || backY() > y) return false;

        if (leftX() < 0 || leftX() > x) return false;
        if (leftY() < 0 || leftY() > y) return false;

        if (rightX() < 0 || rightY() > x) return false;
        if (rightX() < 0 || rightY() > y) return false;
        return true;
    }

    public String toString() {
        return  "Offset: " + this.offset + "\n" +
                "Front Point: " + frontPoint + "\n" +
                "Back Point: " + backPoint + "\n" +
                "Left Point: " + leftPoint + "\n" +
                "Right Point: " + rightPoint;
    }
}
