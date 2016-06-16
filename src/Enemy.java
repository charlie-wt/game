public class Enemy extends Entity {
	int startx, starty, startdir;
	
	public Enemy(Level level, int x, int y, int facing){
		this.startx = x;
		this.starty = y;
		this.startdir = facing;
		this.x = startx;
		this.y = starty;
		this.w = 50;
		this.h = 50;
		this.vy = 0;
		this.walkspeed = 4;
		this.jumpspeed = 10;
		this.texture = Game.loadTexture("braidenemstand64");
		this.facing = startdir;
		this.level = level;
		this.vx = walkspeed;
	}
	
	public Enemy(Level level, int x, int y){
		this(level, x, y, RIGHT);
	}
	
	public void update(){
		ai();
		super.update();
	}
	
	private void ai(){
	// Walk until you hit wall/edge of stage, then turn around. Will fall off things.
		if( touchingEdge(LEFT) || touchingEdge(RIGHT) || Physics.getCollisionX(this, level) != 0 ){
			swapDir();
		}
	}
	
	public void reset(){
	// Put back in start position/direction/velocity.
		x = startx;
		y = starty;
		vx = walkspeed;
		vy = 0;
		facing = startdir;
	}
	
	private void swapDir(){
		vx *= -1;
		
		if(facing == LEFT){facing = RIGHT;}
		else{facing = LEFT;}
	}
}