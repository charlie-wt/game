import java.awt.Rectangle;

public class Physics {
	public static int getCollisionX(Entity entity, Level level) throws DeadException, WinException {
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
//				int tile = level.getTerrain().get(11-y).get(x);
				int tile = level.getTerrain()[11-y][x];
				
				if( tile != Terrain.BACKGROUND ){
					if( entity.getClass().getName().equals("Player") ){
					// Winning or dying, for the player only.
						if( tile == Terrain.SPIKES ){
							throw new DeadException();
						}else if( tile == Terrain.GOAL ){
							throw new WinException();
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
	
	public static int getCollisionY(Entity entity, Level level) throws DeadException, WinException {
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
//				int tile = level.getTerrain().get(11-y).get(x);
				int tile = level.getTerrain()[11-y][x];
				
				if( tile != Terrain.BACKGROUND ){
					if( entity.getClass().getName().equals("Player") ){
					// Winning or dying, for the player only.
						if( tile == Terrain.SPIKES ){
							throw new DeadException();
						}else if( tile == Terrain.GOAL ){
							throw new WinException();
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
	
	public static boolean isStomping(Entity e1, Entity e2){
		int e1l = e1.getX();
		float e1b = e1.getY() + e1.getVY();
		int e1r = e1.getX()+e1.getW();
		
		int e2l = e2.getX();
		float e2b = e2.getY() + e2.getVY();
		int e2r = e2.getX()+e2.getW();
		float e2t = e2.getY() + e2.getVY()+e2.getH();
		
		boolean isWithinX = ((e1l > e2l && e1l < e2r) || (e1r > e2l + 1 && e1r < e2r + 1));
		
		return isWithinX && e1.getVY() < 0 && e1b < e2t && e1b > e2b;
	}
	
	public static boolean touchingFloor(Entity e, Level l){
		int ex = (int)(e.getX());
		int ey = (int)(e.getY() + e.getVY());
		int ew = e.getW();
		int ts = Terrain.size;
		int left = (ex - (ex%ts))/ts;
		int bottom = (ey - (ey%ts))/ts;
		int width = ((ex+ew) - ((ex+ew)%ts))/ts - left;
		int right = (left + width > 23 ? 23 : left + width);


		for(int y=(ey>=ts?bottom-1:bottom); y<=bottom; y++){
			for(int x=left; x<=right; x++){
				if( /*l.getTerrain().get(11-y).get(x)*/l.getTerrain()[11-y][x] != Terrain.BACKGROUND ){
					return e.getVY() <= 0 && ey <= (y+1)*ts && ey > y*ts;
				}
			}
		}
		return false;
	}
}