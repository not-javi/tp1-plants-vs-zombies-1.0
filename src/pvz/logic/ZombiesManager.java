package pvz.logic;

import java.util.Random;
import pvz.control.Level;
import utils.Position;
import pvz.logic.factories.ZombieFactory;

/**
 * Manages the full lifecycle of zombies for a game session.
 *
 * <p>
 * Responsibilities: deciding each cycle whether to spawn a new zombie
 * (probabilistically, subject to the remaining quota from {@link Level}),
 * delegating per-cycle updates and dead-removal to the underlying
 * {@link ZombieList}, and answering win/loss queries
 *
 */
public class ZombiesManager {

	private GameWorld game;

	private Level level;

	private Random rand;

	private int remainingZombies;

	private int zombiesAlive;

	public ZombiesManager(GameWorld game, Level level, Random rand) {
		this.game = game;
		this.level = level;
		this.rand = rand;
		zombiesAlive = 0;
		remainingZombies = level.getNumberOfZombies();
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
		if (shouldAddZombie() && getRemainingZombies() > 0) {
			int row = randomZombieRow();
			Position aux = new Position(row, Game.NUM_COLS);
			if (game.isEmpty(aux)) {
				if (game.addGameObject(ZombieFactory.spawnRandomZombie(game, aux, rand))) {
					remainingZombies--;
					zombiesAlive++;
					return true;
				}
			}
		}
		return false;
	}

	public int getRemainingZombies() {
		return remainingZombies;
	}

	public void update() {
		addZombie();
	}

	public boolean areAllZombiesDead() {
		return zombiesAlive <= 0;
	}

	public void zombieKilled() {
		zombiesAlive--;
	}

	public void zombieAdded() {
		zombiesAlive++;
	}
}
