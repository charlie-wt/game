public class PlayOption extends Option{
	public PlayOption(Game game, String name){super(game, name);}
	public PlayOption(Game game, String name, int index){super(game, name, index);}
	
	public void activate () {
		game.setState(Game.GAMEPLAY);
	}
}
