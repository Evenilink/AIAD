package utils;

import entities.Obstacle;
import repast.simphony.context.Context;

public class ObjectSetups {
    public static void Setup1(Context<Object> context) {
        context.add(new Obstacle(6, 6));
        context.add(new Obstacle(7, 6));
        context.add(new Obstacle(8, 6));
        context.add(new Obstacle(9, 6));
        context.add(new Obstacle(6, 7));
        context.add(new Obstacle(7, 8));
        context.add(new Obstacle(7, 9));
        context.add(new Obstacle(8, 8));
        context.add(new Obstacle(8, 9));
        context.add(new Obstacle(9, 8));
        context.add(new Obstacle(9, 9));
    }

    public static void Setup2(Context<Object> context) {
        //context.add(new Obstacle(4, 3,false,2));
        context.add(new Obstacle(6, 4));
        context.add(new Obstacle(6, 5));
        context.add(new Obstacle(6, 6));
        context.add(new Obstacle(6, 7));
        context.add(new Obstacle(6, 8));
        context.add(new Obstacle(5, 8));
        context.add(new Obstacle(4, 8));
        context.add(new Obstacle(3, 8));
        context.add(new Obstacle(2, 8));
        context.add(new Obstacle(2, 3));
        context.add(new Obstacle(2, 4));
        context.add(new Obstacle(2, 5,false,2));
        context.add(new Obstacle(2, 6));
        context.add(new Obstacle(2, 7));
        context.add(new Obstacle(3, 3));
        context.add(new Obstacle(4, 3));
        context.add(new Obstacle(5, 3));
        context.add(new Obstacle(6, 3));
        context.add(new Obstacle(7, 3));
        
        //ADDED FOR TESTING
        
        
        
        context.add(new Obstacle(12, 11));
        context.add(new Obstacle(11, 11));
        context.add(new Obstacle(11, 12));
        
    }
}
