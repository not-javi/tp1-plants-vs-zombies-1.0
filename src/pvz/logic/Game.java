package pvz.logic;

import java.util.Random;
import pvz.logic.GameObjectContainer;
import pvz.control.Level;
import utils.Position;
import pvz.view.Messages;
import pvz.logic.factories.*;
import pvz.logic.gameobjects.*;

public class Game implements GameModel, GameStatus, GameWorld {

	private Level level;
	private Random rand;
	private int sunCoins;
	private int cycle;
	private ZombiesManager zombiesManager;
	private GameObjectContainer container;
	private boolean finished;
	private boolean zombiesWin;
	private long seed;

	public static final int NUM_ROWS = 4;
	public static final int NUM_COLS = 8;
	public static final int INITIAL_COINS = 50;

	public Game(long seed, Level level) {
		reset(level,seed);
	}

	public boolean addGameObject(GameObject item) {
		if (item == null) {
			return false;
		}
		return container.add(item);
	}

	private int addPlant(String plantName, Position pos, boolean consumeCoins) {
		if (!isInsideBoard(pos) || !isEmpty(pos)) {
			return 1;
		}
		Plant plant = PlantFactory.spawnPlant(plantName, this, pos);
		if (plant == null) {
			return 3;
		}
		if (consumeCoins && sunCoins < plant.getCost()) {
			return 2;
		}
		if (addGameObject(plant)) {
			if (consumeCoins)
				sunCoins -= plant.getCost();
			return 0;
		}
		return 4;
	}

	public int addPlant(String plantName, Position pos) {
		return addPlant(plantName, pos, true);
	}

	public int addCheatPlant(String plantName, Position pos) {
		return addPlant(plantName, pos, false);
	}

	public int addZombie(String zombieName, Position pos) {
		if (!isInsideBoard(pos) || !isEmpty(pos)) {
			return 1;
		}
		Zombie zombie = ZombieFactory.spawnZombie(zombieName, this, pos);
		if (zombie == null) {
			return 3;
		}
		if (addGameObject(zombie))
			return 0;
		return 4;
	}

	public String getPlantsList() {
		StringBuilder sb = new StringBuilder();
		for (Plant plant : PlantFactory.getAvailablePlants()) {
			if (sb.length() > 0)
				sb.append(System.lineSeparator());
			sb.append(plant.getDescription());
		}
		return sb.toString();
	}
	
	@Override
	public String getZombiesList() {
	    StringBuilder sb = new StringBuilder();
	    for (Zombie zombie : ZombieFactory.getAvailableZombies()) {
	        if (sb.length() > 0)
	            sb.append(System.lineSeparator());
	        sb.append(zombie.getDescription());
	    }
	    return sb.toString();
	}

	public void reset() { reset(level, seed); }

	public void reset(Level level, long seed) {
	    this.level = level;
	    this.seed = seed;
	    rand = new Random(seed);
	    cycle = 0;
	    finished = false;
	    zombiesWin = false;
	    sunCoins = INITIAL_COINS;
	    container = new GameObjectContainer();
	    zombiesManager = new ZombiesManager(this, level, rand);
	}

	public void quit() {
		finished = true;
	}

	public boolean isEmpty(Position pos) {
		return (container.isPositionEmpty(pos));
	}

	public GameItem getGameItemInPos(Position pos) {
		return container.getGameItemInPos(pos);
	}

	public void generateSunCoins(int coins) {
		sunCoins += coins;
	}

	public void update() {
		container.update();
		zombiesManager.update();	
		cycle++;
	}

	public String positionToString(Position pos) {
		String res = "";
		res += container.getIconInPosition(pos);
		if (res.isEmpty()) {
			return " ";
		}
		return res;
	}

	public int getCycles() {
		return cycle;
	}

	public int getCoins() {
		return sunCoins;
	}

	public int getRemainingZombies() {
		return zombiesManager.getRemainingZombies();
	}

	public boolean isZombiesWinner() {
		return zombiesWin;
	}

	public boolean isPlayerWinner() {
		return zombiesManager.getRemainingZombies() == 0 && zombiesManager.areAllZombiesDead();
	}

	public boolean playerQuits() {
		return finished && !isPlayerWinner() && !isZombiesWinner();
	}

	public boolean hasGameFinished() {
		return finished || isPlayerWinner() || isZombiesWinner();
	}

	public boolean isInsideBoard(Position pos) {
		return pos.isInside(NUM_COLS, NUM_ROWS);
	}

	public void zombieInPos(Position pos) {
		if (pos.isInCol(-1))
			zombiesWin = true;
	}

	@Override
	public void zombieDied() {
		zombiesManager.zombieDied();
	}

	@Override
	public void zombieEntered() {
		zombiesManager.zombieEntered();
	}
}
