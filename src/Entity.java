import static org.lwjgl.opengl.GL11.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class Entity {
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	
	protected int x, y;
	protected int w, h;
	protected float vx, vy;
	protected int walkspeed, jumpspeed;
	protected Texture texture;
	protected float gravity = 0.5f;
	protected int facing = RIGHT;
	protected boolean jumpFlag = false;
	protected Level level;
	protected Game game;

	public void update() {
	// Gravity only.
		checkGravity();		
	}
	
	public void render(int camerax){
	// Draws the entity sprite.
		int xpad = texture.getImageWidth() - w;			// Is the difference between the texture image's size, and the actual size of the player as interpreted by physics (since slick only likes power of two textures).
		
		glPushMatrix();{
			// Set color, translation (location), rotation & texture.
			glColor3f(1f, 1f, 1f);
			glTranslatef((x-(xpad/2)) - camerax, y, 0);
			glRotatef(0, 0, 0, 1);
			texture.bind();

			// Draw the points to form the shape, with appropriate texture points.
			// Slick texture coordinates go top to bottom, OpenGL coordinates go bottom to top, so must flip.
			glBegin(GL_QUADS);{
				if(facing == RIGHT){glTexCoord2f(0,texture.getHeight());}
				else{glTexCoord2f(texture.getWidth(),texture.getHeight());}
				glVertex2f(0,0);

				if(facing == RIGHT){glTexCoord2f(texture.getWidth(),texture.getHeight());}
				else{glTexCoord2f(0,texture.getHeight());}
				glVertex2f(texture.getImageWidth(),0);

				if(facing == RIGHT){glTexCoord2f(texture.getWidth(),0);}
				else{glTexCoord2f(0,0);}
				glVertex2f(texture.getImageWidth(),texture.getImageHeight());

				if(facing == RIGHT){glTexCoord2f(0,0);}
				else{glTexCoord2f(texture.getWidth(),0);}
				glVertex2f(0,texture.getImageHeight());
			}glEnd();
		}glPopMatrix();
	}
	
	public void checkGravity(){
		if(touchingEdge(DOWN)){
			vy = 0;
			y = 0;
		}else{
			vy -= gravity;
		}
	}
	
	public void updatePos() throws DeadException, WinException {
		if( Physics.getCollisionY(this, level) > 0 ) {vy = 0; jumpFlag = true;}else{jumpFlag = false;}
		x += vx + Physics.getCollisionX(this, level);
		y += vy + Physics.getCollisionY(this, level);
	}
	
	public boolean touchingEdge(int dir){
	// Returns true if the player is currently up against an edge of the stage (or direction is invalid).
		switch(dir){
			case LEFT:	return x <= 0;
			case RIGHT:	return (x+w+game.getCX()) >= level.getTerrain()[0].length*Terrain.size;
			case UP:	return y+h >= Display.getHeight();
			case DOWN:	return y <= 0;
		}
		return true;
	}
	
	public void playSound(File file){
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start();
		} catch (UnsupportedAudioFileException e1) {e1.printStackTrace();} catch (IOException e1) {e1.printStackTrace();} catch (LineUnavailableException e1) {e1.printStackTrace();}
	}
	
	public File findSoundFile(String name){
	// Simple way to not have to specify full path.
		String fullPath = "res/" + name + ".wav";
		return new File(fullPath);
	}
	
	public int getX(){return x;}
	public int getY(){return y;}
	public int getW(){return w;}
	public int getH(){return h;}
	public float getVX(){return vx;}
	public float getVY(){return vy;}
	public void setLevel(Level level){this.level = level;}
}
