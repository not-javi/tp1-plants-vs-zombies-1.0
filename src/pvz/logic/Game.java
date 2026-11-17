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

	private boolean addGameObject(String name, Position pos, boolean consumeCoins) {
		Plant plant = PlantFactory.spawnPlant(name, this, pos);
		if (plant != null) {
		    if (!plant.canBeSpawnedAt(pos) || (consumeCoins && sunCoins < plant.getCost())) return false;
		    if (!addGameObject(plant)) return false;
		    if (consumeCoins) sunCoins -= plant.getCost();
		    return true;
		}
		Zombie zombie = ZombieFactory.spawnZombie(name, this, pos);
		if (zombie != null && zombie.canBeSpawnedAt(pos) && addGameObject(zombie)) {
		    zombiesManager.zombieAdded();
		    return true;
		}
		return false;
	}
	
	public boolean addGameObject(String name, Position pos) {
		return addGameObject(name,pos,true);
	}
	
	public boolean addGameObjectForFree(String name, Position pos) {
		return addGameObject(name,pos,false);
	}

	public boolean zombieAttack(Position pos, int damage) {
		return container.zombieAttack(pos, damage);
	}
	
	public boolean plantAttack(Position pos, int damage) {
		return container.plantAttack(pos, damage);
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
		return container.positionToString(pos);
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

	public boolean doZombiesReachedTheHouse() {
		return container.anyInColumn(-1);
	}

	public boolean playerWins() {
		return zombiesManager.getRemainingZombies() <= 0 && zombiesManager.areAllZombiesDead();
	}

	public boolean playerQuits() {
		return finished && !playerWins() && !doZombiesReachedTheHouse();
	}

	public boolean hasGameFinished() {
		return finished || playerWins() || doZombiesReachedTheHouse();
	}

	public boolean isInsideBoard(Position pos) {
		return pos.isInside(NUM_COLS, NUM_ROWS);
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
		if (caughtSun) return false;
		int caught = container.catchObject(pos);
		if (caught > 0) {
			caughtCoins += caught;
			caughtSun = true;
			return true;
		}
		return false;
	}

	public int getGeneratedCoins() {
		return generatedCoins;
	}

	public int getCaughtCoins() {
		return caughtCoins;
	}
	
	public boolean isInBoardLimit(Position position) {
		return position.row() >= 0 && position.row() < NUM_ROWS && position.column() >= -1 && position.column() <= NUM_COLS;
	}

	public boolean isValidPosition(Position pos) {
		return isInsideBoard(pos) && isEmpty(pos);
	}
	
	public boolean isSunAlreadyCaught() {
		return caughtSun;
	}
	
	public void zombieKilled() {
		zombiesManager.zombieKilled();
	}
}
