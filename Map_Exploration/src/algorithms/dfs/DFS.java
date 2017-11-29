package algorithms.dfs;

import java.util.List;

import agents.Explorer;
import behaviours.AleatoryDFS;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.space.grid.GridPoint;
import utils.Utils.ExplorerState;

public class DFS {

	private Explorer agent;
	private AleatoryDFS behaviour;
	
	public DFS(Explorer agent, AleatoryDFS behaviour) {
		this.agent = agent;
		this.behaviour = behaviour;
	}
	
	public void run() {
		GridPoint pt = agent.getGrid().getLocation(agent);
		
		GridCellNgh<Object> nghCreator = new GridCellNgh<Object>(agent.getGrid(), pt, Object.class, agent.getRadious(), agent.getRadious());
		List<GridCell<Object>> gridCells = nghCreator.getNeighborhood(false);
		
		// SimUtilities.shuffle(gridCells, RandomHelper.getUniform());
		GridCell<Object> cell = null;			
		for(int i = 0; i < gridCells.size(); i++) {
			int row = agent.getGrid().getDimensions().getHeight() - 1 - gridCells.get(i).getPoint().getY();
			int column = gridCells.get(i).getPoint().getX();
			
			// If the point is inside the grid...
			if(row >= 0 && row < agent.getGrid().getDimensions().getWidth() && column >= 0 && column < agent.getGrid().getDimensions().getHeight()) {
				if(behaviour.getMatrixValue(column, row) != 1) {
					behaviour.setMatrixValue(column, row, 1);
					cell = gridCells.get(i);
				}
			}
		}
		
		/*NdPoint origin = space.getLocation(agent);
		printMatrix(origin);
		iteration++;*/
		
		if(cell != null)
			// System.out.println("Moving to point => x: " + cell.getPoint().getX() + ", y: " + cell.getPoint().getY());
			agent.moveAgent(cell.getPoint());
		else if(behaviour.mapFullyExplored())
			behaviour.changeState(ExplorerState.EXIT);
		else behaviour.changeState(ExplorerState.A_STAR);
	}
}
