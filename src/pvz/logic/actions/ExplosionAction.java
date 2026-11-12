package pvz.logic.actions;

import utils.Position;
import pvz.logic.gameobjects.GameItem;
import java.util.List;
import pvz.logic.GameWorld;

public class ExplosionAction implements GameAction {

	private int damage;

	private List<Position> positions;

	private boolean isPlant;

	public ExplosionAction(int damage, boolean isPlant, List<Position> positions) {
		this.damage = damage;
		this.positions = positions;
		this.isPlant = isPlant;
	}
	
	public static ExplosionAction plantExplosion(int damage, List<Position> positions) {
		return new ExplosionAction(damage,true,positions);
	}
	
	public static ExplosionAction zombieExplosion(int damage, List<Position> positions) {
		return new ExplosionAction(damage,false,positions);
	}

	public void execute(GameWorld game) {
		GameItem item;
		if (isPlant) {
			for (Position pos : positions) {
				item = game.getGameItemInPos(pos);
				if (item != null) item.receivePlantAttack(damage);
			}
		} else {
			for (Position pos : positions) {
				item = game.getGameItemInPos(pos);
				if (item != null) item.receiveZombieAttack(damage);
			}
		}
	}

}
