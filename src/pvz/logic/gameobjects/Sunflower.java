package pvz.logic.gameobjects;

import utils.Position;
import pvz.logic.Game;
import pvz.view.Messages;

public class Sunflower {
	
	public static final int FREQUENCY = 3; 
	
	public static final int MAX_RESISTANCE = 1;
	
	public static final int SUN_GEN = 10;
	
	public static final int COST = 20;

	private Position pos;
	
	private int resistance;
	
	private int freqCount;
	
	private Game game;
	
	public Sunflower (Position pos, Game game) {
		this.pos = new Position(pos);
		freqCount = -1;
		resistance = MAX_RESISTANCE;
		this.game = game;
	}
	
	public Sunflower(Sunflower sunflower) {
		resistance = sunflower.resistance;
		freqCount = sunflower.freqCount;
		pos = new Position(sunflower.pos);
		game = sunflower.game;
	}
	
	
	public int getResistance() {
		return resistance;
	}
	
	public int getSunGen() {
		return SUN_GEN;
	}
	
	public void update() {
		if (!isAlive()) {
			return;
		}
		freqCount++;
		if (freqCount >= FREQUENCY) {
			freqCount = 0;
			game.generateSunCoins(SUN_GEN);
		}
	}
	
	public boolean isInPosition(Position pos) {
		return (this.pos.equals(pos));
	}
	
	public void receiveDamage(int damage) {
		resistance -= damage;
	}
	
	public boolean isAlive() {
		if (resistance <= 0) return false;
		return true;
	}
	
	public static String getDescription() {
		return Messages.SUNFLOWER_DESCRIPTION.formatted(COST, 0, MAX_RESISTANCE);
	}
	
	public String getIcon() {
		return String.format(Messages.SUNFLOWER_ICON, resistance);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
