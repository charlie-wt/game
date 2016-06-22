import java.io.File;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

public class Player extends Entity {
	private static final int walklength = 6, walkdelay = 4;
	private int texnum = 0;
	private Texture standtexture, jumptexture, airhangtexture, falltexture;
	private ArrayList<Texture> walktextures;
	private File winSound, deathSound, killSound;

	public Player(Level level, Game game){
		this.x = level.getStartX();
		this.y = level.getStartY();
		this.w = 50;
		this.h = 87;
		this.vx = 0;
		this.vy = 0;
		this.walkspeed = 7;
		this.jumpspeed = 10;
		this.standtexture = Game.loadTexture("playerstand");
		this.texture = standtexture;
		this.level = level;
		this.game = game;
		this.deathSound = findSoundFile("death");
		this.winSound = findSoundFile("win");
		this.killSound = findSoundFile("kill");
		
		this.walktextures = new ArrayList<Texture>();
		for(int i=0;i<walklength;i++){
			walktextures.add(Game.loadTexture("anim/playerwalk" + i));
		}
		this.jumptexture = Game.loadTexture("anim/playerjump");
		this.airhangtexture = Game.loadTexture("anim/playerairhang");
		this.falltexture = Game.loadTexture("anim/playerfall");
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
	
	public void update(){

		super.update();
		
		try {
			updatePos();
		} catch (DeadException e) {
			die();
		} catch (WinException e) {
			win();
		}	

		animateTexture();
	}
	
	public void animateTexture(){
	// Chooses texture based on current position, velocity.
		if(!Physics.touchingFloor(this, level) && !touchingEdge(DOWN)){
			if(vy > 2){
				texture = jumptexture;
			}else if(vy > -2){
				texture = airhangtexture;
			}else{
				texture = falltexture;
			}
			texnum = 0;
		}else if(vx != 0 ){
			if(texnum % walkdelay == 0){
				texture = walktextures.get(texnum/walkdelay);
			}
			
			if(texnum >= (walklength - 1)*walkdelay){
				texnum = 0;
			}else{
				texnum++;
			}
			
		}else{
			texture = standtexture;
			texnum = 0;
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
		playSound(deathSound);
		x = level.getStartX();
		y = level.getStartY();
		game.resetCamera();
		vx = 0;
		vy = 0;
		effectivevx = 0;
		effectivevy = 0;
		jumpFlag = false;
		texture = standtexture;
		level.resetEnemies();
	}
	
	public void win(){
	// If the player touches the goal tile.
		playSound(winSound);
		game.loadNextLevel();
		x = level.getStartX();
		y = level.getStartY();
		vx = 0;
		vy = 0;
		effectivevx = 0;
		effectivevy = 0;
		jumpFlag = false;
		texture = standtexture;
	}
	
	public void kill(){
	// Is the reaction to killing; doesn't actually kill anything.
		playSound(killSound);
		vy = jumpspeed;
	}
	
	private boolean pressJump() { return Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_SPACE); }
	private boolean pressLeft() { return Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT); }
	private boolean pressRight(){ return Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT); }
}