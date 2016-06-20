import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;

public class Terrain {
	public static final int BACKGROUND=0, GRASS=1, DIRT=2, SPIKES=3, GOAL=4;
	public static final int size = 50;
	
	public static Texture grass = Game.loadTexture("grass");
	public static Texture dirt = Game.loadTexture("dirt");
	public static Texture spikes = Game.loadTexture("spikes");
	public static Texture goal = Game.loadTexture("goal");
	
	public static void render(int x, int y, int type){
		float w, h;
		int iw, ih;
		
		glPushMatrix();{
			// Set color, translation (location), rotation & texture.
			glColor3f(1f, 1f, 1f);
			switch(type){
				case GRASS:  grass.bind();  w = grass.getWidth();  h = grass.getHeight();  iw = grass.getImageWidth();  ih = grass.getImageHeight();  break;
				case DIRT:   dirt.bind();   w = dirt.getWidth();   h = dirt.getHeight();   iw = dirt.getImageWidth();   ih = dirt.getImageHeight();   break;
				case SPIKES: spikes.bind(); w = spikes.getWidth(); h = spikes.getHeight(); iw = spikes.getImageWidth(); ih = spikes.getImageHeight(); break;
				case GOAL:   goal.bind();   w = goal.getWidth();   h = goal.getHeight();   iw = goal.getImageWidth();   ih = goal.getImageHeight();   break;
				default: return;
			}
			
			int xpad = iw - size;
			
			glTranslatef(x - (xpad/2), y, 0);
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