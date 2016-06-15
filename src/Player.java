import org.lwjgl.input.Keyboard;

public class Player extends Entity {

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
	
	public void die(){
	// If the player touches spikes, or an enemy.
	// TODO - Sound effect or something, idk.
		System.out.println("Dead!");
		x = level.getStartX();
		y = level.getStartY();
		jumpFlag = false;
	}
	
	public void win(){
	// If the player touches the goal tile.
		game.loadNextLevel();
		x = level.getStartX();
		y = level.getStartY();
		jumpFlag = false;
	}
	
	private boolean pressJump(){ return Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_SPACE); }
	private boolean pressLeft(){ return Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT); }
	private boolean pressRight(){ return Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT); }
}