package utils;

import entities.Obstacle;
import repast.simphony.context.Context;

public class ObjectSetups {
    public static void Setup1(Context<Object> context) {
        context.add(new Obstacle(6, 6, false));
        context.add(new Obstacle(7, 6, false));
        context.add(new Obstacle(8, 6, false));
        context.add(new Obstacle(9, 6, false));
        context.add(new Obstacle(6, 7, false));
        context.add(new Obstacle(7, 8, false));
        context.add(new Obstacle(7, 9, false));
        context.add(new Obstacle(8, 8, false));
        context.add(new Obstacle(8, 9, false));
        context.add(new Obstacle(9, 8, false));
        context.add(new Obstacle(9, 9, false));
    }

    public static void Setup2(Context<Object> context) {
        context.add(new Obstacle(6, 3, false));
        context.add(new Obstacle(6, 4, false));
        context.add(new Obstacle(6, 5, false));
        context.add(new Obstacle(6, 6, false));
        context.add(new Obstacle(6, 7, false));
        context.add(new Obstacle(6, 8, false));
        context.add(new Obstacle(5, 8, false));
        context.add(new Obstacle(4, 8, false));
        context.add(new Obstacle(3, 8, false));
        context.add(new Obstacle(2, 8, false));
        context.add(new Obstacle(2, 3, false));
        context.add(new Obstacle(2, 4, false));
        context.add(new Obstacle(2, 5, false));
        context.add(new Obstacle(2, 6, false));
        context.add(new Obstacle(2, 7, false));
        context.add(new Obstacle(3, 3, false));
        context.add(new Obstacle(4, 3, false));
        context.add(new Obstacle(5, 3, false));
        context.add(new Obstacle(6, 3, false));
        context.add(new Obstacle(7, 3, false));
    }
}
