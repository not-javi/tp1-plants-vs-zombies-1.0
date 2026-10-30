package pvz.logic.gameobjects;

import utils.Position;
import pvz.logic.GameWorld;
import pvz.view.Messages;

public abstract class GameObject implements GameItem {

	protected GameWorld game;
	protected Position pos;
	protected int life;

	public GameObject(GameWorld game, Position p, int life) {
		this.game = game;
		this.pos = p;
		this.life = life;
	}

	@Override
	public boolean receivePlantAttack(int damage) {
		return false;
	}

	@Override
	public boolean receiveZombieAttack(int damage) {
		return false;
	}

	public boolean isInPosition(Position other) {
		return this.pos.equals(other);
	}

	public abstract void update();

	public String getIcon() {
	    return Messages.GAME_OBJECT_ICON.formatted(this.getSymbol(), this.life);
	}

	public abstract String getDescription();

	public boolean isAlive() {
		return this.life > 0;
	}

	public void onEnter() {
	}

	public void onExit() {
	}

	public void onDead() {
	}

	protected void takeDamage(int damage) {
		this.life -= damage;
		if (!this.isAlive())
			this.onDead();
	}

	public abstract String getName();
	
	protected abstract String getSymbol();

	public abstract String getShortcut();
	
	

	public abstract GameObject spawn(Position pos, GameWorld game);

	public boolean matchGameObjectName(String name) {
		return this.getName().equalsIgnoreCase(name) || this.getShortcut().equalsIgnoreCase(name);
	}

}