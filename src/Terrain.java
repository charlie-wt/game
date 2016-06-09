import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;

public class Terrain {
	public static final int BACKGROUND=0, GRASS=1, DIRT=2, SPIKES=3;
	public static final int size = 50;
	
	public static void render(int x, int y, int type){
		Texture tex;
		glPushMatrix();{
			// Set color, translation (location), rotation & texture.
			glColor3f(1f, 1f, 1f);
			switch(type){
				case GRASS:  tex = Game.loadTexture("grass64");break;
				case DIRT:   tex = Game.loadTexture("dirt64"); break;
				case SPIKES: tex = Game.loadTexture("spikes64"); break;
				default: return;
			}
			
			int xpad = tex.getImageWidth() - size;
/*			switch(type){
				case GRASS:  glColor3f(0.43f, 0.81f, 0.16f); break;
				case DIRT:   glColor3f(0.61f, 0.43f, 0.13f); break;
				case SPIKES: glColor3f(0.86f, 0.86f, 0.86f); break;
				default: return;
			}*/
			glTranslatef(x - (xpad/2), y, 0);
			glRotatef(0, 0, 0, 1);
			tex.bind();

			// Draw the points to form the shape.
			glBegin(GL_QUADS);{
				glTexCoord2f(0,tex.getHeight());
				glVertex2f(0,0);

				glTexCoord2f(tex.getWidth(),tex.getHeight());
				glVertex2f(tex.getImageWidth(),0);

				glTexCoord2f(tex.getWidth(),0);
				glVertex2f(tex.getImageWidth(),tex.getImageHeight());

				glTexCoord2f(0,0);
				glVertex2f(0,tex.getImageHeight());
			}glEnd();
/*			glBegin(GL_QUADS);{
				glVertex2f(0,0);
				glVertex2f(size,0);
				glVertex2f(size,size);
				glVertex2f(0,size);
			}glEnd();*/
		}glPopMatrix();
	}
}