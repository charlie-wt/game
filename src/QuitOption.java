
public class QuitOption extends Option{
	public QuitOption(Game game, String name){super(game, name);}
	public QuitOption(Game game, String name, int index){super(game, name, index);}
	
	public void activate () {
		System.exit(0);
	}
}
