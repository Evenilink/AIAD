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
    
    public static void Simple(Context<Object> context, List<Coordinates> coordinates) {
    	List<Coordinates> tempCoordinates = new ArrayList<>();    	
    	tempCoordinates.add(new Coordinates(4, 4));
    	tempCoordinates.add(new Coordinates(4, 5));
    	tempCoordinates.add(new Coordinates(4, 6));
    	tempCoordinates.add(new Coordinates(4, 7));
    	tempCoordinates.add(new Coordinates(4, 8));
    	tempCoordinates.add(new Coordinates(4, 9));
    	tempCoordinates.add(new Coordinates(4, 10));
    	tempCoordinates.add(new Coordinates(4, 11));
    	tempCoordinates.add(new Coordinates(4, 12));
    	tempCoordinates.add(new Coordinates(4, 13));
    	
    	tempCoordinates.add(new Coordinates(21, 4));
    	tempCoordinates.add(new Coordinates(21, 5));
    	tempCoordinates.add(new Coordinates(21, 6));
    	tempCoordinates.add(new Coordinates(21, 7));
    	tempCoordinates.add(new Coordinates(21, 8));
    	tempCoordinates.add(new Coordinates(21, 9));
    	tempCoordinates.add(new Coordinates(21, 10));
    	tempCoordinates.add(new Coordinates(21, 11));
    	tempCoordinates.add(new Coordinates(21, 12));
    	tempCoordinates.add(new Coordinates(21, 13));
    	tempCoordinates.add(new Coordinates(21, 14));
    	tempCoordinates.add(new Coordinates(21, 15));
    	tempCoordinates.add(new Coordinates(21, 16));
    	tempCoordinates.add(new Coordinates(21, 17));
    	tempCoordinates.add(new Coordinates(21, 18));
    	tempCoordinates.add(new Coordinates(21, 19));
    	tempCoordinates.add(new Coordinates(21, 20));
    	tempCoordinates.add(new Coordinates(21, 21));
    	tempCoordinates.add(new Coordinates(21, 22));
    	tempCoordinates.add(new Coordinates(21, 23));
    	
    	tempCoordinates.add(new Coordinates(4, 21));
    	tempCoordinates.add(new Coordinates(5, 21));
    	tempCoordinates.add(new Coordinates(6, 21));
    	tempCoordinates.add(new Coordinates(7, 21));
    	tempCoordinates.add(new Coordinates(8, 21));
    	tempCoordinates.add(new Coordinates(9, 21));
    	tempCoordinates.add(new Coordinates(10, 21));
    	tempCoordinates.add(new Coordinates(11, 21));
    	tempCoordinates.add(new Coordinates(12, 21));
    	tempCoordinates.add(new Coordinates(13, 21));
    	tempCoordinates.add(new Coordinates(14, 21));
    	tempCoordinates.add(new Coordinates(15, 21));
    	tempCoordinates.add(new Coordinates(16, 21));
    	tempCoordinates.add(new Coordinates(17, 21));
    	tempCoordinates.add(new Coordinates(18, 21));
    	tempCoordinates.add(new Coordinates(19, 21));
    	tempCoordinates.add(new Coordinates(20, 21));

    	for (Coordinates coordinates2 : tempCoordinates) {
    		if(!coordinates.contains(coordinates2)) {
    			System.err.println("The coordinate '" + coordinates2.toString() + "' is already in use.");
    			continue;
    		}
			context.add(new Obstacle(coordinates2.getX(), coordinates2.getY()));
			coordinates.remove(coordinates2);
    	}
    }
    
    public static void Maze1515(Context<Object> context, List<Coordinates> coordinates) {
    	List<Coordinates> tempCoordinates = new ArrayList<>();
    	
    	tempCoordinates.add(new Coordinates(2, 6));
    	tempCoordinates.add(new Coordinates(2, 7));
    	tempCoordinates.add(new Coordinates(3, 7));
    	
    	tempCoordinates.add(new Coordinates(2, 3));
    	tempCoordinates.add(new Coordinates(2, 2));
    	tempCoordinates.add(new Coordinates(2, 1));
    	
    	tempCoordinates.add(new Coordinates(7, 2));
    	tempCoordinates.add(new Coordinates(8, 2));
    	tempCoordinates.add(new Coordinates(9, 2));
    	tempCoordinates.add(new Coordinates(9, 3));
    	tempCoordinates.add(new Coordinates(9, 4));
    	tempCoordinates.add(new Coordinates(8, 4));
    	tempCoordinates.add(new Coordinates(7, 3));
    	tempCoordinates.add(new Coordinates(7, 4));
    	
    	tempCoordinates.add(new Coordinates(12, 3));
    	tempCoordinates.add(new Coordinates(12, 4));
    	
    	tempCoordinates.add(new Coordinates(11, 7));
    	tempCoordinates.add(new Coordinates(11, 8));
    	tempCoordinates.add(new Coordinates(12, 8));
    	
    	tempCoordinates.add(new Coordinates(3, 10));
    	tempCoordinates.add(new Coordinates(3, 11));
    	tempCoordinates.add(new Coordinates(3, 12));
    	tempCoordinates.add(new Coordinates(3, 13));
    	
    	tempCoordinates.add(new Coordinates(9, 11));
    	tempCoordinates.add(new Coordinates(9, 12));
    	tempCoordinates.add(new Coordinates(10, 11));
    	tempCoordinates.add(new Coordinates(10, 12));
    	tempCoordinates.add(new Coordinates(11, 11));
    	tempCoordinates.add(new Coordinates(12, 11));
    	
    	
    	for (Coordinates coordinates2 : tempCoordinates) {
    		
    		if(!coordinates.contains(coordinates2)) {
    			System.err.println("The coordinate '" + coordinates2.toString() + "' is already in use.");
    			continue;
    		}
    		if(coordinates2.equals(new Coordinates(7, 3))) 
    			context.add(new Obstacle(coordinates2.getX(), coordinates2.getY(), 2));
    		else context.add(new Obstacle(coordinates2.getX(), coordinates2.getY()));
			coordinates.remove(coordinates2);
    	}
    }
    
    public static void Maze5050(Context<Object> context, List<Coordinates> coordinates) {
    	List<Coordinates> tempCoordinates = new ArrayList<>();
    	
    	tempCoordinates.add(new Coordinates(2, 6));
    	tempCoordinates.add(new Coordinates(2, 7));
    	tempCoordinates.add(new Coordinates(3, 7));
    	
    	tempCoordinates.add(new Coordinates(2, 3));
    	tempCoordinates.add(new Coordinates(2, 2));
    	tempCoordinates.add(new Coordinates(2, 1));
    	
    	tempCoordinates.add(new Coordinates(7, 2));
    	tempCoordinates.add(new Coordinates(8, 2));
    	tempCoordinates.add(new Coordinates(9, 2));
    	tempCoordinates.add(new Coordinates(9, 3));
    	tempCoordinates.add(new Coordinates(9, 4));
    	tempCoordinates.add(new Coordinates(8, 4));
    	tempCoordinates.add(new Coordinates(7, 3));
    	tempCoordinates.add(new Coordinates(7, 4));
    	
    	tempCoordinates.add(new Coordinates(12, 3));
    	tempCoordinates.add(new Coordinates(12, 4));
    	
    	tempCoordinates.add(new Coordinates(11, 7));
    	tempCoordinates.add(new Coordinates(11, 8));
    	tempCoordinates.add(new Coordinates(12, 8));
    	
    	tempCoordinates.add(new Coordinates(3, 10));
    	tempCoordinates.add(new Coordinates(3, 11));
    	tempCoordinates.add(new Coordinates(3, 12));
    	tempCoordinates.add(new Coordinates(3, 13));
    	
    	tempCoordinates.add(new Coordinates(9, 11));
    	tempCoordinates.add(new Coordinates(9, 12));
    	tempCoordinates.add(new Coordinates(10, 11));
    	tempCoordinates.add(new Coordinates(10, 12));
    	tempCoordinates.add(new Coordinates(11, 11));
    	tempCoordinates.add(new Coordinates(12, 11));
    	
    	
    	//------------------------
    	
    	tempCoordinates.add(new Coordinates(15, 2));
    	tempCoordinates.add(new Coordinates(15, 3));
    	tempCoordinates.add(new Coordinates(16, 2));
    	tempCoordinates.add(new Coordinates(16, 3));
    	tempCoordinates.add(new Coordinates(17, 2));
    	tempCoordinates.add(new Coordinates(18, 2));
    	
    	tempCoordinates.add(new Coordinates(23, 3));
    	tempCoordinates.add(new Coordinates(23, 2));
    	tempCoordinates.add(new Coordinates(23, 1));
    	
    	tempCoordinates.add(new Coordinates(27, 2));
    	tempCoordinates.add(new Coordinates(28, 2));
    	tempCoordinates.add(new Coordinates(29, 2));
    	tempCoordinates.add(new Coordinates(29, 3));
    	tempCoordinates.add(new Coordinates(29, 4));
    	tempCoordinates.add(new Coordinates(28, 4));
    	tempCoordinates.add(new Coordinates(27, 3));
    	tempCoordinates.add(new Coordinates(27, 4));
    	
    	tempCoordinates.add(new Coordinates(30, 10));
    	tempCoordinates.add(new Coordinates(30, 11));
    	tempCoordinates.add(new Coordinates(30, 12));
    	tempCoordinates.add(new Coordinates(30, 13));
    	
    	tempCoordinates.add(new Coordinates(33, 2));
    	tempCoordinates.add(new Coordinates(34, 2));
    	tempCoordinates.add(new Coordinates(35, 2));
    	tempCoordinates.add(new Coordinates(35, 3));
    	tempCoordinates.add(new Coordinates(35, 4));
    	tempCoordinates.add(new Coordinates(34, 4));
    	tempCoordinates.add(new Coordinates(33, 3));
    	tempCoordinates.add(new Coordinates(33, 4));
    	
    	
    	tempCoordinates.add(new Coordinates(47, 5));
    	tempCoordinates.add(new Coordinates(47, 6));
    	tempCoordinates.add(new Coordinates(47, 7));
    	tempCoordinates.add(new Coordinates(47, 8));
    	tempCoordinates.add(new Coordinates(48, 6));
    	tempCoordinates.add(new Coordinates(48, 7));
    	
    	tempCoordinates.add(new Coordinates(16, 6));
    	tempCoordinates.add(new Coordinates(17, 6));
    	tempCoordinates.add(new Coordinates(18, 6));
    	tempCoordinates.add(new Coordinates(18, 7));
    	tempCoordinates.add(new Coordinates(18, 8));
    	tempCoordinates.add(new Coordinates(16, 7));
    	tempCoordinates.add(new Coordinates(16, 8));
    	tempCoordinates.add(new Coordinates(17, 7));
    	tempCoordinates.add(new Coordinates(17, 8));
    	tempCoordinates.add(new Coordinates(19, 7));
    	tempCoordinates.add(new Coordinates(19, 8));
    	tempCoordinates.add(new Coordinates(20, 7));
    	tempCoordinates.add(new Coordinates(20, 8));
    	
    	tempCoordinates.add(new Coordinates(16, 20));
    	tempCoordinates.add(new Coordinates(17, 20));
    	tempCoordinates.add(new Coordinates(18, 20));
    	tempCoordinates.add(new Coordinates(19, 20));
    	tempCoordinates.add(new Coordinates(20, 20));
    	tempCoordinates.add(new Coordinates(21, 20));
    	tempCoordinates.add(new Coordinates(22, 20));
    	tempCoordinates.add(new Coordinates(23, 20));
    	tempCoordinates.add(new Coordinates(24, 20));
    	tempCoordinates.add(new Coordinates(25, 20));
    	tempCoordinates.add(new Coordinates(26, 20));
    	tempCoordinates.add(new Coordinates(26, 21));
    	tempCoordinates.add(new Coordinates(26, 22));
    	tempCoordinates.add(new Coordinates(26, 23));
    	tempCoordinates.add(new Coordinates(26, 24));
    	tempCoordinates.add(new Coordinates(26, 26));
    	tempCoordinates.add(new Coordinates(26, 27));
    	tempCoordinates.add(new Coordinates(26, 28));
    	tempCoordinates.add(new Coordinates(26, 29));
    	tempCoordinates.add(new Coordinates(26, 30));
    	tempCoordinates.add(new Coordinates(25, 30));
    	tempCoordinates.add(new Coordinates(24, 30));
    	tempCoordinates.add(new Coordinates(23, 30));
    	tempCoordinates.add(new Coordinates(22, 30));
    	tempCoordinates.add(new Coordinates(21, 30));
    	tempCoordinates.add(new Coordinates(20, 30));
    	tempCoordinates.add(new Coordinates(19, 30));
    	tempCoordinates.add(new Coordinates(18, 30));
    	tempCoordinates.add(new Coordinates(17, 30));
    	tempCoordinates.add(new Coordinates(16, 30));
    	tempCoordinates.add(new Coordinates(16, 29));
    	tempCoordinates.add(new Coordinates(16, 28));
    	tempCoordinates.add(new Coordinates(16, 27));
    	tempCoordinates.add(new Coordinates(16, 26));
    	tempCoordinates.add(new Coordinates(16, 25));
    	tempCoordinates.add(new Coordinates(16, 24));
    	tempCoordinates.add(new Coordinates(16, 23));
    	tempCoordinates.add(new Coordinates(16, 22));
    	tempCoordinates.add(new Coordinates(16, 21));
    	
    	tempCoordinates.add(new Coordinates(30, 6));
    	tempCoordinates.add(new Coordinates(31, 6));
    	tempCoordinates.add(new Coordinates(32, 6));
    	tempCoordinates.add(new Coordinates(32, 7));
    	tempCoordinates.add(new Coordinates(32, 8));
    	tempCoordinates.add(new Coordinates(30, 7));
    	tempCoordinates.add(new Coordinates(30, 8));
    	tempCoordinates.add(new Coordinates(31, 7));
    	tempCoordinates.add(new Coordinates(31, 8));
    	tempCoordinates.add(new Coordinates(33, 7));
    	tempCoordinates.add(new Coordinates(33, 8));
    	tempCoordinates.add(new Coordinates(24, 7));
    	tempCoordinates.add(new Coordinates(24, 8));
    	
    	tempCoordinates.add(new Coordinates(40, 22));
    	tempCoordinates.add(new Coordinates(41, 22));
    	tempCoordinates.add(new Coordinates(42, 22));
    	tempCoordinates.add(new Coordinates(42, 23));
    	tempCoordinates.add(new Coordinates(42, 24));
    	tempCoordinates.add(new Coordinates(40, 23));
    	tempCoordinates.add(new Coordinates(40, 24));
    	tempCoordinates.add(new Coordinates(41, 23));
    	tempCoordinates.add(new Coordinates(41, 24));
    	tempCoordinates.add(new Coordinates(43, 23));
    	tempCoordinates.add(new Coordinates(43, 24));
    	tempCoordinates.add(new Coordinates(44, 23));
    	tempCoordinates.add(new Coordinates(44, 24));
    	
    	tempCoordinates.add(new Coordinates(40, 12));
    	tempCoordinates.add(new Coordinates(41, 12));
    	tempCoordinates.add(new Coordinates(42, 12));
    	tempCoordinates.add(new Coordinates(42, 13));
    	tempCoordinates.add(new Coordinates(42, 14));
    	tempCoordinates.add(new Coordinates(40, 13));
    	tempCoordinates.add(new Coordinates(40, 14));
    	tempCoordinates.add(new Coordinates(41, 13));
    	tempCoordinates.add(new Coordinates(41, 14));
    	tempCoordinates.add(new Coordinates(43, 13));
    	tempCoordinates.add(new Coordinates(43, 14));
    	tempCoordinates.add(new Coordinates(44, 13));
    	tempCoordinates.add(new Coordinates(44, 14));
    	
    	tempCoordinates.add(new Coordinates(8, 37));
    	tempCoordinates.add(new Coordinates(9, 37));
    	tempCoordinates.add(new Coordinates(10, 37));
    	tempCoordinates.add(new Coordinates(10, 38));
    	tempCoordinates.add(new Coordinates(10, 39));
    	tempCoordinates.add(new Coordinates(8, 38));
    	tempCoordinates.add(new Coordinates(8, 39));
    	tempCoordinates.add(new Coordinates(9, 38));
    	tempCoordinates.add(new Coordinates(9, 39));
    	tempCoordinates.add(new Coordinates(11, 38));
    	tempCoordinates.add(new Coordinates(11, 39));
    	tempCoordinates.add(new Coordinates(12, 38));
    	tempCoordinates.add(new Coordinates(12, 39));
    	
    	tempCoordinates.add(new Coordinates(26, 25));
    	tempCoordinates.add(new Coordinates(27, 25));
    	tempCoordinates.add(new Coordinates(28, 25));
    	tempCoordinates.add(new Coordinates(29, 25));
    	tempCoordinates.add(new Coordinates(30, 25));
    	tempCoordinates.add(new Coordinates(31, 25));
    	tempCoordinates.add(new Coordinates(32, 25));
    	tempCoordinates.add(new Coordinates(33, 25));
    	tempCoordinates.add(new Coordinates(34, 25));
    	tempCoordinates.add(new Coordinates(35, 25));
    	tempCoordinates.add(new Coordinates(36, 25));
    	tempCoordinates.add(new Coordinates(36, 26));
    	tempCoordinates.add(new Coordinates(36, 27));
    	tempCoordinates.add(new Coordinates(36, 28));
    	tempCoordinates.add(new Coordinates(36, 29));
    	tempCoordinates.add(new Coordinates(36, 30));
    	tempCoordinates.add(new Coordinates(36, 31));
    	tempCoordinates.add(new Coordinates(36, 32));
    	tempCoordinates.add(new Coordinates(36, 33));
    	tempCoordinates.add(new Coordinates(36, 34));
    	tempCoordinates.add(new Coordinates(36, 35));
    	tempCoordinates.add(new Coordinates(35, 35));
    	tempCoordinates.add(new Coordinates(34, 35));
    	tempCoordinates.add(new Coordinates(33, 35));
    	tempCoordinates.add(new Coordinates(32, 35));
    	tempCoordinates.add(new Coordinates(31, 35));
    	tempCoordinates.add(new Coordinates(30, 35));
    	tempCoordinates.add(new Coordinates(29, 35));
    	tempCoordinates.add(new Coordinates(28, 35));
    	tempCoordinates.add(new Coordinates(27, 35));
    	tempCoordinates.add(new Coordinates(26, 35));
    	tempCoordinates.add(new Coordinates(26, 34));
    	tempCoordinates.add(new Coordinates(26, 33));
    	tempCoordinates.add(new Coordinates(26, 32));
    	tempCoordinates.add(new Coordinates(26, 31));
    	tempCoordinates.add(new Coordinates(2, 26));
    	tempCoordinates.add(new Coordinates(2, 27));
    	tempCoordinates.add(new Coordinates(3, 27));
    	
    	tempCoordinates.add(new Coordinates(2, 23));
    	tempCoordinates.add(new Coordinates(2, 22));
    	tempCoordinates.add(new Coordinates(2, 21));
    	
    	tempCoordinates.add(new Coordinates(7, 22));
    	tempCoordinates.add(new Coordinates(8, 22));
    	tempCoordinates.add(new Coordinates(9, 22));
    	tempCoordinates.add(new Coordinates(9, 23));
    	tempCoordinates.add(new Coordinates(9, 24));
    	tempCoordinates.add(new Coordinates(8, 24));
    	tempCoordinates.add(new Coordinates(7, 23));
    	tempCoordinates.add(new Coordinates(7, 24));
    	
    	tempCoordinates.add(new Coordinates(15, 42));
    	tempCoordinates.add(new Coordinates(15, 43));
    	tempCoordinates.add(new Coordinates(16, 42));
    	tempCoordinates.add(new Coordinates(16, 43));
    	tempCoordinates.add(new Coordinates(17, 42));
    	tempCoordinates.add(new Coordinates(18, 42));
    	
    	tempCoordinates.add(new Coordinates(23, 43));
    	tempCoordinates.add(new Coordinates(23, 42));
    	tempCoordinates.add(new Coordinates(23, 41));
    	
    	tempCoordinates.add(new Coordinates(27, 42));
    	tempCoordinates.add(new Coordinates(28, 42));
    	tempCoordinates.add(new Coordinates(29, 42));
    	tempCoordinates.add(new Coordinates(29, 43));
    	tempCoordinates.add(new Coordinates(29, 44));
    	tempCoordinates.add(new Coordinates(28, 44));
    	tempCoordinates.add(new Coordinates(27, 43));
    	tempCoordinates.add(new Coordinates(27, 44));
    	
    	tempCoordinates.add(new Coordinates(30, 40));
    	tempCoordinates.add(new Coordinates(30, 41));
    	tempCoordinates.add(new Coordinates(30, 42));
    	tempCoordinates.add(new Coordinates(30, 43));
    	
    	tempCoordinates.add(new Coordinates(33, 42));
    	tempCoordinates.add(new Coordinates(34, 42));
    	tempCoordinates.add(new Coordinates(35, 42));
    	tempCoordinates.add(new Coordinates(35, 43));
    	tempCoordinates.add(new Coordinates(35, 44));
    	tempCoordinates.add(new Coordinates(34, 44));
    	tempCoordinates.add(new Coordinates(33, 43));
    	tempCoordinates.add(new Coordinates(33, 44));
    	
    	
    	tempCoordinates.add(new Coordinates(2, 46));
    	tempCoordinates.add(new Coordinates(2, 47));
    	tempCoordinates.add(new Coordinates(3, 47));
    	
    	tempCoordinates.add(new Coordinates(2, 43));
    	tempCoordinates.add(new Coordinates(2, 42));
    	tempCoordinates.add(new Coordinates(2, 41));
    	
    	tempCoordinates.add(new Coordinates(7, 42));
    	tempCoordinates.add(new Coordinates(8, 42));
    	tempCoordinates.add(new Coordinates(9, 42));
    	tempCoordinates.add(new Coordinates(9, 43));
    	tempCoordinates.add(new Coordinates(9, 44));
    	tempCoordinates.add(new Coordinates(8, 44));
    	tempCoordinates.add(new Coordinates(7, 43));
    	tempCoordinates.add(new Coordinates(7, 44));
    	
    	
    	tempCoordinates.add(new Coordinates(3, 40));
    	tempCoordinates.add(new Coordinates(3, 41));
    	tempCoordinates.add(new Coordinates(3, 42));
    	tempCoordinates.add(new Coordinates(3, 43));
    	
    	tempCoordinates.add(new Coordinates(9, 41));
    	tempCoordinates.add(new Coordinates(10, 41));
    	
    	
    	tempCoordinates.add(new Coordinates(18, 17));
    	tempCoordinates.add(new Coordinates(19, 17));
    	tempCoordinates.add(new Coordinates(20, 17));
    	tempCoordinates.add(new Coordinates(20, 18));
    	tempCoordinates.add(new Coordinates(20, 19));
    	tempCoordinates.add(new Coordinates(19, 19));
    	tempCoordinates.add(new Coordinates(18, 18));
    	tempCoordinates.add(new Coordinates(18, 19));
    	
    	for (Coordinates coordinates2 : tempCoordinates) {
    		
    		if(!coordinates.contains(coordinates2)) {
    			System.err.println("The coordinate '" + coordinates2.toString() + "' is already in use.");
    			continue;
    		}
    		if(coordinates2.equals(new Coordinates(7, 3))) 
    			context.add(new Obstacle(coordinates2.getX(), coordinates2.getY(), 2));
    		else context.add(new Obstacle(coordinates2.getX(), coordinates2.getY()));
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
    			context.add(new Obstacle(coordinates2.getX(), coordinates2.getY(), 2));
    		else context.add(new Obstacle(coordinates2.getX(), coordinates2.getY()));
			coordinates.remove(coordinates2);
    	}
    }
    
    public static void Setup3(Context<Object> context, List<Coordinates> coordinates) {
    	List<Coordinates> tempCoordinates = new ArrayList<>();    	
    	tempCoordinates.add(new Coordinates(6+20, 4));
    	tempCoordinates.add(new Coordinates(6+20, 5));
    	tempCoordinates.add(new Coordinates(6+20, 6));
    	tempCoordinates.add(new Coordinates(6+20, 7));
    	tempCoordinates.add(new Coordinates(6+20, 8));
    	tempCoordinates.add(new Coordinates(5+20, 8));
    	tempCoordinates.add(new Coordinates(4+20, 8));
    	tempCoordinates.add(new Coordinates(3+20, 8));
    	tempCoordinates.add(new Coordinates(2+20, 3));
    	tempCoordinates.add(new Coordinates(2+20, 4));
    	tempCoordinates.add(new Coordinates(2+20, 5));
    	tempCoordinates.add(new Coordinates(2+20, 6));
    	tempCoordinates.add(new Coordinates(2+20, 7));
    	tempCoordinates.add(new Coordinates(3+20, 3));
    	tempCoordinates.add(new Coordinates(4+20, 3));
    	tempCoordinates.add(new Coordinates(5+20, 3));
    	tempCoordinates.add(new Coordinates(6+20, 3));
    	tempCoordinates.add(new Coordinates(7+20, 3));
    	tempCoordinates.add(new Coordinates(2+20, 8));
    	
    	tempCoordinates.add(new Coordinates(12+20, 12));
    	tempCoordinates.add(new Coordinates(12+20, 11));
    	tempCoordinates.add(new Coordinates(11+20, 11));
    	tempCoordinates.add(new Coordinates(11+20, 12));
    	
    	for (Coordinates coordinates2 : tempCoordinates) {
    		
    		if(!coordinates.contains(coordinates2)) {
    			System.err.println("The coordinate '" + coordinates2.toString() + "' is already in use.");
    			continue;
    		}
    		if(coordinates2.equals(new Coordinates(2, 4))) 
    			context.add(new Obstacle(coordinates2.getX(), coordinates2.getY(), 2));
    		else context.add(new Obstacle(coordinates2.getX(), coordinates2.getY()));
			coordinates.remove(coordinates2);
    	}
    }
}
