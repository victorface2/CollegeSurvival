import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.Color;
import org.newdawn.slick.Animation;
 
public class SurviveCollege extends BasicGame implements KeyListener {
 
    public static final int SCREEN_W = 900;
    public static final int SCREEN_H = 600;
    private Image playerImage, enemyImage, background, matrix, fail, enemyImageMad, eq1, eq2, asiandad;
    private Game game;
    private Player player;
    private Enemy enemy;
    private ArrayList<Image> studentAttacks;
    private ArrayList<Image> enemyAttacks;
    UnicodeFont font;
    private int playerHealthWidth, enemyHealthWidth;
    private String enemyHealth, playerHealth;
    
    boolean gameOver = true, menu = true, inGame, resuming;
    private boolean paused;
    private final static String title = "Survive College!";
	private final static String pausedMessage = "<ESC> to Resume. <ENTER> for Menu.";
	private final static String startFromMenu = "<ENTER> to Start";
	private final static String gameOverMessage1 = "GAME OVER.";
	private final static String gameOverMessage2 = "You lost or won.";
	private final static String gameOverMessage3 = "It doesn't matter because Asian dad is still disappointed.";
	private int titleWidth, pausedMessageWidth, startFromMenuWidth, gOMW1, gOMW2, gOMW3;
	
	private float playerGpa;
	private Color gpaColor = Color.green;
	private Animation dad;
	private int asianDadCounter;
	private final int asianDadCount = 300;
	private int asianDadHealthTracker;
	private String words;
	
	private ArrayList<String> dadSayings1, dadSayings2;
    
 
    public SurviveCollege() {
        super("Survive College");
    }
    
    public static void main(String[] args) throws SlickException {
    	try {
    	    AppGameContainer app = new AppGameContainer(new SurviveCollege());
            app.setDisplayMode(900, 600, false);
            app.start();
    	} catch (SlickException e) {
    	    e.printStackTrace();
    	}
    }
 
    @Override
    public void init(GameContainer gc) throws SlickException {
    	studentAttacks = new ArrayList<Image>();
        enemyAttacks = new ArrayList<Image>();
        dadSayings1 = new ArrayList<String>();
        dadSayings2 = new ArrayList<String>();
        initializeSayings1();
        initializeSayings2();
        playerImage = new Image("res/fox1.png");
        background = new Image("res/Background.png");
        enemyImage = new Image("res/wizardf.png");
        enemyImageMad = new Image("res/wizardfmad.png");
        fail = new Image("res/fail.png");
        matrix = new Image("res/matrix.png");
        eq1 = new Image("res/eq1.png");
        eq2 = new Image("res/eq2.png");
        asiandad = new Image("res/asiandad.png");
        
        studentAttacks.add(matrix);
        enemyAttacks.add(fail);
        enemyAttacks.add(eq1);
        enemyAttacks.add(eq2);
        
        player = new Player(100, 100, 100);
        enemy = new Enemy(800, 380, 1000);
        gameOver = false;
        game = new Game(player, enemy);
        
        font = new UnicodeFont("fonts/font.ttf", 40, false, false);
        font.addAsciiGlyphs();
        font.getEffects().add(new ColorEffect());
        font.loadGlyphs();
        
        enemyHealth = "GPA: " + enemy.getHealth();
        playerHealth = "GPA: " + player.getHealth();
        enemyHealthWidth = font.getWidth(enemyHealth);
        playerHealthWidth = font.getWidth(playerHealth);
        
        pausedMessageWidth = font.getWidth(pausedMessage);
        startFromMenuWidth = font.getWidth(startFromMenu);
        gOMW1 = font.getWidth(gameOverMessage1);
        gOMW2 = font.getWidth(gameOverMessage2);
        gOMW3 = font.getWidth(gameOverMessage3);
        titleWidth = font.getWidth(title);
        
        asianDadHealthTracker = player.getHealth();
    }
    
    private void initializeSayings1() {
    	dadSayings1.add("I too would be scared for your life.");
    	dadSayings1.add("Why you no doctor yet?");
    	dadSayings1.add("99%? I have no son.");
    	dadSayings1.add("You're 5 years old? When I was your age I was 6.");
    	dadSayings1.add("You failed drivers test?!. Thats okay me too.");
    	dadSayings1.add("I too would be scared for your life.");
    }
    
    private void initializeSayings2() {
    	dadSayings2.add("You got 99 problems. Solve every one.");
    	dadSayings2.add("I have no son.");
    	dadSayings2.add("I will kill you.");
    	dadSayings2.add("100%? Why not 101%?.");
    	dadSayings2.add("You forget to study? I forget to feed you.");
    	dadSayings2.add("I hate you.");
    }
    
