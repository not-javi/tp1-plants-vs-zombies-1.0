package pvz.logic.factories;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import pvz.logic.gameobjects.Plant;
import pvz.logic.gameobjects.Peashooter;
import pvz.logic.gameobjects.Sunflower;
import pvz.logic.gameobjects.WallNut;
import pvz.logic.gameobjects.CherryBomb;

import pvz.logic.GameWorld;
import utils.Position;

public class PlantFactory {

	private static final List<Plant> AVAILABLE_PLANTS = Arrays.asList(new Sunflower(null, null),
			new Peashooter(null, null), new WallNut(null, null), new CherryBomb(null,null));

	public static Plant spawnPlant(String plantName, GameWorld game, Position pos) {
		for (Plant aux : AVAILABLE_PLANTS) {
			if (aux.matchGameObjectName(plantName)) {
				return aux.spawn(pos, game);
			}
		}
		return null;
	}

	public static List<Plant> getAvailablePlants() {
		return Collections.unmodifiableList(AVAILABLE_PLANTS);
	}
}