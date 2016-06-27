import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class Menu {
	private Texture titletex;
	Option[] options;
	
	public Menu (String titletex, Option[] options) {
		this.titletex = Game.loadTexture(titletex);
		this.options = options;
		for (int i=0;i<options.length;i++) {
			options[i].setIndex(i);
		}
	}
	
	public void render () {
		drawTitle();
		for(int i=0;i<options.length;i++){
			options[i].drawOption();
		}
	}
	
	public void update () {
	// Checking if mouse is over any buttons.
		for(int i=0;i<options.length;i++){
			boolean isInX = Mouse.getX() >= options[i].getX() && Mouse.getX() <= options[i].getX() + options[i].getTex().getImageWidth();
			boolean isInY = Mouse.getY() >= options[i].getY() && Mouse.getY() <= options[i].getY() + options[i].getTex().getImageHeight();
			
			if(isInX && isInY && Mouse.isButtonDown(0)){
				options[i].activate();
			}
		}
	}
	
	public void drawTitle () {
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
}