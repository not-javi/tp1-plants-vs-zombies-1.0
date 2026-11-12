package pvz.logic.gameobjects;

import pvz.logic.GameWorld;
import pvz.view.Messages;
import utils.Position;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import pvz.logic.actions.ExplosionAction;

public class CherryBomb extends Plant {

	public static final int MAX_LIFE = 2;

	public static final int FREQUENCY = 2;

	public static final int DAMAGE = 10;

	public static final int COST = 50;

	private int freqCount;

	public CherryBomb(Position pos, GameWorld game) {
		super(game, pos, MAX_LIFE);
		freqCount = 0;
	}

	@Override
	public void update() {
		if (!isAlive()) {
			return;
		}
		freqCount++;
		if (freqCount >= FREQUENCY) {
			List<Position> positions = this.pos.areaRad1();
			positions.removeIf(aux -> !game.isInsideBoard(aux));
			game.pushAction(ExplosionAction.plantExplosion(DAMAGE, positions));
			this.takeDamage(this.life);
		}
	}

	@Override
	public int getCost() {
		return COST;
	}

	@Override
	public String getDescription() {
		return Messages.CHERRY_BOMB_DESCRIPTION.formatted(COST, DAMAGE, MAX_LIFE);
	}

	@Override
	public String getSymbol() {
		if (freqCount == FREQUENCY -1) {
			return Messages.CHERRY_BOMB_SYMBOL.toUpperCase();
		}
		return Messages.CHERRY_BOMB_SYMBOL;
	}

	@Override
	public String getName() {
		return Messages.CHERRY_BOMB_NAME;
	}

	@Override
	public Plant spawn(Position pos, GameWorld game) {
		return new CherryBomb(pos, game);
	}
}
