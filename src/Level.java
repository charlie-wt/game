import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;

public class Level {
	// TODO - Could possibly make this an array of Terrains, alter terrain to have type, x & y members.
	// Also, might change the way size is handled if you do scrolling levels.
	private int[][] terrain = new int[12][24];
	
	public Level(int[][] terrain){
		this.terrain = terrain;
	}
	
	public Level(){
		int[][] tr = new int[12][24];
		
		for (int y=0; y<12;y++){
			for(int x=0;x<24;x++){
				if(y == 11){
					tr[y][x] = Terrain.GRASS;
					System.out.print(" G ");
				}else{
					tr[y][x] = Terrain.BACKGROUND;
					System.out.print(" - ");
				}
			}
			System.out.println();
		}
		
		this.terrain = tr;
	}
	
	public void render(){
		glDisable(GL_TEXTURE_2D);										// Must disable texturing or the terrain won't draw, for some reason (shouldn't need to do this once terrain is textured).
		for (int y=0; y<terrain.length;y++){
			for(int x=0;x<terrain[y].length;x++){
				Terrain.render(x*50, Display.getHeight() - (y+1)*50, terrain[y][x]);
			}
		}
		
//		displayGrid();
	}
	
	public void displayGrid(){
		for (int y=0; y<Display.getHeight();y+=50){
			glPushMatrix();{
				glColor3f(1f, 0f, 0f);
				glTranslatef(0, y, 0);
				glRotatef(0, 0, 0, 1);

				glBegin(GL_LINES);{
					glVertex2f(0,0);
					glVertex2f(Display.getWidth(),0);
				}glEnd();
			}glPopMatrix();
		}
		
		for (int x=0; x<Display.getWidth();x+=50){
			glPushMatrix();{
				glColor3f(1f, 0f, 0f);
				glTranslatef(x, 0, 0);
				glRotatef(0, 0, 0, 1);

				glBegin(GL_LINES);{
					glVertex2f(0,0);
					glVertex2f(0,Display.getHeight());
				}glEnd();
			}glPopMatrix();
		}
	}
	
	public int[][] getTerrain(){return terrain;}
}