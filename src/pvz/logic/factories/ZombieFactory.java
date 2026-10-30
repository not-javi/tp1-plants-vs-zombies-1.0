package pvz.logic.factories;

import java.util.List;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;

import pvz.logic.gameobjects.Zombie;
import pvz.logic.gameobjects.CommonZombie;


import pvz.logic.GameWorld;
import utils.Position;

public class ZombieFactory {

	private static final List<Zombie> AVAILABLE_ZOMBIES = Arrays.asList(
			new CommonZombie(null, null)
			);

	public static Zombie spawnZombie(String zombieName, GameWorld game, Position position) {
		for (Zombie zombie : AVAILABLE_ZOMBIES) {
			if (zombie.matchGameObjectName(zombieName)) {
				return (Zombie) zombie.spawn(position, game);
			}
		}
		return null;
	}

	public static List<Zombie> getAvailableZombies() {
		return Collections.unmodifiableList(AVAILABLE_ZOMBIES);
	}
	
	public static Zombie spawnRandomZombie(GameWorld game, Position position, Random rand) {
		int randomIndex = rand.nextInt(AVAILABLE_ZOMBIES.size());
		Zombie prototype = AVAILABLE_ZOMBIES.get(randomIndex);
	    return (Zombie) prototype.spawn(position, game);
	}
}
