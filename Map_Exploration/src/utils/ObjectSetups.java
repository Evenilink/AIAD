package utils;

import java.util.ArrayList;
import java.util.List;

import entities.Obstacle;
import repast.simphony.context.Context;

public class ObjectSetups {
	
    public static void Setup1(Context<Object> context, List<Coordinates> coordinates) {
    	List<Coordinates> tempCoordinates = new ArrayList<>();    	
    	tempCoordinates.add(new Coordinates(6, 6));
    	tempCoordinates.add(new Coordinates(7, 6));
    	tempCoordinates.add(new Coordinates(8, 6));
    	tempCoordinates.add(new Coordinates(9, 6));
    	tempCoordinates.add(new Coordinates(6, 7));
    	tempCoordinates.add(new Coordinates(7, 8));
    	tempCoordinates.add(new Coordinates(7, 9));
    	tempCoordinates.add(new Coordinates(8, 8));
    	tempCoordinates.add(new Coordinates(8, 9));
    	tempCoordinates.add(new Coordinates(9, 8));
    	tempCoordinates.add(new Coordinates(9, 9));
    	
    	for (Coordinates coordinates2 : tempCoordinates) {
    		if(!coordinates.contains(coordinates2)) {
    			System.err.println("The coordinate '" + coordinates2.toString() + "' is already in use.");
    			continue;
    		}
			context.add(new Obstacle(coordinates2.getX(), coordinates2.getY()));
			coordinates.remove(coordinates2);
    	}
    }

    public static void Setup2(Context<Object> context, List<Coordinates> coordinates) {
    	List<Coordinates> tempCoordinates = new ArrayList<>();    	
    	tempCoordinates.add(new Coordinates(6, 4));
    	tempCoordinates.add(new Coordinates(6, 5));
    	tempCoordinates.add(new Coordinates(6, 6));
    	tempCoordinates.add(new Coordinates(6, 7));
    	tempCoordinates.add(new Coordinates(6, 8));
    	tempCoordinates.add(new Coordinates(5, 8));
    	tempCoordinates.add(new Coordinates(4, 8));
    	tempCoordinates.add(new Coordinates(3, 8));
    	tempCoordinates.add(new Coordinates(2, 3));
    	tempCoordinates.add(new Coordinates(2, 4));
    	tempCoordinates.add(new Coordinates(2, 5));
    	tempCoordinates.add(new Coordinates(2, 6));
    	tempCoordinates.add(new Coordinates(2, 7));
    	tempCoordinates.add(new Coordinates(3, 3));
    	tempCoordinates.add(new Coordinates(4, 3));
    	tempCoordinates.add(new Coordinates(5, 3));
    	tempCoordinates.add(new Coordinates(6, 3));
    	tempCoordinates.add(new Coordinates(7, 3));
    	tempCoordinates.add(new Coordinates(2, 8));
    	
    	tempCoordinates.add(new Coordinates(12, 12));
    	tempCoordinates.add(new Coordinates(12, 11));
    	tempCoordinates.add(new Coordinates(11, 11));
    	tempCoordinates.add(new Coordinates(11, 12));
    	
    	for (Coordinates coordinates2 : tempCoordinates) {
    		
    		if(!coordinates.contains(coordinates2)) {
    			System.err.println("The coordinate '" + coordinates2.toString() + "' is already in use.");
    			continue;
    		}
    		if(coordinates2.equals(new Coordinates(2, 4))) 
    			context.add(new Obstacle(coordinates2.getX(), coordinates2.getY(), 3));
    		else context.add(new Obstacle(coordinates2.getX(), coordinates2.getY()));
			coordinates.remove(coordinates2);
    	}
    }
}
