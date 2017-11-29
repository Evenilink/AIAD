package algorithms.dfs;

import java.util.Iterator;
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
		GridCell<Object> destinationCell = null;
		for (GridCell<Object> gridCell : gridCells) {
			Iterator<Object> it = gridCell.items().iterator();
			while(it.hasNext()) {
				if(it.next() instanceof Explorer) {
					
				}
			}
			
			int row = agent.getGrid().getDimensions().getHeight() - 1 - gridCell.getPoint().getY();
			int column = gridCell.getPoint().getX();
			
			// If the point is inside the grid...
			if(row >= 0 && row < agent.getGrid().getDimensions().getWidth() && column >= 0 && column < agent.getGrid().getDimensions().getHeight()) {
				if(behaviour.getMatrixValue(column, row) != 1) {
					behaviour.setMatrixValue(column, row, 1);
					destinationCell = gridCell;
				}
			}
		}
		
		/*NdPoint origin = space.getLocation(agent);
		printMatrix(origin);
		iteration++;*/
		
		if(destinationCell != null)
			agent.moveAgent(destinationCell.getPoint());
		else if(behaviour.mapFullyExplored())
			behaviour.changeState(ExplorerState.EXIT);
		else behaviour.changeState(ExplorerState.A_STAR);
	}
}
