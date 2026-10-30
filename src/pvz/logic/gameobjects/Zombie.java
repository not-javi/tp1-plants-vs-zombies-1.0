package pvz.logic.gameobjects;

import utils.Position;
import pvz.logic.GameWorld;

public abstract class Zombie extends GameObject {

	public Zombie(GameWorld game, Position pos, int life) {
		super(game, pos, life);
	}

	@Override
	public boolean receivePlantAttack(int damage) {
		if (isAlive()) {
			this.takeDamage(damage);
			return true;
		}
		return false;
	}

	public void didZombiesWin() {
		if (this.isAlive())
			game.zombieInPos(this.pos);
	}

	@Override
	public void onDead() {
		game.zombieDied();
	}
	
	@Override
	public void onEnter() {
		game.zombieEntered();
	}
}