    private void reset() {
    	player = new Player(100, 100, 100);
        enemy = new Enemy(800, 380, 1000);
        gameOver = false;
        game = new Game(player, enemy);
    }
    
    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        if (!gameOver) {
        	Input input = gc.getInput();
        	
        	if (input.isKeyDown(Input.KEY_SPACE)) {
                if (player.canShoot()) {
                	player.shoot();
                	game.addProjectile(new Projectile(player.getX() + 65, 
                			player.getY(), 1, 50, 7, randomElemChooser(studentAttacks)));
                }
            }
        	if (input.isKeyDown(Input.KEY_UP)) {
        		if (player.canJump()) {
                	player.jump();
                }
        	}
        	if (input.isKeyDown(Input.KEY_LEFT)) {
        		player.moveX(-5);
        	}
        	if (input.isKeyDown(Input.KEY_RIGHT)) {
        		player.moveX(5);
        	}
        	
        	if(enemy.canShoot()) {
            	enemy.shoot();
            	game.addProjectile(new Projectile(enemy.getX() - 65,
            			enemy.getY(), 1, 10, -7, randomElemChooser(enemyAttacks)));
            }
            game.update();
            playerHealth = "GPA: " + ((float) player.getHealth() / 25);
            enemyHealth = "HP: " + enemy.getHealth();
            

            // Check for loss
            if (game.checkLose()) {
            	gameOver = true;
            }
        }
    }
    
    private Image randomElemChooser(ArrayList<Image> images) {
    	Random randomGenerator = new Random();
        return images.get(randomGenerator.nextInt(images.size()));
    }
    
    private String randomStringChooser(ArrayList<String> list) {
    	Random randomGenerator = new Random();
    	int a = randomGenerator.nextInt(list.size());
        return list.get(a); 
    }
    
    
    private void renderPause(GameContainer gc, Graphics g) throws SlickException {
        // draw the background image
        background.draw(0, 0);
        // set the text color to white
        g.setColor(Color.white);
        // draw the pause screen text
        font.drawString(gc.getWidth() / 2 - pausedMessageWidth / 2, gc.getHeight() / 2, pausedMessage);
    }
 
    private void renderMenu(GameContainer gc, Graphics g) throws SlickException {
        // draw the background image
        background.draw(0, 0);
        // set the text color to white
        g.setColor(Color.white);
        // Draw the title, start option, and high score text
        font.drawString(SCREEN_W / 2 - titleWidth / 2, SCREEN_H / 2 - 100,
                title);
        font.drawString(SCREEN_W / 2 - startFromMenuWidth / 2, SCREEN_H / 2,
                startFromMenu);
 
    }
 
    public void renderGame(GameContainer gc, Graphics g) throws SlickException {
    	background.draw(0, 0);
    	g.setColor(Color.red);
    	playerImage.draw(player.getX(), player.getY());
    	if (enemy.getHealth() > enemy.MAXHEALTH / 5) {
    		enemyImage.draw(enemy.getX(), enemy.getY());
    	} else {
    		enemyImageMad.draw(enemy.getX(), enemy.getY());
    	}
    	

        for (Projectile p : game.getProjectiles()) {
        	p.getImage().draw(p.getX(), p.getY());
        }
        
        playerGpa = ((float) player.health) / 25;
        
        if (playerGpa > 3.5) {
        	gpaColor = Color.green;
        } else if (playerGpa > 2.5) {
        	gpaColor = Color.yellow;
        } else if (playerGpa > 1.5){
        	gpaColor = Color.red;
        } else {
        	gpaColor = Color.red;
        	playerHealth = "$##@!$*@";
        }
        if (asianDadCounter > 0) {
        	asiandad.draw(0, 0);
        	asianDadCounter -= 1;
        	//font.drawString(230, 100, words);
        } else if (player.getHealth() < asianDadHealthTracker) {
        	asianDadCounter = asianDadCount;
        	if (player.getHealth() < 60) {
        		words = randomStringChooser(dadSayings2);
        	} else {
        		words = randomStringChooser(dadSayings1);
        	}
        }
        asianDadHealthTracker = player.getHealth();
        
        font.drawString(player.getX(), player.getY() - 50, playerHealth, gpaColor);
        font.drawString(enemy.getX(), enemy.getY() - 50, enemyHealth);
        
        if (gameOver) {
        	asianDadCounter = 1000;
            font.drawString(400, 100, gameOverMessage1);
            font.drawString(300, 175, gameOverMessage2);
            font.drawString(0, 250, gameOverMessage3);
            font.drawString(SCREEN_W / 2 - pausedMessageWidth / 2, 0, pausedMessage);
        } 
    }
 
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        // Draw paused screen
        if (paused) {
            renderPause(gc, g);
        }
        // Draw the game
        else if (inGame) {
            renderGame(gc, g);
        }
        // Draw the menu
        else if (menu) {
            renderMenu(gc, g);
        }
        // Limit to 60 fps
        Display.sync(60);
 
    }

    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
        case Input.KEY_ENTER:
            // go from menu to start a new game
            if (menu) {
                reset();
                menu = false;
                inGame = true;
            }// leave from pause screen to menu
            else if (paused || (gameOver)) {
                reset();
                inGame = false;
                gameOver = true;
                menu = true;
                paused = false;
            }
 
            break;
        case Input.KEY_ESCAPE:
            if (inGame) {
                paused = true;
                inGame = false;
            }
            // unpause
            else if (paused) {
                menu = false;
                inGame = true;
                paused = false;
                resuming = true;
            }
 
            break;
 
        }
         
    }
    
    
}
