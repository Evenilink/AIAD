package algorithms.pledge;

import entities.Obstacle;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.space.grid.Grid;

import java.util.Iterator;
import java.util.List;

public class NeighbourObstacles {
    private Object frontObject;
    private Object backObject;
    private Object leftObject;
    private Object rightObject;

    public NeighbourObstacles(Object frontObject, Object backObject, Object leftObject, Object rightObject) {
        this.frontObject = frontObject;
        this.backObject = backObject;
        this.leftObject = leftObject;
        this.rightObject = rightObject;
    }

    public NeighbourObstacles(Grid grid, NeighbourPoints pts) {
        int maxX = grid.getDimensions().getWidth();
        int maxY = grid.getDimensions().getHeight();

        if (pts.frontX() >= 0 && pts.frontX() < maxX && pts.frontY() >= 0 && pts.frontY() < maxY) {
            GridCellNgh<Object> nghCreator = new GridCellNgh<>(grid, pts.front().toGridPoint(), Object.class,0, 0);
            List<GridCell<Object>> cells = nghCreator.getNeighborhood(true);
            for (GridCell<Object> gridCell : cells) {
                Iterator it = gridCell.items().iterator();
                while (it.hasNext()) {
                    Object obj = it.next();
                    if (obj instanceof Obstacle) {
                        frontObject = obj;
                        break;
                    }
                }
            }
        }

        if (pts.backX() >= 0 && pts.backX() < maxX && pts.backY() >= 0 && pts.backY() < maxY) {
            GridCellNgh<Object> nghCreator = new GridCellNgh<>(grid, pts.back().toGridPoint(), Object.class,0, 0);
            List<GridCell<Object>> cells = nghCreator.getNeighborhood(true);
            for (GridCell<Object> gridCell : cells) {
                Iterator it = gridCell.items().iterator();
                while (it.hasNext()) {
                    Object obj = it.next();
                    if (obj instanceof Obstacle) {
                        backObject = obj;
                        break;
                    }
                }
            }
        }

        if (pts.rightX() >= 0 && pts.rightX() < maxX && pts.rightY() >= 0 && pts.rightY() < maxY)  {
            GridCellNgh<Object> nghCreator = new GridCellNgh<>(grid, pts.left().toGridPoint(), Object.class,0, 0);
            List<GridCell<Object>> cells = nghCreator.getNeighborhood(true);
            for (GridCell<Object> gridCell : cells) {
                Iterator it = gridCell.items().iterator();
                while (it.hasNext()) {
                    Object obj = it.next();
                    if (obj instanceof Obstacle) {
                        leftObject = obj;
                        break;
                    }
                }
            }
        }

        if (pts.leftX() >= 0 && pts.leftX() < maxX && pts.leftY() >= 0 && pts.leftY() < maxY)  {
            GridCellNgh<Object> nghCreator = new GridCellNgh<>(grid, pts.right().toGridPoint(), Object.class,0, 0);
            List<GridCell<Object>> cells = nghCreator.getNeighborhood(true);
            for (GridCell<Object> gridCell : cells) {
                Iterator it = gridCell.items().iterator();
                while (it.hasNext()) {
                    Object obj = it.next();
                    if (obj instanceof Obstacle) {
                        rightObject = obj;
                        break;
                    }
                }
            }
        }
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
