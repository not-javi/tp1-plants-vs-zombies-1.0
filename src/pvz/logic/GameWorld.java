package pvz.logic;
import utils.Position;
import pvz.logic.gameobjects.GameItem;
import pvz.logic.gameobjects.GameObject;

public interface GameWorld {
	public static final int NUM_ROWS = 4;
	public static final int NUM_COLS = 8;
	
	boolean isEmpty(Position position);
	boolean isInsideBoard(Position position);
	
	GameItem getGameItemInPos(Position pos);
	void generateSunCoins(int coins);
	public boolean addGameObject(GameObject item);
	public void zombieInPos(Position pos);
	public void zombieDied();
	public void zombieEntered();
}
