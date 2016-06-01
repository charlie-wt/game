
public class Game {
	Player player;
	
	public Game(){
		player = new Player(100, 100);
	}
	
	public void render(){player.draw();}
	public void update(){}
	public void getInput(){player.getInput();}
}