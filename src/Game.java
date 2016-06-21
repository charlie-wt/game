import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Game {
	Player player;
	Level level;

	public Game(){
		level = Level.fromFile("lvl1");
		player = new Player(level, this);
	}

	public void render(){
		level.drawBackground();
		player.render();
		for (Entity e : level.getEntities()){
			e.render();
		}
		level.render();
	}
	
	public void update(){
	// Game logic.
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
					player.die();
					hasDied = true;
				}
			}
		}
		if( !toKill.isEmpty() && !hasDied ){
			player.kill();
		}
		level.removeEntities(toKill);
	}
	
	public void getInput(){
		player.getInput();
	}
	
	public void loadNextLevel(){
		switch(level.getName()){
			case "lvl1" : level = Level.fromFile("lvl2"); break;
			case "lvl2" : level = Level.fromFile("lvl3"); break;
		}
		player.setLevel(level);
	}
	
	public static Texture loadTexture(String name){
		try {
			return TextureLoader.getTexture("png",  new FileInputStream(new File("res/" + name + ".png")));
		} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		return null;
	}
}