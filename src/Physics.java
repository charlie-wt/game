import java.awt.Rectangle;

public class Physics {
	public static int getCollisionX(Entity entity, Level level){
	// Gets the horizontal distance that the entity may be intersecting the terrain by, after the next position update.
		int ex = (int)(entity.getX() + entity.getVX());					// A preemptive measure of the entity's x coordinate, after the next update.
		int ey = (int)(entity.getY());
		int ew = entity.getW();
		int eh = entity.getH();
		int ts = Terrain.size;											// The size of each terrain tile.
		int eTileX = (ex - (ex%ts))/ts;									// The leftmost tile occupied by the entity.
		int eTileY = (ey - (ey%ts))/ts;									// The bottommost tile occupied by the entity.
		int eTileW = ((ex+ew) - ((ex+ew)%ts))/ts - eTileX;				// The width of the entity, in tiles.
		int eTileH = ((ey+eh) - ((ey+eh)%ts))/ts - eTileY;				// The height of the entity, in tiles.
		int right = (eTileX + eTileW > 23 ? 23 : eTileX + eTileW);		// The rightmost tile occupied by the entity.
		int top = (eTileY + eTileH > 11 ? 11 : eTileY + eTileH);		// The topmost tile occupied by the entity.
		
/*		if(entity.getClass().getName().equals("Enemy")){
			Display.setTitle("L: " + pTileX + "   R: " + right + "   B: " + pTileY + "   T: " + top);
		}*/

		for(int y=eTileY; y<=top; y++){
			for(int x=eTileX; x<=right; x++){
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
					
//					boolean isWithinY = ((y*ts > ey + 1 && y*ts < ey+eh - 1) || ((y+1)*ts > ey + 1 && (y+1)*ts < ey+eh - 1));
					
					if( entity.getVX() > 0         /*&& isWithinY*/ ){
						return ((ex+ew) - x*ts)*-1;
					}
					else if( entity.getVX() < 0    /*&& isWithinY*/ ){
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
		int ey = (int)(entity.getY() + entity.getVY());					// A preemptive measure of the entity's y coordinate, after the next update.
		int ew = entity.getW();
		int eh = entity.getH();
		int ts = Terrain.size;											// The size of each terrain tile.
		int eTileX = (ex - (ex%ts))/ts;									// The leftmost tile occupied by the entity.
		int eTileY = (ey - (ey%ts))/ts;									// The bottommost tile occupied by the entity.
		int eTileW = ((ex+ew) - ((ex+ew)%ts))/ts - eTileX;				// The width of the entity, in tiles.
		int eTileH = ((ey+eh) - ((ey+eh)%ts))/ts - eTileY;				// The height of the entity, in tiles.
		int right = (eTileX + eTileW > 23 ? 23 : eTileX + eTileW);		// The rightmost tile occupied by the entity.
		int top = (eTileY + eTileH > 11 ? 11 : eTileY + eTileH);		// The topmost tile occupied by the entity.

		for(int y=eTileY; y<=top; y++){
			for(int x=eTileX; x<=right; x++){
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