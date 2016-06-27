import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Game {
	public static final int MAINMENU = 0, GAMEPLAY = 1, PAUSED = 2, WIN = 3;
	
	private Player player;
	private Level level;
	private Menu mainmenu;
	private int camerax, camerawidth;
	private int state = MAINMENU;

	public Game(){
		this.level = Level.fromFile("lvl1", this);
		this.player = new Player(level, this);
		
		this.camerax = 0;
		this.camerawidth = 600;
		
		Option play = new PlayOption(this, "play");
		Option[] options = {play};
		this.mainmenu = new Menu("Main Menu", "title", options);
	}

	public void render(){
		level.drawBackground();
		switch(state){
			case MAINMENU:
				mainmenu.render();
				break;
			case GAMEPLAY:
				player.render(camerax);
				for (Entity e : level.getEntities()){
					e.render(camerax);
				}
				level.render(camerax);
				break;
			case PAUSED: break;
			case WIN: break;
		}
	}
	
	public void update(){
	// Game logic.
		switch(state){
			case MAINMENU:
				mainmenu.update();
				break;
			case GAMEPLAY:
				player.update();
				
				// Updating the level entities. Checking whether the player has died, or killed an enemy.
				boolean hasDied = false;
				List<Entity> toKill = new ArrayList<Entity>();
				for (Entity e : level.getEntities()){
					e.update();
					
					if(Physics.touchingEntity(player, e)){
						if(Physics.isStomping(player, e)){
							toKill.add(e);
						}else{			
							player.die(false);
							hasDied = true;
						}
					}
				}
				if( hasDied ){
					level.resetEnemies();
				} else if( !toKill.isEmpty() ){
					player.kill();
				}		
				level.removeEntities(toKill);
				
				// Apply scrolling, if there's space for it and the player's off to one side.
				boolean isSpace = Display.getWidth() + (camerax + player.getVX()) <= level.getTerrain()[0].length*Terrain.size && camerax + player.getVX() >= 0;
				
				if( player.getX() > camerax + (Display.getWidth() / 2) + (camerawidth / 2) && player.getEffectiveVX() > 0 && isSpace ){
					camerax += player.getVX();
				}else if( player.getX() < camerax + (Display.getWidth() / 2) - (camerawidth / 2) && player.getEffectiveVX() < 0 && isSpace ){
					camerax += player.getVX();
				}
				break;
			case PAUSED: break;
			case WIN: break;
		}
	}
	
	public void getInput(){
		player.getInput();
	}
	
	public void loadNextLevel(){
		resetCamera();
		switch(level.getName()){
			case "lvl1" : level = Level.fromFile("lvl2", this); break;
			case "lvl2" : level = Level.fromFile("lvl3", this); break;
		}
		player.setLevel(level);
	}
	
	public static Texture loadTexture(String name){
		try {
			return TextureLoader.getTexture("png",  new FileInputStream(new File("res/" + name + ".png")));
		} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		return null;
	}
	
	public void resetCamera(){
		camerax = 0;
	}
	
	public void setState(int state){this.state = state;}
}