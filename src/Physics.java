public class Physics {
	public static int getCollisionX(Player player, Level level){
	// Gets the horizontal distance that the player may be intersecting the terrain by, after the next position update.
		int px = (int)(player.getX() + player.getVX());					// A preemptive measure of the player's x coordinate, after the next update.
		int py = (int)(player.getY());									// The player's current y coordinate.
		int pw = player.getW();
		int ph = player.getH();
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
					boolean isWithinPlayerY = ((y*ts > py + 1 && y*ts < py+ph + 1) || ((y+1)*ts > py + 1 && (y+1)*ts < py+ph + 1));
					
					if( player.getVX() > 0    && isWithinPlayerY ){
						return ((px+pw) - x*ts)*-1;
					}
					else if( player.getVX() < 0    && isWithinPlayerY ){
						return (x+1)*ts - px;
					}
				}
			}
		}
		return 0;
	}
	
	public static int getCollisionY(Player player, Level level){
	// Gets the vertical distance that the player may be intersecting the terrain by, after the next position update.
		int px = (int)(player.getX());									// A preemptive measure of the player's x coordinate, after the next update.
		int py = (int)(player.getY() + player.getVY());					// A preemptive measure of the player's y coordinate, after the next update.
		int pw = player.getW();
		int ph = player.getH();
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
					boolean isWithinPlayerX = ((x*ts > px + 1 && x*ts < px+pw - 1) || ((x+1)*ts > px + 1 && (x+1)*ts < px+pw + 1));
					
					if( player.getVY() > 0 && isWithinPlayerX ){
						return ((py+ph) - y*ts)*-1;
					}
					else if( player.getVY() < 0 && isWithinPlayerX ){
						return (y+1)*ts - py;
					}
				}
			}
		}
		return 0;
	}
}