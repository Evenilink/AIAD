package utils;

public class Utils {

	public static final double sqrt2 = Math.sqrt(2);
	
	public enum ExplorerState {
		ALEATORY_DFS,
		A_STAR,
		PLEDGE,
		EXIT
	}
	
	public static float getDistance(Coordinates source, Coordinates target) {
		int distX = Math.abs(target.getX() - source.getX());
		int distY = Math.abs(target.getY() - source.getY());
		
		if(distX > distY)
			return (float)sqrt2 * distY + 1 * (distX - distY);
		return (float)sqrt2 + 1 * (distY - distX);
	}
}
