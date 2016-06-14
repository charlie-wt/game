import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class Player {
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;

	private int x, y;
	private int w = 50, h = 87;
	private float vy = 0, vx = 0;
	private int walkspeed = 7, jumpspeed = 10;
	private float gravity = 0.5f;
	private int facing = RIGHT;
	private Texture texture;
	private Level level;
	private Game game;
	public boolean jumpFlag = false;

	public Player(Level level, Game game){
		this.level = level;
		this.x = level.getStartX();
		this.y = level.getStartY();
		this.texture = Game.loadTexture("braidstandsmall");
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
		// Gravity, collisions and updating position based on velocity.
		Display.setTitle("X: " + x + "   y: " + y);
		if(touchingEdge(DOWN)){
			vy = 0;
			y = 0;
		}else{
			vy -= gravity;
		}
		
		x += vx + Physics.getCollisionX(this, level);
		y += vy + Physics.getCollisionY(this, level);
		if(Physics.getCollisionY(this, level) > 0){vy = 0; jumpFlag = true;}else{jumpFlag = false;}
	}

	public void render(){
	// Draws the player sprite.
		int xpad = texture.getImageWidth() - w;			// Is the difference between the texture image's size, and the actual size of the player as interpreted by physics (since slick only likes power of two textures).
		
		glPushMatrix();{
			// Set color, translation (location), rotation & texture.
			glColor3f(1f, 1f, 1f);
			glTranslatef(x-(xpad/2), y, 0);
			glRotatef(0, 0, 0, 1);
			texture.bind();

			// Draw the points to form the shape, with appropriate texture points.
			// Slick texture coordinates go top to bottom, OpenGL coordinates go bottom to top, so must flip.
			glBegin(GL_QUADS);{
				if(facing == RIGHT){glTexCoord2f(0,texture.getHeight());}
				else{glTexCoord2f(texture.getWidth(),texture.getHeight());}
				glVertex2f(0,0);

				if(facing == RIGHT){glTexCoord2f(texture.getWidth(),texture.getHeight());}
				else{glTexCoord2f(0,texture.getHeight());}
				glVertex2f(texture.getImageWidth(),0);

				if(facing == RIGHT){glTexCoord2f(texture.getWidth(),0);}
				else{glTexCoord2f(0,0);}
				glVertex2f(texture.getImageWidth(),texture.getImageHeight());

				if(facing == RIGHT){glTexCoord2f(0,0);}
				else{glTexCoord2f(texture.getWidth(),0);}
				glVertex2f(0,texture.getImageHeight());
			}glEnd();
		}glPopMatrix();
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