package algorithms.dfs;

import java.util.Iterator;
import java.util.List;

import agents.Explorer;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.random.RandomHelper;
import repast.simphony.util.SimUtilities;

public class DFS {

	private Explorer agent;
	
	public DFS(Explorer agent) {
		this.agent = agent;
	}
	
	public GridCell<Object> execute(List<GridCell<Object>> neighborhoodCells) {
		List<GridCell<Object>> neighborhood = neighborhoodCells;
		//SimUtilities.shuffle(neighborhood, RandomHelper.getUniform());
		GridCell<Object> destinationCell = null;
		for (GridCell<Object> gridCell : neighborhood) {
			int row = agent.getGrid().getDimensions().getHeight() - 1 - gridCell.getPoint().getY();
			int column = gridCell.getPoint().getX();
			
			// If the point is inside the grid...
			if(row >= 0 && row < agent.getGrid().getDimensions().getHeight() && column >= 0 && column < agent.getGrid().getDimensions().getWidth()) {
				if(agent.getMatrix().getValue(row, column) == 0) {
					boolean freeCell = true;
					Iterator<Object> it = gridCell.items().iterator();
					while(it.hasNext()) {
						if(it.next() instanceof Explorer) {
							freeCell = false;
							break;
						}
					}
					
					if(!freeCell)
						continue;
					
					destinationCell = gridCell;
					break;
				}
			}
		}
		return destinationCell;		
	}
}
