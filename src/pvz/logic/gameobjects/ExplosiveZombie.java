package pvz.logic.gameobjects;

import java.util.List;

import pvz.logic.GameWorld;
import pvz.logic.actions.ExplosionAction;
import pvz.view.Messages;
import utils.Position;

public class ExplosiveZombie extends Zombie {
	
	private static final int MAX_LIFE = 5;

	private static final int DAMAGE = 1;
	
	private static final int EXPLOSIVE_DAMAGE = 3;

	private static final int WALK_FREQUENCY = 2;

	private int freqCount;

	public ExplosiveZombie(Position pos, GameWorld game) {
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
	    return Messages.EXPLOSIVE_ZOMBIE_SYMBOL;
	}

	@Override
	public String getName() {
		return Messages.EXPLOSIVE_ZOMBIE_NAME;
	}

	@Override
	public Zombie spawn(Position pos, GameWorld game) {
		return new ExplosiveZombie(pos, game);
	}

	@Override
	public void onExit() {
		super.onExit();
		List<Position> positions = this.pos.areaRad1();
		positions.removeIf(aux -> !game.isInsideBoard(aux));
		game.pushAction(ExplosionAction.zombieExplosion(EXPLOSIVE_DAMAGE, positions));
	}
}
