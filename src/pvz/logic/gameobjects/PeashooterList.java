package pvz.logic.gameobjects;

import utils.Position;
import pvz.logic.Game;

public class PeashooterList {

	private int numberOfPeashooters;
	
	private Peashooter[] peashooters;
	
	private static final int MAX_PEASHOOTERS = 50;
	
	public PeashooterList() {
		numberOfPeashooters = 0;
		peashooters = new Peashooter[MAX_PEASHOOTERS];
	}
	
	public boolean isPositionEmpty(Position pos) {
		for (int i = 0; i < numberOfPeashooters; i++) {
			if (peashooters[i].isInPosition(pos)) {
				return false;
			}
		}
		return true;
	}

	public int getPrice() {
		return Peashooter.COST;
	}

	public void add(Position pos, Game game) {
		peashooters[numberOfPeashooters] = new Peashooter(pos, game);
		numberOfPeashooters++;
	}
	
	private void deletePeashooter(int numPeashooter) {
		numberOfPeashooters--;
		for (int i = numPeashooter; i < numberOfPeashooters; i++) {
			peashooters[i] =peashooters[i+1];
		}
	}
	
	private int searchPeashooterByPos(Position pos) {
		int i = 0;
		while (i < numberOfPeashooters && !peashooters[i].isInPosition(pos)) {
			i++;
		}
		if (i == numberOfPeashooters) return -1;
		return i;
	}
	
	public void attackPeashooter(Position pos, int damage) {
		int peashooterNum = searchPeashooterByPos(pos);
		if (peashooterNum != -1) {
			peashooters[peashooterNum].receiveDamage(damage);
			if (!peashooters[peashooterNum].isAlive()) deletePeashooter(peashooterNum);
		}
	}
	
	public void update() {
		for (int i = 0; i < numberOfPeashooters; i++) {
			peashooters[i].update();
		}
	}
	
	public String getPeashooterIconInPosition(Position pos) {
	    int p = searchPeashooterByPos(pos);
	    if (p != -1) {
	    	return peashooters[p].getIcon();
	    }
	    return "";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}