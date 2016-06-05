public class Game {
	Player player;
	Level level;

	public Game(){
		level = new Level();
		player = new Player(100, 100, level);
	}

	public void render(){player.render(); level.render();}
	public void update(){Physics.getCollision(player, level); player.update();}
	public void getInput(){player.getInput();}
}