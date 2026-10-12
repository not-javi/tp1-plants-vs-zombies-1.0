package pvz.logic.gameobjects;

import utils.Position;
import pvz.logic.Game;

public class SunflowerList {
	
	private Sunflower [] sunflowers;
	
	private int numberOfSunflowers;
	
	private static final int MAX_SUNFLOWERS = 50;
	
	public SunflowerList() {
		numberOfSunflowers = 0;
		sunflowers = new Sunflower[MAX_SUNFLOWERS];
	}
	
	public boolean isPositionEmpty(Position pos) {
		for (int i = 0; i < numberOfSunflowers; i++) {
			if (sunflowers[i].isInPosition(pos)) {
				return false;
			}
		}
		return true;
	}
	
	public int getPrice() {
		return Sunflower.COST;
	}
	
	public void add(Position pos, Game game) {
		sunflowers[numberOfSunflowers] = new Sunflower(pos, game);
		numberOfSunflowers++;
	}
	
	public void attackSunflower(Position pos, int damage) {
		int sunflowerNum = searchSunflowerByPos(pos);
		if (sunflowerNum != -1) {
			sunflowers[sunflowerNum].receiveDamage(damage);
			if (!sunflowers[sunflowerNum].isAlive()) deleteSunflower(sunflowerNum);
		}
	}
	
	private void deleteSunflower(int numSunflower) {
		numberOfSunflowers--;
		for (int i = numSunflower; i < numberOfSunflowers; i++) {
			sunflowers[i] = sunflowers[i+1];
		}
	}
	
	private int searchSunflowerByPos(Position pos) {
		int i = 0;
		while (i < numberOfSunflowers && !sunflowers[i].isInPosition(pos)) {
			i++;
		}
		if (i == numberOfSunflowers) return -1;
		return i;
	}
	
	public void update() {
		for (int i = 0; i < numberOfSunflowers; i++) {
			sunflowers[i].update();
		}
	}
	
	public String getSunflowerIconInPosition(Position pos) {
	    int s = searchSunflowerByPos(pos);
	    if (s != -1) {
	    	return sunflowers[s].getIcon();
	    }
	    return "";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
