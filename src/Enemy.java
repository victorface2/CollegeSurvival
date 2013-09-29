import org.newdawn.slick.Image;
public class Enemy extends Actor {
	
	final int MAXHEALTH = 1000;
	final int COOLDOWN = 80;
	final int MOVEMENT_SPEED = 4;
	protected int boxWidth = 25;
	protected int cdCount;
	protected int bottomLimit, leftLimit;
	protected boolean up;
	protected boolean left;
	protected int healthCount;
	
	public Enemy(int x, int y, int hlth) {
		super(x, y, hlth);
		cdCount = 0;
		box = new ActorBox(x, y, boxWidth);
		bottomLimit = y;
		leftLimit = x - 150;
		up = true;
		left = true;
	}
	
	public void move() {
		if (cdCount > 0) {
			cdCount -= 1;
		}
		
		if (up) {
			yPos -= MOVEMENT_SPEED;
		} else {
			yPos += MOVEMENT_SPEED;
		}
		
		if (yPos + 5 > bottomLimit) {
			up = true;
		} else if (yPos - 200 < 0) {
			up = false;
		}
		
		if (left) {
			xPos -= MOVEMENT_SPEED;
		} else {
			xPos += MOVEMENT_SPEED;
		}
		
		if (xPos - 5 < leftLimit) {
			left = false;
		} else if (xPos + 5 > leftLimit + 150) {
			left = true;
		}
		
		
		box.update(xPos, yPos);
		healthRegen();
	}
	
	private void healthRegen() {
		if (health < MAXHEALTH) {
			if (healthCount == 40) {
				health += 5;
				healthCount = -1;
			}
			healthCount ++;
		}
	}
	
	public boolean canShoot() {
		return (cdCount == 0);
	}
	
	public void shoot() {
		cdCount = genCooldown();
	}
	
	public int genCooldown() {
		if (health > MAXHEALTH / 5) {
			return (int) (Math.random() * COOLDOWN + 10);
		}
		return (int) (Math.random() * (COOLDOWN/2));
		
	}

}
