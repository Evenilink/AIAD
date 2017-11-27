package grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utils.Coordinates;

public class Pathfinding {

	private Grid grid;
	
	public Pathfinding(int gridSizeX, int gridSizeY) {
		grid = new Grid(gridSizeX, gridSizeY);
	}
	
	/**
	 * Finds the shortest path between 2 nodes using A* algorithm.
	 * @param sourceWorldPosition
	 * @param targetWorldPosition
	 * @return shortest path between 2 nodes.
	 */
	public List<Node> FindPath(Coordinates sourceWorldPosition, Coordinates targetWorldPosition) {
		Node sourceNode = grid.nodeFromWorldPoint(sourceWorldPosition);
		Node targetNode = grid.nodeFromWorldPoint(targetWorldPosition);
		
		List<Node> openSet = new ArrayList<Node>();
		List<Node> closedSet = new ArrayList<Node>();
		openSet.add(sourceNode);
		
		while(openSet.size() > 0) {
			Node currentNode = openSet.get(0);
			for (Node node : openSet) {
				if(node.getFCost() < currentNode.getFCost() || (node.getFCost() == currentNode.getFCost() && node.getHCost() < currentNode.getHCost()))
					currentNode = node;
			}
			
			openSet.remove(currentNode);
			closedSet.add(currentNode);
			
			// Path has been found.
			if(currentNode.equals(targetNode))
				return ReversePath(sourceNode, targetNode);
			
			for (Node neighbour : grid.getNeighbours(currentNode)) {
				if(!neighbour.getWalkable() || closedSet.contains(neighbour))
					continue;
				
				int newCostToNeighbour = currentNode.getGCost() + GetDistance(currentNode, neighbour);
				if(newCostToNeighbour < neighbour.getGCost() || !openSet.contains(neighbour)) {
					neighbour.setGCost(newCostToNeighbour);
					neighbour.setHCost(GetDistance(neighbour, targetNode));
					neighbour.setParent(currentNode);
					
					if(!openSet.contains(neighbour))
						openSet.add(neighbour);
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Reverses the path for the entity to traverse.
	 * Goes from the end node, to his parents, to the start node.
	 * @param startNode
	 * @param endNode
	 * @return traversal path.
	 */
	private List<Node> ReversePath(Node startNode, Node endNode) {
		List<Node> path = new ArrayList<Node>();
		Node currentNode = endNode;
		
		while(!currentNode.equals(startNode)) {
			path.add(currentNode);
			currentNode = currentNode.getParent();
		}
		
		Collections.reverse(path);
		return path;
	}
	
	/**
	 * Returns the distance between 2 nodes.
	 * @param source
	 * @param target
	 * @return distance between source and target nodes.
	 */
	private int GetDistance(Node source, Node target) {
		int distX = Math.abs(target.getGridPosition().getX() - source.getGridPosition().getX());
		int distY = Math.abs(target.getGridPosition().getY() - source.getGridPosition().getY());
		
		if(distX > distY)
			return 14 * distY + 10 * (distX - distY);
		return 14 * distX + 10 * (distY - distX);
	}
}
