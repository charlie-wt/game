import java.awt.Rectangle;

import org.lwjgl.opengl.Display;

public class Physics {
	public static final int NONE = 0, UP = 1, LEFT = 2, DOWN = 3, RIGHT = 4, INSIDE = 5;
	public static final int VERTICAL = 6, HORIZONTAL = 7;
	
	public static boolean getCollision(Player player, Level level){
		int pTileX = (player.getX() - (player.getX()%Terrain.size))/Terrain.size;												// The leftmost tile occupied by the player.
		int pTileY = (player.getY() - (player.getY()%Terrain.size))/Terrain.size;												// The bottommost tile occupied by the player.
		int pTileW = ((player.getX()+player.getW()) - ((player.getX()+player.getW())%Terrain.size))/Terrain.size - pTileX;		// The width of the player, in tiles.
		int pTileH = ((player.getY()+player.getH()) - ((player.getY()+player.getH())%Terrain.size))/Terrain.size - pTileY;		// The height of the player, in tiles.
		int right = (pTileX + pTileW > 23 ? 23 : pTileX + pTileW);
		int top = (pTileY + pTileH > 11 ? 11 : pTileY + pTileH);
		boolean isTerrain = false, isCollision = false;

		for(int y=pTileY; y<=top; y++){
			for(int x=pTileX; x<=right; x++){

				if( level.getTerrain()[11-y][x] != Terrain.BACKGROUND ){																		// If the tile is not a background tile.
					isTerrain = true;
					Rectangle r1 = new Rectangle(player.getX(), player.getY(), player.getX() + player.getW(), player.getY() + player.getH());	// A (awt) rectangle representing the player.
					Rectangle r2 = new Rectangle(x*Terrain.size, y*Terrain.size, (x+1)*Terrain.size, (y+1)*Terrain.size);						// A (awt) rectangle representing the currently checked block.
					
					if(r1.intersects(r2)){
						isCollision = true;
						
						if( player.getX() > x*Terrain.size && (y*Terrain.size >= player.getY() || (y+1)*Terrain.size >= player.getY()+player.getH()) ){
							player.leftCol = true;
						}else{player.leftCol = false;}
						
						if( player.getX() < (x)*Terrain.size && (y*Terrain.size >= player.getY() || (y+1)*Terrain.size >= player.getY()+player.getH()) ){
							player.rightCol = true;
						}else{player.rightCol = false;}
						
						if( player.getY()+player.getH() > y*Terrain.size && player.getY()+player.getH() <= (y+1)*Terrain.size ){
							player.upCol = true;
						}else{player.upCol = false;}
						
						if( player.getY() <= (y+1)*Terrain.size ){
							player.downCol = true;
						}else{player.downCol = false;}
					}else{player.leftCol = false; player.rightCol = false; player.upCol = false; player.downCol = false;}
				}
			}
		}
		
		if(!isTerrain){player.leftCol = false; player.rightCol = false; player.upCol = false; player.downCol = false;}
		
		return isCollision;
	}
}