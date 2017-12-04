package utils;

import entities.Obstacle;
import repast.simphony.space.grid.Grid;

public class RayTracing {
    /**
     * Casts a ray against the first object it collides.
     * @param grid The grid to get the objects from
     * @param origin The origin to cast the ray
     * @param target The destination coordinates
     * @param onlyOpaque Only checks collision with opaque objects
     * @return Obstacle Returns the first obstacle it hits or null
     */
    public static Obstacle trace(Grid grid, Coordinates origin, Coordinates target, boolean onlyOpaque) {
        int dx = Math.abs(target.getX() - origin.getX());
        int dy = Math.abs(target.getY() - origin.getY());

        int x_inc = (target.getX() > origin.getX()) ? 1 : -1;
        int y_inc = (target.getY() > origin.getY()) ? 1 : -1;
        int error = dx - dy;
        dx = dx * 2;
        dy = dy * 2;

        int x = origin.getX();
        int y = origin.getY();
        for (int n = 1 + dx + dy; n > 0; n--) {
            if (x < 0 || x >= grid.getDimensions().getWidth() || y < 0 || y >= grid.getDimensions().getHeight())
                continue;
            Iterable<Object> objs = grid.getObjectsAt(x, y);
            for (Object obj : objs) {
                if (obj != null && obj instanceof Obstacle) {
                    Obstacle obstacle = (Obstacle) obj;
                    if (!onlyOpaque) return obstacle;
                    else if (!obstacle.isSeeThrough()) return obstacle;
                }
            }

            if (error > 0) {
                x += x_inc;
                error -= dy;
            } else {
                y += y_inc;
                error += dx;
            }
        }
        return null;
    }
}
