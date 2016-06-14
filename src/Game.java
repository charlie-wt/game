import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Game {
	Player player;
	Level level;

	public Game(){
		level = Level.fromFile("lvl1");
		player = new Player(level, this);
	}

	public void render(){level.drawBackground(); player.render(); level.render();}
	public void update(){player.update();}
	public void getInput(){player.getInput();}
	
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