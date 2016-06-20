import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

public class Enemy extends Entity {
	private int startx, starty, startdir;
	private Texture standtexture, airhangtexture, falltexture;
	private ArrayList<Texture> walktextures;
	private int texnum = 0;
	private static final int walklength = 6, walkdelay = 9;
	
	public Enemy(Level level, int x, int y, int facing){
		this.startx = x;
		this.starty = y;
		this.startdir = facing;
		this.x = startx;
		this.y = starty;
		this.w = 50;
		this.h = 50;
		this.vy = 0;
		this.walkspeed = 2;
		this.jumpspeed = 10;
		this.standtexture = Game.loadTexture("enemystand");
		this.texture = standtexture;
		this.facing = startdir;
		this.level = level;
		this.vx = walkspeed;
		
		this.walktextures = new ArrayList<Texture>();
		for(int i=0;i<walklength;i++){
			walktextures.add(Game.loadTexture("anim/enemywalk" + i));
		}
		this.airhangtexture = Game.loadTexture("anim/enemyairhang");
		this.falltexture = Game.loadTexture("anim/enemyfall");
	}
	
	public Enemy(Level level, int x, int y){
		this(level, x, y, RIGHT);
	}
	
	public void update(){
		ai();	
		
		super.update();
		
		try {
			updatePos();
		} catch (DeadException | WinException e) {e.printStackTrace();}
		
		animateTexture();
	}
	
	private void ai(){
	// Walk until you hit wall/edge of stage, then turn around. Will fall off things.
		try {
			if( touchingEdge(LEFT) || touchingEdge(RIGHT) || Physics.getCollisionX(this, level) != 0 ){
				swapDir();
			}
		} catch (DeadException | WinException e) {e.printStackTrace();}
	}
	
	public void animateTexture(){
	// Chooses texture based on current position, velocity.
		if(!Physics.touchingFloor(this, level) && !touchingEdge(DOWN)){
			if(vy > -2){
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