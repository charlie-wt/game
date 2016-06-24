import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class Main {
	private static Game game;

	public static void main(String[] args){
		init();

		while(!Display.isCloseRequested()){
			gameLoop();
		}

		cleanup();
	}

	public static void init(){
		// Display
			try {
				Display.setDisplayMode(new DisplayMode(1200, 600));
				Display.create();
				Display.setVSyncEnabled(true);
				Keyboard.create();
				Mouse.create();
			} catch (LWJGLException e) {e.printStackTrace();}

		// OpenGL
			// Basics of clearing/preparing the screen for 2D. 
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);		/* Orthographic projection, from 0 to window size on x and y and nothing on z axis. */
			glMatrixMode(GL_MODELVIEW);
			glClearColor(0f, 0f, 0f, 1);
			glDisable(GL_DEPTH_TEST);
			glEnable(GL_TEXTURE_2D);
			// Enabling alpha blending, for transparency in textures.
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		// Create Game
		game = new Game();
	}

	public static void gameLoop(){
		getInput();
		update();
		render();
	}

	public static void getInput(){
		game.getInput();
	}

	public static void update(){
		game.update();
	}

	public static void render(){
		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();

		game.render();

		Display.update();
		Display.sync(60);
	}

	public static void cleanup(){
		Keyboard.destroy();
		Mouse.destroy();
		Display.destroy();
	}
}