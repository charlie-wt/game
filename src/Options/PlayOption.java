package Options;
import Game.Game;
import Game.Option;

public class PlayOption extends Option{
	public PlayOption(Game game, String name){super(game, name);}
	
	public void activate () {
		game.setState(Game.GAMEPLAY);
	}
}