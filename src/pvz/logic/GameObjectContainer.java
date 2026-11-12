package pvz.logic;

import java.util.List;
import pvz.logic.gameobjects.GameObject;
import pvz.logic.gameobjects.GameItem;
import utils.Position;
import java.util.ArrayList;

public class GameObjectContainer {

	private List<GameObject> gameObjects;

	public GameObjectContainer() {
		gameObjects = new ArrayList<GameObject>();
	}

	public boolean isPositionEmpty(Position pos) {
		for (GameObject obj : gameObjects) {
			if (obj.isInPosition(pos) && obj.fillsPos() && obj.isAlive())
				return false;
		}
		return true;
	}

	public boolean add(GameObject gameObj) {
		if (gameObjects.add(gameObj)) {
			gameObj.onEnter();
			return true;
		}
		return false;
	}

	public GameItem getGameItemInPos(Position pos) {
		for (GameObject obj : gameObjects) {
			if (obj.isInPosition(pos) && obj.fillsPos() && obj.isAlive())
				return obj;
		}
		return null;
	}

	public String getIconInPosition(Position pos) {
		StringBuilder icon = new StringBuilder();
		boolean sunDrawn = false;
		for (GameObject obj : gameObjects) {
			if (obj.isInPosition(pos) && obj.isAlive()) {
				if (obj.fillsPos()) {
					icon.append(obj.getIcon());
				} else if (!sunDrawn) {
					icon.append(obj.getIcon());
					sunDrawn = true;
				}
			}
		}
		return icon.toString();
	}

	public void update() {
		for (int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).update();
		}
	}

	public boolean removeDead() {
		for (GameObject obj : gameObjects) {
			if (!obj.isAlive()) {
				obj.onExit();
				gameObjects.remove(obj);
				return true;
			}
		}
		return false;
	}

	public int catchObject(Position pos) {
		int caught = 0;
		for (GameObject obj : gameObjects) {
			if (obj.catchObject(pos)) {
				caught++;
			}
		}
		return caught;
	}
}
