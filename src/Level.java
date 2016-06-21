import static org.lwjgl.opengl.GL11.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class Level {
//	private List<List<Integer>> terrain = new ArrayList<List <Integer>>(12);
	private int[][] terrain;
	private ArrayList<Entity> entities;
	Texture background = Game.loadTexture("sky");
	private String name;
	private int startx, starty;
	public ArrayList<Entity> startentities;
	
	public Level(int[][] terrain/*List<List<Integer>> terrain*/, String name, int startx, int starty){
		this.terrain = terrain;
		this.name = name;
		this.startx = startx;
		this.starty = starty;
		this.entities = new ArrayList<Entity>();
		this.startentities = new ArrayList<Entity>();
		for(Entity e : entities){
			startentities.add(e);
		}
	}
	
	public Level(String name, int startx, int starty){
//		List<List<Integer>> tr = new ArrayList<List<Integer>>(12);
		int[][] tr = new int[12][24];

		//Initialize terrain 2D list with null values.
/*		for(int y=0;y<12;y++){
			terrain.add(new ArrayList<Integer>(24));
			for(int x=0;x<24;x++){
				terrain.get(y).add(null);
			}
		}*/
		
		for (int y=0; y<12;y++){
			for(int x=0;x<24;x++){
				if(y == 11){
//					tr.get(y).set(x, Terrain.GRASS);
					tr[y][x] = Terrain.GRASS;
				}else{
//					tr.get(y).set(x, Terrain.BACKGROUND);
					tr[y][x] = Terrain.BACKGROUND;
				}
			}
		}
		
		this.terrain = tr;
		this.name = name;
		this.startx = startx;
		this.starty = starty;
		this.entities = new ArrayList<Entity>();
		this.startentities = new ArrayList<Entity>();
		for(Entity e : entities){
			startentities.add(e);
		}
	}
	
	public void render(){
		for (int y=0; y</*terrain.size()*/terrain.length;y++){
			for(int x=0;x</*terrain.get(y).size()*/terrain[y].length;x++){
				Terrain.render(x*50, Display.getHeight() - (y+1)*50, terrain[y][x]/*terrain.get(y).get(x)*/);
			}
		}
	}
	
	public static Level fromFile(String filename){
	// Reads a level in from a file and converts it to an object for use.
		File file = new File("lvl/" + filename);
		int startx, starty;
		int[][] terrain;
		// Initialize terrain 2D list with empty values.
/*		List <List <Integer>> terrain = new ArrayList <List <Integer>>(12);
		for(int y=0;y<12;y++){
			terrain.add(new ArrayList<Integer>(24));
			for(int x=0;x<24;x++){
				terrain.get(y).add(null);
			}
		}*/
		
		try {
			// Set up reader, read player start coords.
			BufferedReader br = new BufferedReader(new FileReader(file));
			startx = Integer.parseInt(br.readLine());
			starty = Integer.parseInt(br.readLine());
			String line = br.readLine();
			terrain = new int[12][line.length()];
			
			// Read terrain info.
			for(int y=0;line != null && !line.startsWith("enem:");y++){
				for(int x=0;x<line.length();x++){
//					terrain.get(y).set(x, Character.getNumericValue(line.charAt(x)));
					terrain[y][x] = Character.getNumericValue(line.charAt(x));
				}
				line = br.readLine();
			}
			Level level = new Level(terrain, filename, startx, starty);
			
			// Read enemy info.
			while(line != null){
				String[] info = line.substring(line.indexOf(':') + 1).split(" ");
				int x = Integer.parseInt(info[0]);
				int y = Integer.parseInt(info[1]);
				int facing = (info[2] == "l" ? Entity.LEFT : Entity.RIGHT);
				Enemy enemy = new Enemy(level, x, y, facing);
				level.addEntity(enemy);
				
				line = br.readLine();
			}
			
			br.close();
			return level;
		} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		return null;
	}
	
	public void drawBackground(){
		glPushMatrix();{
			// Set color, translation (location), rotation & texture.
			background.bind();
			glColor3f(1f, 1f, 1f);
			glTranslatef(0, 0, 0);
			glRotatef(0, 0, 0, 1);

			// Draw the points to form the shape.
			glBegin(GL_QUADS);{
				glTexCoord2f(0,background.getHeight());
				glVertex2f(0,0);

				glTexCoord2f(background.getWidth(),background.getHeight());
				glVertex2f(background.getImageWidth(),0);

				glTexCoord2f(background.getWidth(),0);
				glVertex2f(background.getImageWidth(),background.getImageHeight());

				glTexCoord2f(0,0);
				glVertex2f(0,background.getImageHeight());
			}glEnd();
		}glPopMatrix();
	}
	
	public void displayGrid(){
	// Displays a red terrain grid.
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
	
	public void addEntity(Entity e){
		entities.add(e);
		startentities.add(e);
	}
	
	public void removeEntity(Entity e){
		if(entities.contains(e)){
			entities.remove(e);
		}
	}
	
	public void removeEntities(List<Entity> e){
		entities.removeAll(e);
	}
	
	public void resetEnemies(){
		for(Entity e : startentities){
			if (!entities.contains(e)){
				entities.add(e);
			}
		}
		for(Entity e : entities){
			if(e.getClass().getName().equals("Enemy")){
				Enemy enem = (Enemy)e;
				enem.reset();
			}
		}
	}

	public /*List <List <Integer>>*/ int[][] getTerrain(){return terrain;}
	public String getName(){return this.name;}
	public int getStartX(){return this.startx;}
	public int getStartY(){return this.starty;}
	public ArrayList<Entity> getEntities(){return entities;}
}