package algorithms.dfs;

import java.util.List;

import agents.Explorer;
import behaviours.Exploration;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.space.grid.GridPoint;
import utils.Coordinates;
import utils.Utils.ExplorerState;

public class DFS {

	private Explorer agent;
	private Exploration behaviour;
	
	public DFS(Explorer agent, Exploration behaviour) {
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
			int row = agent.getGrid().getDimensions().getHeight() - 1 - gridCell.getPoint().getY();
			int column = gridCell.getPoint().getX();
			
			// If the point is inside the grid...
			if(row >= 0 && row < agent.getGrid().getDimensions().getHeight() && column >= 0 && column < agent.getGrid().getDimensions().getWidth()) {
				if(agent.getMatrix().getValue(row, column) != 1)
					destinationCell = gridCell;
			}
		}
		
		if(destinationCell != null)
			agent.moveAgent(Coordinates.FromGridPoint(destinationCell.getPoint()));
		else if(!agent.getMatrix().hasUndiscoveredCells())
			behaviour.changeState(ExplorerState.EXIT);
		else behaviour.changeState(ExplorerState.A_STAR);
	}
}
