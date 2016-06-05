import static org.lwjgl.opengl.GL11.*;

public class Terrain {
	public static final int BACKGROUND=0, GRASS=1, DIRT=2, ROCK=3, SPIKES=4;
	public static final int size = 50;
	
	public static void render(int x, int y, int type){
		glPushMatrix();{
			// Set color, translation (location), rotation & texture.
			if(type == GRASS){
				glColor3f(0.43f, 0.81f, 0.16f);
			}else return;
			glTranslatef(x, y, 0);
			glRotatef(0, 0, 0, 1);

			// Draw the points to form the shape.
			glBegin(GL_QUADS);{
				glVertex2f(0,0);
				glVertex2f(size,0);
				glVertex2f(size,size);
				glVertex2f(0,size);
			}glEnd();
		}glPopMatrix();
	}
}