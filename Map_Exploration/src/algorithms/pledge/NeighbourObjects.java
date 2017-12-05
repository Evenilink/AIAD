package algorithms.pledge;

import repast.simphony.space.grid.Grid;

public class NeighbourObjects {
    private Object frontObject;
    private Object backObject;
    private Object leftObject;
    private Object rightObject;

    public NeighbourObjects(Object frontObject, Object backObject, Object leftObject, Object rightObject) {
        this.frontObject = frontObject;
        this.backObject = backObject;
        this.leftObject = leftObject;
        this.rightObject = rightObject;
    }

    public NeighbourObjects(Grid grid, NeighbourPoints pts) {
        int maxX = grid.getDimensions().getWidth();
        int maxY = grid.getDimensions().getHeight();
        if (pts.frontX() >= 0 && pts.frontX() < maxX && pts.frontY() >= 0 && pts.frontY() < maxY)
            this.frontObject = grid.getObjectAt(pts.frontX(), pts.frontY());
        if (pts.backX() >= 0 && pts.backX() < maxX && pts.backY() >= 0 && pts.backY() < maxY)
            this.backObject = grid.getObjectAt(pts.backX(), pts.backY());
        if (pts.rightX() >= 0 && pts.rightX() < maxX && pts.rightY() >= 0 && pts.rightY() < maxY)
            this.rightObject = grid.getObjectAt(pts.rightX(), pts.rightY());
        if (pts.leftX() >= 0 && pts.leftX() < maxX && pts.leftY() >= 0 && pts.leftY() < maxY)
            this.leftObject = grid.getObjectAt(pts.leftX(), pts.leftY());
    }

    public Object front() {
        return frontObject;
    }

    public void front(Object frontObject) {
        this.frontObject = frontObject;
    }

    public Object back() {
        return backObject;
    }

    public void back(Object backObject) {
        this.backObject = backObject;
    }

    public Object left() {
        return leftObject;
    }

    public void left(Object leftObject) {
        this.leftObject = leftObject;
    }

    public Object right() {
        return rightObject;
    }

    public void right(Object rightObject) {
        this.rightObject = rightObject;
    }

    public String toString() {
        return  "Front Object: " + frontObject + "\n" +
                "Back Object: " + backObject + "\n" +
                "Left Object: " + leftObject + "\n" +
                "Right Object: " + rightObject;
    }
}
