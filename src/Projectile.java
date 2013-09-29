import org.newdawn.slick.Image;


public class Projectile extends Actor {
	
	protected int damage;
	protected int velocity;
	protected Image img;

	public Projectile(int x, int y, int hlth, int dmg, int vel, Image im) {
		super(x, y, hlth);
		damage = dmg;
		velocity = vel;
		box = new ActorBox(x, y, 10);
		img = im;
	}
	
	public void move() {
		xPos += velocity;
		box.update(xPos, yPos);
	}
	
	public int getDamage() {
		return damage;
	}
	
	public Image getImage() {
		return img;
	}

}
