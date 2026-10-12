package pvz.logic.gameobjects;

import utils.Position;
import pvz.logic.Game;
import pvz.view.Messages;

public class Zombie {
	
	private static final int MAX_RESISTANCE = 5;
	
	private static final int DAMAGE = 1;
	
	private static final int WALK_FREQUENCY = 2;
	
	private static final int ATTACK_FREQUENCY = 1;
	
	private int attFreqCount;
	
	private int wlkFreqCount;
	
	private int resistance;
	
	private Game game;
	
	private Position pos;
	
	public Zombie(Position pos, Game game) {
		attFreqCount = 0;
		wlkFreqCount = -1;
		this.pos = pos;
		this.game = game;
		this.resistance = MAX_RESISTANCE;
	}
	
	public Zombie(Zombie zombie) {
		pos = zombie.pos;
		game = zombie.game;
		resistance = zombie.resistance;
		attFreqCount = zombie.attFreqCount;
		wlkFreqCount = zombie.wlkFreqCount;
	}
	
	public boolean isInPosition(Position pos) {
		return (this.pos.equals(pos));
	}
	
	public boolean update() {
		if (!isAlive()) {
			return false;
		}
		Position aux = new Position (game.plantInZombieRange(pos));
		if (aux.equals(pos)) {
			return zombieWalk();
		}
		zombieAttack(aux);
		return false;
	}
	
	private boolean zombieWalk() {
		wlkFreqCount++;
		if (wlkFreqCount >= WALK_FREQUENCY) {
			wlkFreqCount = 0;
			attFreqCount = 0;
			Position leftPos = pos.left();
			if (leftPos == null) return true;
			if (game.isEmpty(leftPos)) {
				pos = leftPos;
			}
		}
		return false;
	}
	
	private void zombieAttack(Position plantPos) {
		attFreqCount++;
		if (attFreqCount >= ATTACK_FREQUENCY) {
			attFreqCount = 0;
			game.attackPlant(plantPos, DAMAGE);
		}
	}
	
	public void receiveAttack(int damage) {
		resistance -= damage;
	}
	
	public boolean isAlive() {
		if (resistance <= 0) return false;
		return true;
	}
	
	public String getIcon() {
		return String.format(Messages.ZOMBIE_ICON, resistance);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
