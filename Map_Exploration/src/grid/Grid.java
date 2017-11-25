package grid;

import java.util.ArrayList;
import java.util.List;

import utils.Coordinates;

public class Grid {

	private Node[][] grid;
	private int gridSizeX;
	private int gridSizeY;
	
	public Grid(int gridSizeX, int gridSizeY) {
		grid = new Node[gridSizeX][gridSizeY];
		this.gridSizeX = gridSizeX;
		this.gridSizeY = gridSizeY;
		
		for(int y = gridSizeY - 1; y >= 0; y--)
			for(int x = 0; x < gridSizeX; x++)
				grid[y][x] = new Node(new Coordinates(x, gridSizeY - y), new Coordinates(x, y));
	}
	
	/**
	 * Returns all the node's neighbours.
	 * @param node to get neighbours from.
	 * @return list of neighbours.
	 */
	public List<Node> getNeighbours(Node node) {
		List<Node> neighbours = new ArrayList<Node>();
		
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				if(x == 0 && y == 0)
					continue;
				
				int checkX = node.getGridPosition().getX() + x;
				int checkY = node.getGridPosition().getY() + y;
				if(checkX >= 0 && checkX < gridSizeX && checkY >= 0 && checkY < gridSizeY)
					neighbours.add(grid[checkY][checkX]);
			}
		}
		
		return neighbours;
	}
	
	/**
	 * Returns the node that is in the specified world position.
	 * @param worldPosition
	 * @return node that is in the world position.
	 */
	public Node nodeFromWorldPoint(Coordinates worldPosition) {
		return grid[gridSizeY - 1 - worldPosition.getY()][worldPosition.getX()];
	}
}
