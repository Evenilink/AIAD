import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridAdder;

public class LocatedAdder<Located> implements GridAdder<Located> {

	@Override
	public void add(Grid<Located> grid, Located object) {
		grid.moveTo(object, 1, 1);
	}
	
}
