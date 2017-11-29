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
		System.out.println("Running dfs");

		GridPoint pt = agent.getGrid().getLocation(agent);
		
		GridCellNgh<Object> nghCreator = new GridCellNgh<Object>(agent.getGrid(), pt, Object.class, agent.getRadious(), agent.getRadious());
		List<GridCell<Object>> gridCells = nghCreator.getNeighborhood(false);
		
		// SimUtilities.shuffle(gridCells, RandomHelper.getUniform());
		GridCell<Object> destinationCell = null;
		for (GridCell<Object> gridCell : gridCells) {
			
			/*Iterator<Object> it = gridCell.items().iterator();
			while(it.hasNext()) {
				Object obj = it.next();
				if(obj instanceof Explorer) {
					Explorer otherExplorer = (Explorer) obj;
					Message msgSend = new Message(MessageType.MATRIX, agent.getMatrix(), otherExplorer.getAID());
					try {
						msgSend.sendMessage();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}*/
			
			int row = agent.getGrid().getDimensions().getHeight() - 1 - gridCell.getPoint().getY();
			int column = gridCell.getPoint().getX();
			
			// If the point is inside the grid...
			if(row >= 0 && row < agent.getGrid().getDimensions().getWidth() && column >= 0 && column < agent.getGrid().getDimensions().getHeight()) {
				if(agent.getMatrix().getValue(column, row) != 1) {
					agent.getMatrix().setValue(column, row, 1);
					destinationCell = gridCell;
				}
			}
		}
		
		/*NdPoint origin = space.getLocation(agent);
		printMatrix(origin);
		iteration++;*/
		
		if(destinationCell != null) {
			System.out.println("1");
			agent.moveAgent(Coordinates.FromGridPoint(destinationCell.getPoint()));
		}
		else if(!agent.getMatrix().hasUndiscoveredCells()) {
			behaviour.changeState(ExplorerState.EXIT);
			System.out.println("2");
		}
		else {
			behaviour.changeState(ExplorerState.A_STAR);
			System.out.println("3");
		}
	}
}
