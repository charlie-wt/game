import static org.lwjgl.opengl.GL11.*;
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
	
	public void render(){
	// Draws the entity sprite.
		int xpad = texture.getImageWidth() - w;			// Is the difference between the texture image's size, and the actual size of the player as interpreted by physics (since slick only likes power of two textures).
		
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
}
