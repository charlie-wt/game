import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class Menu {
	private String title;
	private Texture titletex;
	Option[] options;
	
	public Menu (String title, String titletex, Option[] options) {
		this.title = title;
		Display.setTitle(title);
		this.titletex = Game.loadTexture(titletex);
		this.options = options;
	}
	
	public int getX (int index) {
		return (Display.getWidth()/2) - (options[index].getTex().getImageWidth()/2);
	}
	
	public int getY (int index) {
		return 300 - index*options[index].getTex().getImageHeight() - index*10;
	}
	
	public void render () {
		drawTitle();
		for(int i=0;i<options.length;i++){
			drawOption(i);
		}
	}
	
	public void update () {
	// Checking if mouse is over any buttons.
		for(int i=0;i<options.length;i++){
			boolean isInX = Mouse.getX() >= getX(i) && Mouse.getX() <= getX(i) + options[i].getTex().getImageWidth();
			boolean isInY = Mouse.getY() >= getY(i) && Mouse.getY() <= getY(i) + options[i].getTex().getImageHeight();
			
			if(isInX && isInY){
				if(Mouse.isButtonDown(0)){
					// TODO - Not a perfect solution, as is usually triggered multiple times on click, but given the actions that will be performed on click this may not matter.
					System.out.println(options[i].getName());
					options[i].activate();
				}
				Display.setTitle("In.");
			}else{
				Display.setTitle("Out.");
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
	
	public void drawOption (int index) {
		Texture tex = options[index].getTex();
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
			x = getX(index);
			y = getY(index);

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