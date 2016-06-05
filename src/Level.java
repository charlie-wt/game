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
	}
	
	public int[][] getTerrain(){return terrain;}
}