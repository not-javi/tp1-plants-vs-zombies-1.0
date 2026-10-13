package pvz.logic.gameobjects;
import utils.Position;
import pvz.logic.Game;
import pvz.view.Messages;

public class Peashooter {
	
	public static final int MAX_RESISTANCE = 3;
	
	public static final int FREQUENCY = 1; 
	
	public static final int DAMAGE = 1;
	
	public static final int COST = 50;

	private Position pos;
	
	private int resistance;
	
	private int freqCount;
	
	private Game game;
	
	public Peashooter(Position pos, Game game) {
		freqCount = -1;
		resistance = MAX_RESISTANCE;
		this.pos = new Position(pos);
		this.game = game;
	}
	
	public Peashooter(Peashooter peashooter) {
		resistance = peashooter.resistance;
		freqCount = peashooter.freqCount;
		pos = new Position(peashooter.pos);
		game = peashooter.game;
	}
	
	// We set freqCount at -1 so peashooters cant shoot the instant they are planted
	
	public int getResistance() {
		return resistance;
	}
	
	public void update() {
		if (!isAlive()) {
			return;
		}
		freqCount++;
		if (freqCount >= FREQUENCY) {
			freqCount = 0;
			Position z = new Position(game.zombieInPeashooterRange(pos));
			if (!z.equals(pos)) {
				game.attackZombie(z, DAMAGE);
			}
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
		return Messages.PEASHOOTER_DESCRIPTION.formatted(COST, DAMAGE, MAX_RESISTANCE);
	}
	
	public String getIcon() {
		return String.format(Messages.PEASHOOTER_ICON, resistance);
	}
	
	
	
	
	
	
	
	
	
	
	
}
	
