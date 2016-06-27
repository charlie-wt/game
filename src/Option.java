import org.newdawn.slick.opengl.Texture;

public abstract class Option {
	protected Game game;
	protected String name;
	protected Texture tex;
	
	public Option (Game game, String name) {
		this.game = game;
		this.name = name;
		this.tex = Game.loadTexture("text/" + name);
	}
	
	public abstract void activate ();
	
	public Game getGame(){return game;}
	public String getName(){return name;}
	public Texture getTex(){return tex;}
}