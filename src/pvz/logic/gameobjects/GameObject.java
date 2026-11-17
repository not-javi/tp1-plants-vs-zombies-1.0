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

	public boolean receivePlantAttack(int damage) {
		return false;
	}

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

	public boolean fillsPos() {
		return true;
	}

	public boolean catchObject(Position pos) {
		return false;
	}

	protected void takeDamage(int damage) {
		this.life -= damage;
	}

	public int getDamage() {
		return 0;
	}

	public int getEndurance() {
		return life;
	}
	
	public boolean isInColumn(int column) {
		return this.pos.isInCol(column);
	}

	public abstract boolean canBeSpawnedAt(Position pos);

	public abstract String getName();

	public abstract String getSymbol();

	public boolean matchGameObjectName(String name) {
		return this.getName().equalsIgnoreCase(name) || this.getSymbol().equalsIgnoreCase(name);
	}

}