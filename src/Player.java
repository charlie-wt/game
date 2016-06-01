import static org.lwjgl.opengl.GL11.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Player {
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;

	private int x, y;
	private int sx = 50, sy = 87;
	private float vy=0;
	private int walkspeed = 7, jumpspeed = 10;
	private float gravity = 0.7f;
	private int facing = RIGHT;
	private Texture texture;

	public Player(int x, int y){
		this.x = x;
		this.y = y;
		this.texture = loadTexture("braidstandsmall_c");
	}

	public Player(){
		this(0, 0);
	}

	public void getInput(){
	// Key press -> character movement (via other method).
		if(Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)){
			move(UP);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			move(LEFT);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			move(RIGHT);
		}
		
		Display.setTitle("x: " + x + " y: " + y);
	}

	public void move(int dir){
	// Updates location/velocity variables.
		switch(dir){
			case LEFT:	if(checkEdge(LEFT)) { x -= walkspeed; facing = LEFT;  }else{x = 0;} break;
			case RIGHT:	if(checkEdge(RIGHT)){ x += walkspeed; facing = RIGHT; }else{x = Display.getWidth() - sx;} break;
			case UP:	if(!checkEdge(DOWN)){ y  = jumpspeed; vy = jumpspeed; } break;
		}
	}

	public boolean checkEdge(int dir){
	// Returns false if the player is currently up against a wall (or direction is invalid).
		switch(dir){
			case LEFT:	return x > 0;
			case RIGHT:	return x+sx < Display.getWidth();
			case UP:	return y+sy < Display.getHeight();
			case DOWN:	return y > 0;
		}
		return false;
	}

	public void update(){
		// Gravity, and not falling through the floor.
		if(checkEdge(DOWN)){
			vy -= gravity;
			y += vy;
		}else{
			vy = 0;
			y = 0;
		}
	}

	public void render(){
	// Draws the player sprite.
		int xpad = texture.getImageWidth() - sx;			// Is the difference between the texture image's size, and the actual size of the player as interpreted by physics (since slick only likes power of two textures).

		glPushMatrix();{
			// Set color, translation (location), rotation & texture.
			glColor3f(1f, 1f, 1f);
			glTranslatef(x-(xpad/2), y, 0);
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

	private static Texture loadTexture(String name){
	// TODO - THIS PROBABLY SHOULDN'T BE LOCATED HERE
		try {
			return TextureLoader.getTexture("png",  new FileInputStream(new File("res/" + name + ".png")));
		} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		return null;
	}
}