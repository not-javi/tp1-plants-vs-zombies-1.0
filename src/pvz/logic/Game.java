package pvz.logic;

import java.util.Random;
import pvz.control.Level;
import utils.Position;
import pvz.logic.factories.*;
import pvz.logic.gameobjects.*;
import java.util.ArrayDeque;
import java.util.Deque;
import pvz.logic.actions.GameAction;

public class Game implements GameModel, GameStatus, GameWorld {

	private Level level;
	private Random rand;
	private int sunCoins;
	private int cycle;
	private ZombiesManager zombiesManager;
	private SunsManager sunsManager;
	private GameObjectContainer container;
	private boolean finished;
	private boolean zombiesWin;
	private boolean caughtSun;
	private long seed;
	private Deque<GameAction> actions;
	private int generatedCoins = 0;
	private int caughtCoins = 0;

	public static final int NUM_ROWS = 4;
	public static final int NUM_COLS = 8;
	public static final int INITIAL_COINS = 50;

	public Game(long seed, Level level) {
		reset(level, seed);
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
		if (plant == null)
			return 2;
		if (consumeCoins && sunCoins < plant.getCost()) {
			return 3;
		}
		addGameObject(plant);
		if (consumeCoins)
			sunCoins -= plant.getCost();
		return 0;

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
		if (addGameObject(zombie))
			return 0;
		else
			return 2;
	}

	public void generateSun() {
		addGameObject(new Sun(randomPosition(),this ));
		generatedCoins += 1;
	}

	public Position randomPosition() {
		return new Position(rand.nextInt(NUM_ROWS), rand.nextInt(NUM_COLS));
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

	public void reset() {
		reset(level, seed);
	}

	public void reset(Level level, long seed) {
		this.level = level;
		this.seed = seed;
		rand = new Random(seed);
		cycle = 0;
		finished = false;
		zombiesWin = false;
		sunCoins = INITIAL_COINS;
		this.generatedCoins = 0;
		this.caughtCoins = 0;
		this.caughtSun = false;
		container = new GameObjectContainer();
		zombiesManager = new ZombiesManager(this, level, rand);
		sunsManager = new SunsManager(this);
		this.actions = new ArrayDeque<>();
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

	public void generateCoins(int coins) {
		sunCoins += coins;
	}

	public void update() {
		executePendingActions();
		zombiesManager.update();
		sunsManager.update();
		container.update();
		boolean deadRemoved = true;
		while (deadRemoved || arePendingActionsLeft()) {
			deadRemoved = this.container.removeDead();
			executePendingActions();
		}
		this.cycle++;
		this.caughtSun = false;
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

	public void pushAction(GameAction gameAction) {
		this.actions.addLast(gameAction);
	}

	private void executePendingActions() {
		while (!this.actions.isEmpty()) {
			GameAction action = this.actions.removeFirst();
			action.execute(this);
		}
	}

	private boolean arePendingActionsLeft() {
		return !this.actions.isEmpty();
	}

	public boolean catchObject(Position pos) {
		int caught = container.catchObject(pos);
		if (caught > 0) {
			caughtCoins += caught;
			caughtSun = true;
			return true;
		}
		return false;
	}

	public int catchCommand(Position pos) {
		if (!isInsideBoard(pos))
			return 1;
		if (caughtSun)
			return 3;
		if (catchObject(pos))
			return 0;
		return 2;
	}

	public int getGeneratedCoins() {
		return generatedCoins;
	}

	public int getCaughtCoins() {
		return caughtCoins;
	}

}
