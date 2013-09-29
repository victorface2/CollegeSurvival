import java.util.ArrayList;

public class Game {
	protected ArrayList<Projectile> projectiles;
	protected Player player;
	protected Enemy enemy;
	
	public Game(Player plyer, Enemy enmy) {
		player = plyer;
		enemy = enmy;
		projectiles = new ArrayList<Projectile>();
	}
	
	public void update() {
		player.decreaseCooldown();
		player.move();
		enemy.move();
		for (Projectile p : projectiles) {
			p.move();
		}
		applyDmg();
		destroyProjectiles();
	}
	
	private void applyDmg() {
		for (int i = projectiles.size() - 1; i > -1; i--) {
			Projectile p = projectiles.get(i);
			if (player.isColliding(p)) {
				player.takeDamage(p.getDamage());
				projectiles.remove(i);
			} else if (enemy.isColliding(p)) {
				enemy.takeDamage(p.getDamage());
				projectiles.remove(i);
			}
		}
	}
	
	private void destroyProjectiles() {
		for (int i = projectiles.size() - 1; i > -1; i--) {
			if (projectiles.get(i).getX() < -50) {
				projectiles.remove(i);
			}
		}
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Enemy getEnemy() {
		return enemy;
	}
	
	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}
	
	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}
	
	public boolean checkWin() {
		return (enemy.getHealth() <= 0);
	}
	
	public boolean checkLose() {
		return (player.getHealth() <= 0);
	}
}
