package states;

import java.util.Iterator;
import java.util.List;

import agents.Explorer;
import algorithms.astar.Node;
import behaviours.Exploration;
import communication.IndividualMessage;
import repast.simphony.space.grid.GridPoint;
import utils.Coordinates;
import utils.Utils.MessageType;

public class TravelToObstacle implements IAgentState {
	private Exploration behaviour;
	private List<Node> path;
	private int pathNode;

	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;
		path = behaviour.getAStar().getNearestObstacle(behaviour.getAgentPoint());

		if (path == null) {
			System.err.println("Path was null...travelling to nearest undiscovered");
			behaviour.changeState(new TravelNearestUndiscovered());
		}
		pathNode = 0;
	}

	@Override
	public void execute() {
		if (path != null) {
			Coordinates target = new Coordinates(path.get(pathNode).getWorldPosition().getX(),
					path.get(pathNode).getWorldPosition().getY());
			if (behaviour.moveAgentToCoordinate(target))
				pathNode++;

			if (pathNode == path.size()) {
				boolean becameGaurdianAgent = true;
				target = behaviour.getAgentCoordinates();
				Iterator it = behaviour.getAgent().getGrid().getObjectsAt(target.getX(), target.getY()).iterator();
				while (it.hasNext()) {
					Object obj = it.next();
					if (obj instanceof Explorer) {
						Explorer otherExplorer = (Explorer) obj;
						// Check for Explorer that is not himself.
						if (!behaviour.getAgent().getLocalName().equals(otherExplorer.getLocalName())) {
							becameGaurdianAgent = false;
							break;
						}
					}
				}

				if (becameGaurdianAgent) {
					System.out.println(behaviour.getAgent().getLocalName() + " is guarding the exit.");
					behaviour.changeState(new ObstacleGuardian());
				}

				path = null;
			}
		} else
			behaviour.changeState(new TravelNearestUndiscovered());
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
	}

}
