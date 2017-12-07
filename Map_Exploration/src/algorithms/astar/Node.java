package algorithms.astar;

import utils.Coordinates;

public class Node {

	private boolean walkable;	// Does this node contains objects that are not walkable?
	private Coordinates worldPosition;	// World position in Repast's grid.
	private Node parent;
	
	private int gCost;	// Distance from the source node.
	private int hCost;	// Distance from the target node (heuristic).
	
	public Node(boolean walkable, Coordinates worldPosition) {
		this.walkable = walkable;
		this.worldPosition = worldPosition;
	}
	
	public boolean getWalkable() {
		return walkable;
	}
	
	public Coordinates getWorldPosition() {
		return worldPosition;
	}

	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public int getFCost() {
		return gCost + hCost;
	}
	
	public int getGCost() {
		return gCost;
	}
	
	public void setGCost(int value) {
		gCost = value;
	}
	
	public int getHCost() {
		return hCost;
	}
	
	public void setHCost(int value) {
		hCost = value;
	}
	
	public void setWalkable(boolean newWalkable) {
		walkable = newWalkable;
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Node))
			return false;
		
		Node otherNode = (Node) other;
		return worldPosition.equals(otherNode.getWorldPosition());
	}
}
