import java.awt.Rectangle;

public class Physics {
	public static int getCollisionX(Entity entity, Level level){
	// Gets the horizontal distance that the entity may be intersecting the terrain by, after the next position update.
		int ex = (int)(entity.getX() + entity.getVX());
		int ey = (int)(entity.getY());
		int ew = entity.getW();
		int eh = entity.getH();
		int ts = Terrain.size;										// The size of each terrain tile.
		int left = (ex - (ex%ts))/ts;								// The leftmost tile occupied by the entity.
		int bottom = (ey - (ey%ts))/ts;								// The bottommost tile occupied by the entity.
		int width = ((ex+ew) - ((ex+ew)%ts))/ts - left;				// The width of the entity, in tiles.
		int height = ((ey+eh) - ((ey+eh)%ts))/ts - bottom;			// The height of the entity, in tiles.
		int right = (left + width > 23 ? 23 : left + width);		// The rightmost tile occupied by the entity.
		int top = (bottom + height > 11 ? 11 : bottom + height);	// The topmost tile occupied by the entity.

		for(int y=bottom; y<=top; y++){
			for(int x=left; x<=right; x++){
				if( level.getTerrain()[11-y][x] != Terrain.BACKGROUND ){
					
					if(entity.getClass().getName().equals("Player")){
						Player player = (Player)entity;
						if( level.getTerrain()[11-y][x] == Terrain.SPIKES ){
							player.die();
							return 0;
						}else if( level.getTerrain()[11-y][x] == Terrain.GOAL ){
							// TODO - PROBLEM WITH THIS METHOD (INSTEAD OF EXCEPTION): CARRIES ON NORMAL CHECKS EVEN IF DEAD/WON - CAN RESULT IN WINNING LEVEL TWICE IF COLLIDING ON X AND Y.
							// TODO - FOR SOME REASON DOESN'T RESET PLAYER'S COORDINATES PROPERLY IF COLLIDING HORIZONTALLY.
							player.win();
							return 0;
						}
					}
					
					
					if( entity.getVX() > 0 ){
						return ((ex+ew) - x*ts)*-1;
					}
					else if( entity.getVX() < 0 ){
						return (x+1)*ts - ex;
					}
				}
			}
		}
		return 0;
	}
	
	public static int getCollisionY(Entity entity, Level level){
	// Gets the vertical distance that the entity may be intersecting the terrain by, after the next position update.
		int ex = (int)(entity.getX());
		int ey = (int)(entity.getY() + entity.getVY());
		int ew = entity.getW();
		int eh = entity.getH();
		int ts = Terrain.size;										// The size of each terrain tile.
		int left = (ex - (ex%ts))/ts;								// The leftmost tile occupied by the entity.
		int bottom = (ey - (ey%ts))/ts;								// The bottommost tile occupied by the entity.
		int width = ((ex+ew) - ((ex+ew)%ts))/ts - left;				// The width of the entity, in tiles.
		int height = ((ey+eh) - ((ey+eh)%ts))/ts - bottom;			// The height of the entity, in tiles.
		int right = (left + width > 23 ? 23 : left + width);		// The rightmost tile occupied by the entity.
		int top = (bottom + height > 11 ? 11 : bottom + height);	// The topmost tile occupied by the entity.

		for(int y=bottom; y<=top; y++){
			for(int x=left; x<=right; x++){
				if( level.getTerrain()[11-y][x] != Terrain.BACKGROUND ){
					
					if(entity.getClass().getName().equals("Player")){
						Player player = (Player)entity;
						if( level.getTerrain()[11-y][x] == Terrain.SPIKES ){
							player.die();
						}else if( level.getTerrain()[11-y][x] == Terrain.GOAL ){
							// TODO - PROBLEM WITH THIS METHOD (INSTEAD OF EXCEPTION): CARRIES ON NORMAL CHECKS EVEN IF DEAD/WON - CAN RESULT IN WINNING LEVEL TWICE IF COLLIDING ON X AND Y.
							player.win();
						}
					}
					
					boolean isWithinX = ((x*ts > ex && x*ts < ex+ew) || ((x+1)*ts > ex + 1 && (x+1)*ts < ex+ew + 1));
					
					if( entity.getVY() > 0 && isWithinX ){
						return ((ey+eh) - y*ts)*-1;
					}
					else if( entity.getVY() < 0 && isWithinX ){
						return (y+1)*ts - ey;
					}
				}
			}
		}
		return 0;
	}
	
	public static boolean touchingEntity(Entity e1, Entity e2){
		Rectangle r1 = new Rectangle(e1.getX(),e1.getY(),e1.getW(),e1.getH());
		Rectangle r2 = new Rectangle(e2.getX(),e2.getY(),e2.getW(),e2.getH());
		return r1.intersects(r2);
	}
}