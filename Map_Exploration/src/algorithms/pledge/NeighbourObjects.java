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
        this.frontObject = grid.getObjectsAt(pts.frontX(), pts.frontY());
        this.backObject = grid.getObjectsAt(pts.backX(), pts.backY());
        this.rightObject = grid.getObjectsAt(pts.rightX(), pts.rightY());
        this.leftObject = grid.getObjectsAt(pts.leftX(), pts.leftY());
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
}
