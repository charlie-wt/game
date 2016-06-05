import org.lwjgl.opengl.Display;

public class Physics {
	public static final int NONE = 0, TOP = 1, LEFT = 2, BOTTOM = 3, RIGHT = 4, INSIDE = 5;
	public static final int VERTICAL = 6, HORIZONTAL = 7;
	
	public static boolean isColliding(Player player, Level level){
	// TODO - DOESN'T WORK PROPERLY - WILL RETURN TRUE IF YOU'RE 1 BLOCK AWAY FROM COLLISION.
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
	
	public static int getCollision(Player player, Level level){
		int pTileX = (player.getX() - (player.getX()%Terrain.size))/Terrain.size;
		int pTileY = (player.getY() - (player.getY()%Terrain.size))/Terrain.size;
		int pTileW = ((player.getX()+player.getW()) - ((player.getX()+player.getW())%Terrain.size))/Terrain.size - pTileX + 1;
		int pTileH = ((player.getY()+player.getH()) - ((player.getY()+player.getH())%Terrain.size))/Terrain.size - pTileY + 1;
		int right = (pTileX + pTileW > 23 ? 23 : pTileX + pTileW);
		int top = (pTileY + pTileH > 11 ? 11 : pTileY + pTileH);
//		Display.setTitle("X: " + pTileX + "   Y: " + pTileY + "   W: " + pTileW + "   H: " + pTileH);
		
		for(int y=/*(pTileY==0?0:pTileY-1)*/pTileY;y<=top;y++){
			for(int x=/*(pTileX==0?0:pTileX-1)*/pTileX;x<=right;x++){
				int block = level.getTerrain()[11-y][x];
				if (block != Terrain.BACKGROUND){
					if(y == pTileY){return HORIZONTAL;}
					else if(x == pTileX){return VERTICAL;}
					else {return INSIDE;}
					/*if(y > pTileY){System.out.println("TOP"); return TOP;}
					if(y < pTileY){System.out.println("BOTTOM"); return BOTTOM;}
					if(x > pTileX){System.out.println("LEFT"); return LEFT;}
					if(x < pTileX){System.out.println("RIGHT"); return RIGHT;}
					else {System.out.println("INSIDE"); return INSIDE;}*/
				}
			}
		}
		
		/*System.out.println("None");*/
		return NONE;
	}
	
	public static boolean collidingVert(Player player, Level level){
		int pTileX = (player.getX() - (player.getX()%Terrain.size))/Terrain.size;
		int pTileY = (player.getY() - (player.getY()%Terrain.size))/Terrain.size;
		int pTileW = ((player.getX()+player.getW()) - ((player.getX()+player.getW())%Terrain.size))/Terrain.size - pTileX + 1;
		int pTileH = ((player.getY()+player.getH()) - ((player.getY()+player.getH())%Terrain.size))/Terrain.size - pTileY + 1;
		int right = (pTileX + pTileW > 23 ? 23 : pTileX + pTileW);
		int top = (pTileY + pTileH > 11 ? 11 : pTileY + pTileH);
		
		for(int y=pTileY;y<=top;y++){
			for(int x=pTileX;x<=right;x++){
				int block = level.getTerrain()[11-y][x];
				if (block != Terrain.BACKGROUND){
					if(x == pTileX){return true;}
				}
			}
		}
		
		return false;
	}
	
	public static boolean collidingHoriz(Player player, Level level){
		int pTileX = (player.getX() - (player.getX()%Terrain.size))/Terrain.size;
		int pTileY = (player.getY() - (player.getY()%Terrain.size))/Terrain.size;
		int pTileW = ((player.getX()+player.getW()) - ((player.getX()+player.getW())%Terrain.size))/Terrain.size - pTileX + 1;
		int pTileH = ((player.getY()+player.getH()) - ((player.getY()+player.getH())%Terrain.size))/Terrain.size - pTileY + 1;
		int right = (pTileX + pTileW > 23 ? 23 : pTileX + pTileW);
		int top = (pTileY + pTileH > 11 ? 11 : pTileY + pTileH);
		
		for(int y=pTileY;y<=top;y++){
			for(int x=pTileX;x<=right;x++){
				int block = level.getTerrain()[11-y][x];
				if (block != Terrain.BACKGROUND){
					if(y == pTileY){return true;}
				}
			}
		}
		
		return false;		
	}
}