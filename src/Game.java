import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Game {
	Player player;
	Level level;
/*	Enemy enemy;*/

	public Game(){
		level = Level.fromFile("lvl1");
		player = new Player(level, this);
/*		enemy = new Enemy(level, this, 700, 200);*/
/*		level.addEnemy(enemy);*/
	}

	public void render(){
		level.drawBackground();
		player.render();
		for (Entity e : level.getEnemies()){
			e.render();
		}
		level.render();
	}
	
	public void update(){
		player.update();
		for (Entity e : level.getEnemies()){
			e.update();
			// TODO - BIT MESSY TO HAVE THIS HERE; SHOULD PROB HAVE SOMEWHERE IN Player, BUT DON'T WANT TO CYCLE THROUGH ALL ENTITIES ANY MORE TIMES.
			if(Physics.touchingEntity(player, e)){
				player.die();
			}
		}
	}
	
	public void getInput(){
		player.getInput();
	}
	
	public void loadNextLevel(){
		System.out.println("Level complete!");
		if(level.getName().equals("lvl1")){
			level = Level.fromFile("lvl2");
			player.setLevel(level);
		}else{
			System.out.println("Game complete!");
		}
	}
	
	public static Texture loadTexture(String name){
		try {
			return TextureLoader.getTexture("png",  new FileInputStream(new File("res/" + name + ".png")));
		} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		return null;
	}
}