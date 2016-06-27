package Game;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public abstract class Option {
	protected Game game;
	protected String name;
	protected Texture tex;
	
	public Option (Game game, String name) {
		this.game = game;
		this.name = name;
		this.tex = Game.loadTexture("text/" + name);
	}
	
	public abstract void activate ();
	
	public int getX (int index) {
		return (Display.getWidth()/2) - (tex.getImageWidth()/2);
	}
	
	public int getY (int index) {
		return 300 - index*tex.getImageHeight() - index*10;
	}
	
	public void drawOption (int index) {
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
	
	public Game getGame(){return game;}
	public String getName(){return name;}
	public Texture getTex(){return tex;}
}