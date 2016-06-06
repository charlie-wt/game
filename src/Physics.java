import java.awt.Rectangle;

import org.lwjgl.opengl.Display;

public class Physics {
	public static final int NONE = 0, UP = 1, LEFT = 2, DOWN = 3, RIGHT = 4, INSIDE = 5;
	public static final int VERTICAL = 6, HORIZONTAL = 7;
	
	public static boolean isColliding(Player player, Level level){
	// TODO - DYSFUNCTIONAL - TO BE EVENTUALLY DELETED.
		int pTileX = (player.getX() - (player.getX()%Terrain.size))/Terrain.size;
		int pTileY = (player.getY() - (player.getY()%Terrain.size))/Terrain.size;
		Display.setTitle("X: " + pTileX + "   Y: " + pTileY);
		
		for(int y=(pTileY==0?0:pTileY-1);y<=pTileY+2;y++){
			for(int x=(pTileX==0?0:pTileX-1);x<=pTileX+1;x++){
				System.out.print("Checking (" + x + "," + y + ")");
				System.out.println(" against " + level.getTerrain()[y][x] + "...");
				if (level.getTerrain()[11-y][x] != Terrain.BACKGROUND){
					System.out.println("True");
					return true;
				}
			}
		}
		
		System.out.println("False");
		return false;
	}
	
	public static boolean getCollision(Player player, Level level){
	//TODO - FLAGS ARE NEVER SET TO FALSE - WILL LAST FOREVER.
		int pTileX = (player.getX() - (player.getX()%Terrain.size))/Terrain.size;
		int pTileY = (player.getY() - (player.getY()%Terrain.size))/Terrain.size;
		int pTileW = ((player.getX()+player.getW()) - ((player.getX()+player.getW())%Terrain.size))/Terrain.size - pTileX + 1;
		int pTileH = ((player.getY()+player.getH()) - ((player.getY()+player.getH())%Terrain.size))/Terrain.size - pTileY + 1;
		int left = (pTileX - 1 < 0 ? 0 : pTileX - 1);
		int right = (pTileX + pTileW > 23 ? 23 : pTileX + pTileW);
		int top = (pTileY + pTileH > 11 ? 11 : pTileY + pTileH);
		int bottom = (pTileY - 1 < 0 ? 0 : pTileY - 1);
		boolean isTerrain = false;

		for(int y=bottom;y<=top;y++){
			for(int x=left;x<=right;x++){

				if( level.getTerrain()[11-y][x] != Terrain.BACKGROUND){																			// If the tile is not a background tile.
					isTerrain = true;
					Rectangle r1 = new Rectangle(player.getX(), player.getY(), player.getX() + player.getW(), player.getY() + player.getH());	// A (awt) rectangle representing the player.
					Rectangle r2 = new Rectangle(x*Terrain.size, y*Terrain.size, (x+1)*Terrain.size, (y+1)*Terrain.size);						// A (awt) rectangle representing the currently checked block.
					
					if(r1.intersects(r2)){
						if(player.getX() < x*Terrain.size && player.getY() == y){
							System.out.println("Intersecting left at (" + x + "," + y + ").");
							player.leftCol = true;
						}else{player.leftCol = false;}
						if(player.getX()+player.getW() > (x+1)*Terrain.size && player.getY() == y){
							System.out.println("Intersecting right at (" + x + "," + y + ").");
							player.rightCol = true;
						}else{player.rightCol = false;}
						if(player.getY()+player.getH() > y*Terrain.size && player.getY()+player.getH() <= (y+1)*Terrain.size){
							System.out.println("Intersecting up at (" + x + "," + y + ").");
							player.upCol = true;
						}else{player.upCol = false;}
						if(player.getY() <= (y+1)*Terrain.size){
							System.out.println("Intersecting down at (" + x + "," + y + ").");
							player.downCol = true;
						}else{player.downCol = false;}
						return true;
					}else{player.leftCol = false; player.rightCol = false; player.upCol = false; player.downCol = false;}
				}
			}
		}
		
		if(!isTerrain){player.leftCol = false; player.rightCol = false; player.upCol = false; player.downCol = false;}
		
		return false;
	}
	
	public static boolean collidingVert(Player player, Level level){
		// TODO - DYSFUNCTIONAL - TO BE EVENTUALLY DELETED.
		int pTileX = (player.getX() - (player.getX()%Terrain.size))/Terrain.size;
		int pTileY = (player.getY() - (player.getY()%Terrain.size))/Terrain.size;
		int pTileW = ((player.getX()+player.getW()) - ((player.getX()+player.getW())%Terrain.size))/Terrain.size - pTileX + 1;
		int pTileH = ((player.getY()+player.getH()) - ((player.getY()+player.getH())%Terrain.size))/Terrain.size - pTileY + 1;
		int right = (pTileX + pTileW > 23 ? 23 : pTileX + pTileW);
		int top = (pTileY + pTileH > 11 ? 11 : pTileY + pTileH);
		
		for(int y=pTileY;y<=top;y++){
			for(int x=pTileX;x<=right;x++){
				int block = level.getTerrain()[11-y][x];
//				Display.setTitle("Checking if " + player.getY() + " > " + y*Terrain.size + " or if " + (player.getY() + player.getH()) + "<" + (y + 1)*Terrain.size + "...");
				if ( block != Terrain.BACKGROUND   &&   (player.getY() > y*Terrain.size   ||   (player.getY() + player.getH() < (y + 1)*Terrain.size)) ){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean collidingHoriz(Player player, Level level){
		// TODO - DYSFUNCTIONAL - TO BE EVENTUALLY DELETED.
		int pTileX = (player.getX() - (player.getX()%Terrain.size))/Terrain.size;
		int pTileY = (player.getY() - (player.getY()%Terrain.size))/Terrain.size + 1;
		int pTileW = ((player.getX()+player.getW()) - ((player.getX()+player.getW())%Terrain.size))/Terrain.size - pTileX + 1;
		int pTileH = ((player.getY()+player.getH()) - ((player.getY()+player.getH())%Terrain.size))/Terrain.size - pTileY + 1;
		int right = (pTileX + pTileW > 23 ? 23 : pTileX + pTileW);
		int top = (pTileY + pTileH > 11 ? 11 : pTileY + pTileH);
		
		for(int y=pTileY;y<=top;y++){
			for(int x=pTileX;x<=right;x++){

				if( level.getTerrain()[11-y][x] != Terrain.BACKGROUND){																			// If the tile is not a background tile.
					Rectangle r1 = new Rectangle(player.getX(), player.getY(), player.getX() + player.getW(), player.getY() + player.getH());	// A (awt) rectangle representing the player.
					Rectangle r2 = new Rectangle(x*Terrain.size, y*Terrain.size, (x+1)*Terrain.size, (y+1)*Terrain.size);						// A (awt) rectangle representing the currently checked block.
					
					if(r1.intersects(r2)){
						if((player.getX() > x*Terrain.size && player.getX() <= (x+1)*Terrain.size) || (player.getX()+player.getW() > x*Terrain.size && player.getX()+player.getW() <= (x+1)*Terrain.size)){
							// Extra check for if the intersection is horizontal, not vertical.
							System.out.println("Intersecting (" + x + "," + y + ").");
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
}