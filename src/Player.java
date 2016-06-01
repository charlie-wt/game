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
	private int x, y;
	private int sx, sy;
	private int vx=0, vy=0;
	private int walkspeed = 5, jumpspeed = 10;
	private Texture texture;
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3; 
	
	public Player(int x, int y, String texture){
		this.x = x;
		this.y = y;
		this.sx = 64;
		this.sy = 128;
		if(texture != null){this.texture = loadTexture(texture);}
	}
	
	public Player(int x, int y){
		this(x, y, null);
	}
	
	public Player(String texture){
		this(0, 0, texture);
	}
	
	public Player(){
		this(0, 0, null);
	}
	
	public void getInput(){
	// Key press -> character movement (via other method).
		if(Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)){
			move(UP);
		}
/*		if(Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			move(DOWN);
		}*/
		if(Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			move(LEFT);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			move(RIGHT);
		}
		
		if(checkEdge(DOWN)){
			vy -= 1;
			y += vy;
		}else{
			vy = 0;
		}
		
		Display.setTitle("x: " + x + " y: " + y);
	}
	
	public void move(int dir){
	// Updates location/velocity variables.
		switch(dir){
			case LEFT:	if(checkEdge(LEFT)) { x -= walkspeed; vx = walkspeed; } break;
			case RIGHT:	if(checkEdge(RIGHT)){ x += walkspeed; vx = walkspeed; } break;
			case UP:	if(!checkEdge(DOWN)){ y  = jumpspeed; vy = jumpspeed; } break;
/*			case DOWN:	if(checkEdge(DOWN)) { y -= walkspeed; } break;*/
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
	
	public void draw(){
		if(texture != null){
			glPushMatrix();{
				// Set color, translation (location), rotation & texture.
				glColor3f(1f, 1f, 1f);
				glTranslatef(x, y, 0);
				glRotatef(0, 0, 0, 1);
				texture.bind();
				
				// Draw the points to form the shape, with appropriate texture points.
				// Slick texture coordinates go top to bottom, OpenGL coordinates go bottom to top, so must flip.
				glBegin(GL_QUADS);{
					glTexCoord2f(0,texture.getHeight());
					glVertex2f(0,0);
					
					glTexCoord2f(texture.getWidth(),texture.getHeight());
					glVertex2f(texture.getImageWidth(),0);
					
					glTexCoord2f(texture.getWidth(),0);
					glVertex2f(texture.getImageWidth(),texture.getImageHeight());
					
					glTexCoord2f(0,0);
					glVertex2f(0,texture.getImageHeight());
				}glEnd();
			}glPopMatrix();
		}else{
			glPushMatrix();{
				// Set color, translation (location) & rotation.
				glColor3f(0.5f, 0.8f, 0.5f);
				glTranslatef(x, y, 0);
				glRotatef(0, 0, 0, 1);
				
				// Draw the points to form the shape.
				glBegin(GL_QUADS);{
					glVertex2f(0, 0);
					glVertex2f(0, 50);
					glVertex2f(50, 50);
					glVertex2f(50, 0);
				}glEnd();
			}glPopMatrix();
		}
	}
	
	private static Texture loadTexture(String name){
	// TODO - THIS PROBABLY SHOULDN'T BE LOCATED HERE
		try {
			return TextureLoader.getTexture("png",  new FileInputStream(new File("res/" + name + ".png")));
		} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		return null;
	}
}