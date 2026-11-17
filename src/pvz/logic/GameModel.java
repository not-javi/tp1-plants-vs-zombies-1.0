package pvz.logic;

import utils.Position;
import pvz.control.Level;

public interface GameModel {
	void reset(Level level, long seed);

	void reset();

	void update();

	void quit();

	String getPlantsList();

	String getZombiesList();

	boolean addGameObject(String name, Position pos);

	boolean addGameObjectForFree(String name, Position pos);

	boolean catchObject(Position pos);

	boolean isValidPosition(Position pos);

	boolean isInsideBoard(Position pos);

	boolean isSunAlreadyCaught();
}
