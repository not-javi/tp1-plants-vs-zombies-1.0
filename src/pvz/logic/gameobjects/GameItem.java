package pvz.logic.gameobjects;

public interface GameItem {
	
	boolean receivePlantAttack(int damage);
	
	boolean receiveZombieAttack(int damage);

}
