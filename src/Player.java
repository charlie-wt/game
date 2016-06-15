import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Player extends Entity {
	private Level level;
	private Game game;

	public Player(Level level, Game game){
		this.x = level.getStartX();
		this.y = level.getStartY();
		this.w = 50;
		this.h = 87;
		this.vx = 0;
		this.vy = 0;
		this.walkspeed = 7;
		this.jumpspeed = 10;
		this.texture = Game.loadTexture("braidstandsmall");
		this.level = level;
		this.game = game;
	}

	public void getInput(){
	// Key press -> character movement.
		if(pressJump()){
			move(UP);
		}
		if(pressLeft()){
			move(LEFT);
		}
		if(pressRight()){
			move(RIGHT);
		}
		if((!pressRight() && !pressLeft()) || (pressRight() && pressLeft())){
			vx = 0;
		}
	}

	public void move(int dir){
	// Updates location/velocity variables.
		switch(dir){
			case LEFT:	if(!touchingEdge(LEFT))           { vx = walkspeed*-1; facing = LEFT; }  else { vx=0; } break;
			case RIGHT:	if(!touchingEdge(RIGHT))          { vx = walkspeed;    facing = RIGHT; } else { vx=0; } break;
			case UP:	if(touchingEdge(DOWN) || jumpFlag){ vy = jumpspeed; } break;
		}
	}

	public boolean touchingEdge(int dir){
	// Returns true if the player is currently up against an edge of the stage (or direction is invalid).
		switch(dir){
			case LEFT:	return x <= 0;
			case RIGHT:	return x+w >= Display.getWidth();
			case UP:	return y+h >= Display.getHeight();
			case DOWN:	return y <= 0;
		}
		return true;
	}

	public void update(){
		// TODO - MOVE TO Entity, ADAPT FOR ENEMIES.
		// Gravity, collisions and updating position based on velocity.
		if(touchingEdge(DOWN)){
			vy = 0;
			y = 0;
		}else{
			vy -= gravity;
		}
		
		if(Physics.getCollisionY(this, level) > 0){vy = 0; jumpFlag = true;}else{jumpFlag = false;}
		x += vx + Physics.getCollisionX(this, level);
		y += vy + Physics.getCollisionY(this, level);
	}
	
	public void die(){
	// TODO - Sound effect or something, idk.
		System.out.println("Dead!");
		x = level.getStartX();
		y = level.getStartY();
	}
	
	public void win(){
		game.loadNextLevel();
		x = level.getStartX();
		y = level.getStartY();
	}
	
	private boolean pressJump(){ return Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_SPACE); }
	private boolean pressLeft(){ return Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT); }
	private boolean pressRight(){ return Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT); }
	
	public int getX(){return x;}
	public int getY(){return y;}
	public int getW(){return w;}
	public int getH(){return h;}
	public float getVX(){return vx;}
	public float getVY(){return vy;}
	public void setLevel(Level level){this.level = level;}
}