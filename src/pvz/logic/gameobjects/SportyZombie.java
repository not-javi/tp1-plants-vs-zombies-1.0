package pvz.logic.gameobjects;

import pvz.logic.GameWorld;
import pvz.view.Messages;
import utils.Position;

public class SportyZombie extends Zombie {
	
	private static final int MAX_LIFE = 2;

	private static final int DAMAGE = 1;

	private static final int WALK_FREQUENCY = 1;

	private int freqCount;

	public SportyZombie(Position pos, GameWorld game) {
		super(game, pos, MAX_LIFE);
		freqCount = -1;
	}

	@Override
	public void update() {
		if (!isAlive()) {
			return;
		}
		Position aux = pos.left();
		GameItem item = game.getGameItemInPos(aux);
		if (item != null && item.receiveZombieAttack(DAMAGE)) {
			freqCount = 0;
			return;
		}
		freqCount++;
		if (freqCount >= WALK_FREQUENCY) {
			freqCount = 0;
			if (game.isEmpty(aux)) {
				pos = aux;
			}
		}
		didZombiesWin();
	}

	@Override
	public String getDescription() {
		return Messages.ZOMBIE_DESCRIPTION.formatted(Messages.ZOMBIE_NAME_AND_SHORTCUT, WALK_FREQUENCY, DAMAGE,
				MAX_LIFE);
	}

	@Override
	public String getSymbol() {
	    return Messages.SPORTY_ZOMBIE_SYMBOL;
	}

	@Override
	public String getName() {
		return Messages.SPORTY_ZOMBIE_NAME;
	}

	@Override
	public Zombie spawn(Position pos, GameWorld game) {
		return new SportyZombie(pos, game);
	}
}
