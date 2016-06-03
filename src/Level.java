
public class Level {
	// TODO - Possibly make this an array of Terrains, alter terrain to have type, x & y members.
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
		for (int y=0; y<terrain.length;y++){
			for(int x=0;x<terrain[y].length;x++){
				Terrain.render(x, y, terrain[y][x]);
			}
		}
	}
}