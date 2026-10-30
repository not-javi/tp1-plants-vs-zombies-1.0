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
			if (obj.isInPosition(pos)) return false;
		}
		return true;
	}
	
	public boolean add(GameObject gameObj) {
		if (gameObjects.add(gameObj))  {
			gameObj.onEnter();
			return true;
		}
		return false;
	}
	
	private GameObject searchByPos(Position pos) {
		for (GameObject obj : gameObjects) {
			if (obj.isInPosition(pos)) return obj;
		}
		return null;
	}
	
	public GameItem getGameItemInPos(Position pos) {
		return searchByPos(pos);
	}
	
	public String getIconInPosition(Position pos) {
	    GameObject obj = searchByPos(pos);
	    if (obj != null) {
	    	return obj.getIcon();
	    }
	    return "";
	}
	
	public void update() {
		for (GameObject obj : gameObjects) {
			obj.update();
		}
		gameObjects.removeIf(obj -> !obj.isAlive());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
