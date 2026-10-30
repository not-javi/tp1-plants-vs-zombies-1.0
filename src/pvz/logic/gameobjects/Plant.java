package pvz.logic.gameobjects;

import pvz.logic.GameWorld;
import utils.Position;

public abstract class Plant extends GameObject {

	public Plant(GameWorld game, Position p, int life) {
		super(game, p, life);
	}

	@Override
	public boolean receiveZombieAttack(int damage) {
		if (isAlive()) {
			this.takeDamage(damage);
			return true;
		}
		return false;
	}

	public abstract int getCost();

}
