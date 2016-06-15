
public class Enemy extends Entity {
	Game game;
	
	public Enemy(Level level, Game game, int x, int y){
		this.x = x;
		this.y = y;
		this.w = 50;
		this.h = 50;
		this.vx = 0;
		this.vy = 0;
		this.walkspeed = 7;
		this.jumpspeed = 10;
		this.texture = Game.loadTexture("braidenemstand64");
		this.level = level;
		this.game = game;
		this.vx = walkspeed;
	}
	
	public void update(){
		super.update();
		
		if(touchingEdge(LEFT) || touchingEdge(RIGHT)){
			System.out.println("TE");
			swapDir();
		}
	}
	
	public void swapDir(){
		vx *= -1;
		
		if(facing == LEFT){facing = RIGHT;}
		else{facing = LEFT;}
	}
}