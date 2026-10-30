package pvz.logic;
import utils.Position;
import pvz.control.Level;

public interface GameModel {
	void reset(Level level, long seed);
	void reset();
	void update();
	void quit();
	
	public String getPlantsList();
	int addPlant(String plantName, Position pos);
	int addCheatPlant(String plantName, Position pos);
	int addZombie(String zombieName, Position pos);
	String getZombiesList();
}
