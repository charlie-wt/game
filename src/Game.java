import static org.lwjgl.opengl.GL11.*;
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
	private Player player;
	private Level level;
	private int camerax, camerawidth;

	public Game(){
		this.level = Level.fromFile("lvl1", this);
		this.player = new Player(level, this);
		this.camerax = 0;
		this.camerawidth = 600;
	}

	public void render(){
		level.drawBackground();
		drawTitle();
		drawOption();
/*		player.render(camerax);
		for (Entity e : level.getEntities()){
			e.render(camerax);
		}
		level.render(camerax);*/
	}
	
	public void update(){
	// Game logic.
/*		player.update();
		
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
		}*/
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
	
	public void drawTitle () {
		Texture titletex = loadTexture("title");
		float w, h;
		int iw, ih, x, y;
		
		glPushMatrix();{
			// Set color, translation (location), rotation & texture.
			glColor3f(1f, 1f, 1f);
			titletex.bind();
			
			w = titletex.getWidth();
			h = titletex.getHeight();
			iw = titletex.getImageWidth();
			ih = titletex.getImageHeight();
			x = (Display.getWidth() / 2) - (iw/2);
			y = ((Display.getHeight() / 2) - (ih/2)) + 200;
			
			glTranslatef(x, y, 0);
			glRotatef(0, 0, 0, 1);

			// Draw the points to form the shape.
			glBegin(GL_QUADS);{
				glTexCoord2f(0,h);
				glVertex2f(0,0);

				glTexCoord2f(w,h);
				glVertex2f(iw,0);

				glTexCoord2f(w,0);
				glVertex2f(iw,ih);

				glTexCoord2f(0,0);
				glVertex2f(0,ih);
			}glEnd();
		}glPopMatrix();
	}
	
	public void drawOption () {
		Texture tex = loadTexture("text/play");
		float w, h;
		int iw, ih, x, y;
		
		glPushMatrix();{
			// Set color, translation (location), rotation & texture.
			glColor3f(1f, 1f, 1f);
			tex.bind();
			
			w = tex.getWidth();
			h = tex.getHeight();
			iw = tex.getImageWidth();
			ih = tex.getImageHeight();
			x = (Display.getWidth() / 2) - (iw/2);
			y = 350 - ih;
			
			glTranslatef(x, y, 0);
			glRotatef(0, 0, 0, 1);

			// Draw the points to form the shape.
			glBegin(GL_QUADS);{
				glTexCoord2f(0,h);
				glVertex2f(0,0);

				glTexCoord2f(w,h);
				glVertex2f(iw,0);

				glTexCoord2f(w,0);
				glVertex2f(iw,ih);

				glTexCoord2f(0,0);
				glVertex2f(0,ih);
			}glEnd();
		}glPopMatrix();
	}
}