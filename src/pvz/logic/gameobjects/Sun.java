package pvz.logic.gameobjects;

import pvz.logic.GameWorld;
import pvz.view.Messages;
import utils.Position;

public class Sun extends GameObject {
	
	private static final int MAX_LIFE = 11;
	
	private static final int SUN_GEN = 10;
	
	public Sun(Position p, GameWorld game) {
		super(game,p,MAX_LIFE);
	}

	public void update() {
		life -=1;
	}

	public String getIcon() {
	    return Messages.GAME_OBJECT_ICON.formatted(this.getSymbol(), this.life);
	}

	public String getDescription() {
		return Messages.SUN_DESCRIPTION;
	}

	public String getName() {
		return getDescription();
	}
	
	public String getSymbol() {
		return Messages.SUN_SYMBOL;
	}
	
	@Override
	public boolean fillsPos() {
		return false;
	}
	
	@Override 
	public boolean catchObject(Position pos) {
		if (this.isInPosition(pos)) {
			game.generateCoins(SUN_GEN);
			this.takeDamage(life);
			return true;
		}
		return false;
	}
}
