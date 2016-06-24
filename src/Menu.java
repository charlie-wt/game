import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class Menu {
	private String title;
	private Texture titletex;
	Texture[] options;
	
	public Menu (String title, String titletex, Texture[] options) {
		this.title = title;
		Display.setTitle(title);
		this.titletex = Game.loadTexture("res/" + titletex);
		this.options = options;
	}
	
	public Menu (String title, Texture[] options) {
		this(title, null, options);
	}
	
	public Menu (String title, String titletex) {
		this(title, titletex, null);
	}
	
	public Menu (String title) {
		this(title, null, null);
	}
	
	public int getX (int index) {
		return (Display.getWidth()/2) - (options[index].getImageWidth()/2);
	}
	
	public int getY (int index) {
		return 800 - options[index].getImageHeight() - index*100;
	}
	
	public void render () {
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
	
	public void update () {
		
	}
}