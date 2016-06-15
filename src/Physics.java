import java.awt.Rectangle;

public class Physics {
	public static int getCollisionX(Entity entity, Level level){
	// Gets the horizontal distance that the player may be intersecting the terrain by, after the next position update.
		int px = (int)(entity.getX() + entity.getVX());					// A preemptive measure of the player's x coordinate, after the next update.
		int py = (int)(entity.getY());									// The player's current y coordinate.
		int pw = entity.getW();
		int ph = entity.getH();
		int ts = Terrain.size;											// The size of each terrain tile.
		int pTileX = (px - (px%ts))/ts;									// The leftmost tile occupied by the player.
		int pTileY = (py - (py%ts))/ts;									// The bottommost tile occupied by the player.
		int pTileW = ((px+pw) - ((px+pw)%ts))/ts - pTileX;				// The width of the player, in tiles.
		int pTileH = ((py+ph) - ((py+ph)%ts))/ts - pTileY;				// The height of the player, in tiles.
		int right = (pTileX + pTileW > 23 ? 23 : pTileX + pTileW);		// The rightmost tile occupied by the player.
		int top = (pTileY + pTileH > 11 ? 11 : pTileY + pTileH);		// The topmost tile occupied by the player.

		for(int y=pTileY; y<=top; y++){
			for(int x=pTileX; x<=right; x++){
				if( level.getTerrain()[11-y][x] != Terrain.BACKGROUND ){
					
					if(entity.getClass().getName().equals("Player")){
						Player player = (Player)entity;
						if( level.getTerrain()[11-y][x] == Terrain.SPIKES ){
							player.die();
						}else if( level.getTerrain()[11-y][x] == Terrain.GOAL ){
							// TODO - PROBLEM WITH THIS METHOD (INSTEAD OF EXCEPTION): CARRIES ON NORMAL CHECKS EVEN IF DEAD/WON - CAN RESULT IN WINNING LEVEL TWICE IF COLLIDING ON X AND Y.
							// TODO - FOR SOME REASON DOESN'T RESET PLAYER'S COORDINATES PROPERLY IF COLLIDING HORIZONTALLY.
							player.win();
						}
					}
					
					boolean isWithinY = ((y*ts > py + 1 && y*ts < py+ph - 1) || ((y+1)*ts > py + 1 && (y+1)*ts < py+ph - 1));
					
					if( entity.getVX() > 0    && isWithinY ){
						return ((px+pw) - x*ts)*-1;
					}
					else if( entity.getVX() < 0    && isWithinY ){
						return (x+1)*ts - px;
					}
				}
			}
		}
		return 0;
	}
	
	public static int getCollisionY(Entity entity, Level level){
	// Gets the vertical distance that the player may be intersecting the terrain by, after the next position update.
		int px = (int)(entity.getX());									// A preemptive measure of the player's x coordinate, after the next update.
		int py = (int)(entity.getY() + entity.getVY());					// A preemptive measure of the player's y coordinate, after the next update.
		int pw = entity.getW();
		int ph = entity.getH();
		int ts = Terrain.size;											// The size of each terrain tile.
		int pTileX = (px - (px%ts))/ts;									// The leftmost tile occupied by the player.
		int pTileY = (py - (py%ts))/ts;									// The bottommost tile occupied by the player.
		int pTileW = ((px+pw) - ((px+pw)%ts))/ts - pTileX;				// The width of the player, in tiles.
		int pTileH = ((py+ph) - ((py+ph)%ts))/ts - pTileY;				// The height of the player, in tiles.
		int right = (pTileX + pTileW > 23 ? 23 : pTileX + pTileW);		// The rightmost tile occupied by the player.
		int top = (pTileY + pTileH > 11 ? 11 : pTileY + pTileH);		// The topmost tile occupied by the player.

		for(int y=pTileY; y<=top; y++){
			for(int x=pTileX; x<=right; x++){
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
					
					boolean isWithinX = ((x*ts > px && x*ts < px+pw) || ((x+1)*ts > px + 1 && (x+1)*ts < px+pw + 1));
					
					if( entity.getVY() > 0 && isWithinX ){
						return ((py+ph) - y*ts)*-1;
					}
					else if( entity.getVY() < 0 && isWithinX ){
						return (y+1)*ts - py;
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