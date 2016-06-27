package Options;
import Game.Game;
import Game.Level;
import Game.Option;

public class RestartOption extends Option {
	public RestartOption(Game game, String name){super(game, name);}
	
	public void activate () {
		Level level = Level.fromFile("lvl1", game);
		game.setLevel(level);
		game.getPlayer().setLevel(level);
		game.getPlayer().resetLevel(true);		
		game.setState(Game.GAMEPLAY);
	}
}