package pvz.logic;
import utils.Position;
import pvz.logic.actions.GameAction;
import pvz.logic.gameobjects.GameItem;
import pvz.logic.gameobjects.GameObject;

public interface GameWorld {
	static final int NUM_ROWS = 4;
	static final int NUM_COLS = 8;
	
	boolean isEmpty(Position position);
	boolean isInsideBoard(Position position);
	boolean isInBoardLimit(Position pos);
	Position randomPosition();
	
	void generateCoins(int coins);
	boolean addGameObject(GameObject item);
	void generateSun();
	
	void zombieKilled();
	void pushAction(GameAction gameAction);
	boolean zombieAttack(Position p, int damage);
	boolean plantAttack(Position p, int damage);
}
