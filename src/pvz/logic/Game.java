package pvz.logic;

import java.util.Random;
import pvz.logic.gameobjects.PeashooterList;
import pvz.logic.gameobjects.SunflowerList;
import pvz.control.Level;
import utils.Position;
import pvz.view.Messages;

public class Game {
	
	private Level level;
	private Random rand;
	private int sunCoins;
	private int cycle;
	private ZombiesManager zombiesManager; 
	private PeashooterList peashooterList;
	private SunflowerList sunflowerList;
	private boolean finished;
	private boolean zombiesWin;
	
	
	public static final int NUM_ROWS = 4;
	public static final int NUM_COLS = 8;
	public static final int INITIAL_COINS = 50;
	
	
	
	
	
	
	public Game (long seed, Level level) {
		rand = new Random(seed);
		this.level = level;
		sunCoins = INITIAL_COINS;
		cycle = 0;
		finished = false;
		zombiesWin = false;
		peashooterList = new PeashooterList();
		zombiesManager = new ZombiesManager(this,level,rand);
		sunflowerList = new SunflowerList();
	}
	
	public int addGameObject(String plant, Position pos) {
		if (!pos.inBoard() || !isEmpty(pos)) {
			return 1;
		}
		if (!checkGameObject(plant)) return 3;
		switch (plant) {
		case Messages.PEASHOOTER: 
		case Messages.SHORT_PEASHOOTER: 
			if (sunCoins < peashooterList.getPrice()) return 2;
			peashooterList.add(pos, this);
			sunCoins -=  peashooterList.getPrice();
			break;
		case Messages.SUNFLOWER:
		case Messages.SHORT_SUNFLOWER:
			if (sunCoins < sunflowerList.getPrice()) return 2;
			sunflowerList.add(pos, this);
			sunCoins -=  sunflowerList.getPrice();
			break;
		}		
		return 0;
	}
	
	public String getPlantsList() {
		return Messages.LIST;
	}
	
	public void reset() {
		cycle = 0;
		finished = false;
		zombiesWin = false;
		sunCoins = INITIAL_COINS;
		peashooterList = new PeashooterList();
		zombiesManager = new ZombiesManager(this,level,rand);
		sunflowerList = new SunflowerList();
	}
	
	public void quit() {
		finished = true;
	}
	
	public boolean isEmpty(Position pos) {
		return (peashooterList.isPositionEmpty(pos) && sunflowerList.isPositionEmpty(pos) && zombiesManager.isPositionEmpty(pos));
	}
	
	public Position zombieInPeashooterRange(Position pos) {
		Position aux = pos.right();
		while (aux != null) {
			if (!zombiesManager.isPositionEmpty(aux)) {
	            return aux;
			}
			aux = aux.right();
		}
		return pos;
	}
	
	public Position plantInZombieRange(Position pos) {
		Position aux = new Position(pos);
		aux = aux.left();
		if (aux == null) {
			return pos;
		}
		if (!peashooterList.isPositionEmpty(aux) || !sunflowerList.isPositionEmpty(aux)) {
			return aux;
		}
		return pos;
	}
	
	public void attackZombie(Position pos, int damage) {
		zombiesManager.attackZombie(pos, damage);
	}
	
	public void attackPlant(Position pos, int damage) {
		peashooterList.attackPeashooter(pos, damage);
		sunflowerList.attackSunflower(pos,damage);
	}
	
	public void generateSunCoins(int coins) {
		sunCoins += coins;
	}

	public void update() {
		sunflowerList.update();
		peashooterList.update();
		if (zombiesManager.update()) zombiesWin = true;
		cycle++;
	}
	
	public String positionToString(Position pos) {
		String res = "";
		res += peashooterList.getPeashooterIconInPosition(pos);
		res += sunflowerList.getSunflowerIconInPosition(pos);
		res += zombiesManager.getZombieIconInPosition(pos);
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
	
	public boolean checkGameObject(String plant) {
	    return plant.equalsIgnoreCase(Messages.PEASHOOTER) || plant.equalsIgnoreCase(Messages.SHORT_PEASHOOTER) ||
	           plant.equalsIgnoreCase(Messages.SUNFLOWER) || plant.equalsIgnoreCase(Messages.SHORT_SUNFLOWER);
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
		return pos.inBoard();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
