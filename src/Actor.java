
public class Actor {
	protected int xPos;
	protected int yPos;
	protected int health;
	protected ActorBox box;
	
	public Actor(int x, int y, int hlth) {
		xPos = x;
		yPos = y;
		health = hlth;
		box = null;
	}
	
	public int getX() {
		return xPos;
	}
	
	public void setX(int x) {
		xPos = x;
	}
	
	public int getY() {
		return yPos;
	}
	
	public void setY(int y) {
		xPos = y;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int hlth) {
		health = hlth;
	}
	
	public void takeDamage(int dmg) {
		health -= dmg;
	}
	
	public boolean isDead() {
		return (health <= 0);
	}
	
	public void move() {
	}
	
	public ActorBox getBox() {
		return box;
	}
	
	public boolean isColliding(Actor other) {
		return box.inRange(other.getBox());
	}
	
}
