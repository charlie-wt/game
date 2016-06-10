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
		int[][] terr = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0},
						{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
						{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
						{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
						{1, 0, 0, 0, 0, 0, 1, 1, 2, 2, 0, 0, 2, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
						{0, 0, 0, 1, 1, 1, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 1, 1, 1, 0, 0, 0, 0, 0},
						{1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1},
					   };
//		level = new Level(terr);
		level = Level.fromFile("lvl1");
		player = new Player(100, 100, level);
	}

	public void render(){level.drawBackground(); player.render(); level.render();}
	public void update(){player.update();}
	public void getInput(){player.getInput();}
	
	public static Texture loadTexture(String name){
	// TODO - THIS PROBABLY SHOULDN'T BE LOCATED HERE
		try {
			return TextureLoader.getTexture("png",  new FileInputStream(new File("res/" + name + ".png")));
		} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		return null;
	}
}