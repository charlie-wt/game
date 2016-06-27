package Options;
import Game.Game;
import Game.Option;

public class QuitOption extends Option{
	public QuitOption(Game game, String name){super(game, name);}
	
	public void activate () {
		System.exit(0);
	}
}