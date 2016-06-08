public class Physics {
	public static final int NONE = 0, UP = 1, LEFT = 2, DOWN = 3, RIGHT = 4, INSIDE = 5;
	public static final int VERTICAL = 6, HORIZONTAL = 7;
	
	public static boolean getCollision(Player player, Level level){
	// TODO = COULD HANDLE THIS (IN PLAYER CLASS) VERY DIFFERENTLY, FOR POSSIBLY BETTER RESULTS:
		// ONLY USE THE BOOLEAN RETURN VALUE FROM THIS METHOD, THEN IN (POSSIBLY) update(), LOOK AT VELOCITY X AND Y:
			// IF VX > 0, MOVE LEFT TO THE NEAREST TILE BOUNDARY.
			// IF VX < 0, MOVE RIGHT TO THE NEAREST TILE BOUNDARY.
			// IF VY > 0, MOVE DOWN TO THE NEAREST TILE BOUNDARY.
			// IF VY < 0, MOVE UP TO THE NEAREST TILE BOUNDARY.
		// WOULD REDUCE COMPLEXITY OF THIS METHOD, AND (IF IT WORKS) REMOVE PROBLEMS WITH VELOCITY NOT MATCHING UP WITH STUFF.
		int px = (int)(player.getX() + player.getVX());								// A preemptive measure of the player's x coordinate, after the next update.
		int py = (int)(player.getY() + player.getVY());								// A preemptive measure of the player's y coordinate, after the next update.
		int pw = player.getW();
		int ph = player.getH();
		int ts = Terrain.size;														// The size of each terrain tile.
		int pTileX = (px - (px%ts))/ts;												// The leftmost tile occupied by the player.
		int pTileY = (py - (py%ts))/ts;												// The bottommost tile occupied by the player.
		int pTileW = ((px+pw) - ((px+pw)%ts))/ts - pTileX;							// The width of the player, in tiles.
		int pTileH = ((py+ph) - ((py+ph)%ts))/ts - pTileY;							// The height of the player, in tiles.
		int right = (pTileX + pTileW > 23 ? 23 : pTileX + pTileW);					// The rightmost tile occupied by the player.
		int top = (pTileY + pTileH > 11 ? 11 : pTileY + pTileH);					// The topmost tile occupied by the player.
		boolean isCollision = false;												// If there is any collision.

		for(int y=pTileY; y<=top; y++){
			for(int x=pTileX; x<=right; x++){

				if( level.getTerrain()[11-y][x] != Terrain.BACKGROUND ){			// If the tile is not a background tile.
					isCollision = true;
					
					// If the colliding tile is to the left of the player (but the same y coordinate).
					if( px > x*ts    && ((y*ts > py + 1 && y*ts < py+ph + 1) || ((y+1)*ts > py + 1 && (y+1)*ts < py+ph + 1)) ){
						System.out.println("L: " + x + "," + y + "   X: " + player.getX() + "   Y: " + player.getY());
						player.leftCol = true;
					}else{player.leftCol = false;}

					// If the colliding tile is to the right of the player (but the same y coordinate).
					if( px < x*ts    && ((y*ts > py + 1 && y*ts < py+ph + 1) || ((y+1)*ts > py + 1 && (y+1)*ts < py+ph + 1)) ){
						System.out.println("R: " + x + "," + y + "   X: " + player.getX() + "   Y: " + player.getY());
						player.rightCol = true;
					}else{player.rightCol = false;}
					
					// If the colliding tile is above the player.
					if( py+ph > y*ts && py+ph <= (y+1)*ts   && ((x*ts > px - 1 && x*ts < px+pw + 1) || ((x+1)*ts > px - 1 && (x+1)*ts < px+pw + 1)) ){
						System.out.println("U: " + x + "," + y + "   X: " + player.getX() + "   Y: " + player.getY());
						player.upCol = true;
					}else{player.upCol = false;}
					
					// If the colliding tile is below the player.
					if( py <= (y+1)*ts ){
//						System.out.println("D: " + x + "," + y + "   X: " + player.getX() + "   Y: " + player.getY());
						player.downCol = true;
					}else{player.downCol = false;}
					
					/*player.inside = true;*/
					/*if( !player.upCol && !player.downCol && !player.leftCol && !player.rightCol ){player.inside = true;}*/
				}
			}
		}
		
		if(!isCollision){player.leftCol = false; player.rightCol = false; player.upCol = false; player.downCol = false; player.inside = false;}
//		System.out.println("L: " + pTileX + "   R: " + right + "   B: " + pTileY + "   T: " + top + "     X: " + player.getX() + "   Y: " + player.getY());
		return isCollision;
	}
	
	public static int getCollisionX(Player player, Level level){
	// TODO - STILL RESPONDS TO IF YOU'RE COLLIDING VERTICALLY (SPECIFICALLY UPWARD) BUT HAPPEN TO HAVE HORIZONTAL VELOCITY.
		int px = (int)(player.getX() + player.getVX());								// A preemptive measure of the player's x coordinate, after the next update.
		int py = (int)(player.getY()/* + player.getVY()*/);							// A preemptive measure of the player's y coordinate, after the next update.
		int pw = player.getW();
		int ph = player.getH();
		int ts = Terrain.size;														// The size of each terrain tile.
		int pTileX = (px - (px%ts))/ts;												// The leftmost tile occupied by the player.
		int pTileY = (py - (py%ts))/ts;												// The bottommost tile occupied by the player.
		int pTileW = ((px+pw) - ((px+pw)%ts))/ts - pTileX;							// The width of the player, in tiles.
		int pTileH = ((py+ph) - ((py+ph)%ts))/ts - pTileY;							// The height of the player, in tiles.
		int right = (pTileX + pTileW > 23 ? 23 : pTileX + pTileW);					// The rightmost tile occupied by the player.
		int top = (pTileY + pTileH > 11 ? 11 : pTileY + pTileH);					// The topmost tile occupied by the player.

		for(int y=pTileY; y<=top; y++){
			for(int x=pTileX; x<=right; x++){
				if( level.getTerrain()[11-y][x] != Terrain.BACKGROUND ){			// If the tile is not a background tile.
					boolean isWithinPlayerY = ((y*ts > py + 1 && y*ts < py+ph + 1) || ((y+1)*ts > py + 1 && (y+1)*ts < py+ph + 1));
					
					if( player.getVX() > 0    && isWithinPlayerY ){
						System.out.println("R: " + ((px+pw) - x*ts));
						return ((px+pw) - x*ts)*-1;
					}
					else if( player.getVX() < 0    && isWithinPlayerY ){
						System.out.println("L: " + ((x+1)*ts - px));
						return (x+1)*ts - px;
					}
				}
			}
		}
		return 0;
	}
	
	public static int getCollisionY(Player player, Level level){
		int px = (int)(player.getX()/* + player.getVX()*/);							// A preemptive measure of the player's x coordinate, after the next update.
		int py = (int)(player.getY() + player.getVY());								// A preemptive measure of the player's y coordinate, after the next update.
		int pw = player.getW();
		int ph = player.getH();
		int ts = Terrain.size;														// The size of each terrain tile.
		int pTileX = (px - (px%ts))/ts;												// The leftmost tile occupied by the player.
		int pTileY = (py - (py%ts))/ts;												// The bottommost tile occupied by the player.
		int pTileW = ((px+pw) - ((px+pw)%ts))/ts - pTileX;							// The width of the player, in tiles.
		int pTileH = ((py+ph) - ((py+ph)%ts))/ts - pTileY;							// The height of the player, in tiles.
		int right = (pTileX + pTileW > 23 ? 23 : pTileX + pTileW);					// The rightmost tile occupied by the player.
		int top = (pTileY + pTileH > 11 ? 11 : pTileY + pTileH);					// The topmost tile occupied by the player.

		for(int y=pTileY; y<=top; y++){
			for(int x=pTileX; x<=right; x++){
				if( level.getTerrain()[11-y][x] != Terrain.BACKGROUND ){			// If the tile is not a background tile.
					boolean isWithinPlayerX = ((x*ts > px + 1 && x*ts < px+pw - 1) || ((x+1)*ts > px + 1 && (x+1)*ts < px+pw + 1));
					
					if( player.getVY() > 0 && isWithinPlayerX ){
						System.out.println("U: " + ((py+ph) - y*ts));
						return ((py+ph) - y*ts)*-1;
					}
					else if( player.getVY() < 0 && isWithinPlayerX ){
//						System.out.println("D: " + ((y+1)*ts - py));
						return (y+1)*ts - py;
					}
				}
			}
		}
		return 0;
	}
}