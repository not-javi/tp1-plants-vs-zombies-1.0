package pvz.logic.gameobjects;

import utils.Position;
import pvz.logic.Game;

public class ZombieList {
	
	private int numberOfZombies;
	
	private Zombie[] zombies;
	
	private static final int MAX_ZOMBIES = 10;

	
	public ZombieList() {
		zombies = new Zombie [MAX_ZOMBIES];
		numberOfZombies = 0;
	}
	
	public void addZombie(Position pos, Game game) {
		zombies[numberOfZombies] = new Zombie(pos, game);
		numberOfZombies++;
	}
	
	public boolean isPositionEmpty(Position pos) {
		for (int i = 0; i < numberOfZombies; i++) {
			if (zombies[i].isInPosition(pos)) {
				return false;
			}
		}
		return true;
	}
	
	public void attackZombie(Position pos, int damage) {
		int numZombie = searchZombieByPos(pos);
		if (numZombie != -1) {
	        zombies[numZombie].receiveAttack(damage);
	        if (!zombies[numZombie].isAlive()) deleteZombie(numZombie);
	    }
	}
	
	private int searchZombieByPos(Position pos) {
		int i = 0;
		while (i < numberOfZombies && !zombies[i].isInPosition(pos)) {
			i++;
		}
		if (i == numberOfZombies) return -1;
		return i;
	}
	
	private void deleteZombie(int numZombie) {
		numberOfZombies --;
		for (int i = numZombie; i < numberOfZombies; i++) {
			zombies[i] = zombies[i+1];
		}
	}
	
	public boolean update() {
		for (int i = 0; i < numberOfZombies; i++) {
			if (zombies[i].update()) return true;
		}
		return false;
	}
	
	public boolean noZombiesOnBoard() {
		return numberOfZombies == 0;
	}
	
	public String getZombieIconInPosition(Position pos) {
	    int z = searchZombieByPos(pos);
	    if (z != -1) {
	    	return zombies[z].getIcon();
	    }
	    return "";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
