package pvz.logic.gameobjects;

import utils.Position;
import pvz.logic.GameWorld;

public abstract class Zombie extends GameObject {

	private int freqCount = -1;

	private int speed;

	public Zombie(GameWorld game, Position pos, int life, int speed) {
		super(game, pos, life);
		this.speed = speed;
	}

	@Override
	public boolean receivePlantAttack(int damage) {
		if (isAlive()) {
			this.takeDamage(damage);
			return true;
		}
		return false;
	}

	@Override
	public void update() {
		if (!isAlive())
			return;
		Position next = pos.left();
		if (!game.isEmpty(next)) {
			game.zombieAttack(next, this.getDamage());
			freqCount = 0;
			return;
		}
		freqCount++;
		if (freqCount >= speed) {
			freqCount = 0;
			pos = next;
		}
	}

	@Override
	public void onExit() {
		game.zombieKilled();
	}

	@Override
	public boolean canBeSpawnedAt(Position pos) {
		return game.isInBoardLimit(pos) && game.isEmpty(pos);
	}

	public abstract Zombie copy(Position pos, GameWorld game);
}
