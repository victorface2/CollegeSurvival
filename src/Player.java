import org.newdawn.slick.Image;
public class Player extends Actor {
	
	final static int GRAVITY = 1;
	final int MAXHEALTH = 100;
	final int COOLDOWN = 10;
	protected int boxWidth = 25;
	protected int cdCount;
	protected int yVelocity;
	protected int ground;
	protected int healthCount;
	
	public Player(int x, int y, int hlth) {
		super(x, y, hlth);
		yVelocity = 0;
		cdCount = 0;
		ground = (int) (SurviveCollege.SCREEN_H * .63);
		box = new ActorBox(x, y, boxWidth);
	}
	
	public void move() {
		if (yPos + yVelocity > ground) {
			yPos = ground;
			yVelocity = 0;
		} else {
			yPos += yVelocity;
		}
		
		if (yPos == ground) {
			yVelocity = 0;
		} else {
			yVelocity += GRAVITY;
		}
		box.update(xPos, yPos);
		healthRegen();
	}
	
	private void healthRegen() {
		if (health < MAXHEALTH) {
			if (healthCount == 100) {
				health += 1;
				healthCount = -1;
			}
			healthCount ++;
		}
	}

	public void moveX(int x) {
		int result = xPos + x;
		if (result < SurviveCollege.SCREEN_W - 20 && result > 20) {
			xPos = result;
		}
	}
	
	public boolean canJump() {
		if (yPos == ground) {
			return true;
		}
		return false;
	}
	
	public void jump() {
		yVelocity = -23;
	}
	
	public boolean canShoot() {
		return (cdCount == 0);
	}
	
	public void decreaseCooldown() {
		if (cdCount > 0){
			cdCount -= 1;
		}
	}
	
	public void shoot() {
		cdCount = COOLDOWN;
	}
	
}
