package pvz.logic;

import java.util.Random;
import pvz.control.Level;
import pvz.logic.gameobjects.Zombie;
import pvz.logic.gameobjects.ZombieList;
import utils.Position;

/**
 * Manages the full lifecycle of zombies for a game session.
 *
 * <p>Responsibilities: deciding each cycle whether to spawn a new zombie
 * (probabilistically, subject to the remaining quota from {@link Level}),
 * delegating per-cycle updates and dead-removal to the underlying
 * {@link ZombieList}, and answering win/loss queries
 *
 */
public class ZombiesManager {

	private Game game;

	private Level level;

	private Random rand;

	private int remainingZombies;

	private ZombieList zombies;

	public ZombiesManager(Game game, Level level, Random rand) {
		this.game = game;
		this.level = level;
		this.rand = rand;
		remainingZombies = level.getNumberOfZombies();
		zombies = new ZombieList();
	}

	/**
	 * Checks if the game should add (if possible) a zombie to the game.
	 * 
	 * @return <code>true</code> if a zombie should be added to the game.
	 */
	private boolean shouldAddZombie() {
		return rand.nextDouble() < level.getZombieFrequency();
	}
	
	/**
	 * Return a random row within the board limits.
	 * 
	 * @return a random row.
	 */
	private int randomZombieRow() {
		return rand.nextInt(Game.NUM_ROWS);
	}
	
	public boolean addZombie() {
		int row = randomZombieRow();
		return addZombie(row);
	}

	public boolean addZombie(int row) {
		Position aux = new Position(row, Game.NUM_COLS - 1);
		boolean canAdd = getRemainingZombies() > 0 && shouldAddZombie() && isPositionEmpty(aux);
		if(canAdd) {
			remainingZombies--;
			zombies.addZombie(aux, game);
		}
		return canAdd;
	}
	
	public int getRemainingZombies() {
		return remainingZombies;
	}
	
	public boolean isPositionEmpty(Position pos) {
		return zombies.isPositionEmpty(pos);
	}
	
	public void attackZombie(Position pos, int damage) {
		zombies.attackZombie(pos, damage);
	}
	
	public boolean update() {
		if (zombies.update()) return true;
		addZombie();
		return false;
	}
	
	public String getZombieIconInPosition(Position pos) {
		return zombies.getZombieIconInPosition(pos);
	}
	
	public boolean areAllZombiesDead() {
	    return zombies.noZombiesOnBoard();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
