
public class ActorBox {
	protected int xPos;
	protected int yPos;
	protected int radius;
	
	public ActorBox(int x, int y, int r) {
		xPos = x;
		yPos = y;
		radius = r;
	}
	
	public void update(int x, int y) {
		xPos = x;
		yPos = y;
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public boolean inRange(ActorBox box) {
		int threshold = radius + box.getRadius();
		int xdist = xPos - box.getX();
		int ydist = yPos - box.getY();
		double dist = Math.sqrt(Math.pow(xdist,2) + Math.pow(ydist, 2));
		if (dist < threshold) {
			return true;
		}
		return false;
	}
}
