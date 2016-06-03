public class Game {
	Player player;
	Level level;

	public Game(){
		player = new Player(100, 100);
		level = new Level();
	}

	public void render(){player.render(); level.render();}
	public void update(){player.update();}
	public void getInput(){player.getInput();}
}