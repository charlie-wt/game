public class Game {
	Player player;

	public Game(){
		player = new Player(100, 100);
	}

	public void render(){player.render();}
	public void update(){player.update();}
	public void getInput(){player.getInput();}
}