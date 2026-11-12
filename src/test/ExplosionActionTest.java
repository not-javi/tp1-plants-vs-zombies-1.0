package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pvz.logic.actions.ExplosionAction;
import pvz.logic.actions.GameAction;
import test.FakeGameWorld;
import test.FakeGameWorld.Attack;
import utils.Position;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * An {@link ExplosionAction} attacks a set of positions: zombies when it comes
 * from a plant, plants when it comes from a zombie.
 */
class ExplosionActionTest {

	private FakeGameWorld world;

	@BeforeEach
	void setUp() {
		world = new FakeGameWorld();
	}

	@Test
	void plantExplosion_attacksZombiesInEachPosition() {
		List<Position> positions = Arrays.asList(new Position(0,1), new Position (1,2));
		GameAction action = new ExplosionAction(10, true, positions);

		action.execute(world);
		
		assertEquals(List.of(new Attack(new Position(0, 1), 10), new Attack(new Position(1, 2), 10)),
				world.plantAttacks);
		assertTrue(world.zombieAttacks.isEmpty());
	}

	@Test
	void zombieExplosion_attacksPlantsInEachPosition() {
		List<Position> positions = Arrays.asList(new Position(2, 2), new Position(3, 3));
		GameAction action = new ExplosionAction(3, false, positions);

		action.execute(world);

		assertEquals(List.of(new Attack(new Position(2, 2), 3), new Attack(new Position(3, 3), 3)),
				world.zombieAttacks);
		assertTrue(world.plantAttacks.isEmpty());
	}

	@Test
	void withoutPositions_nothingHappens() {
		List<Position> positions = Arrays.asList();
		new ExplosionAction(10, true, positions).execute(world);
		assertTrue(world.plantAttacks.isEmpty());
		assertTrue(world.zombieAttacks.isEmpty());
	}

	@Test
	void executingTwice_attacksTwice() {
		List<Position> positions = Arrays.asList(new Position(0, 0));
		GameAction action = new ExplosionAction(3, false, positions);
		action.execute(world);
		action.execute(world);
		assertEquals(2, world.zombieAttacks.size());
	}

	@Test
	void explosion_canTriggerFurtherActions_throughTheWorld() {
		// an action may push new actions into the world; the game runs them afterwards
		List<Position> positions = Arrays.asList(new Position(0, 0));
		GameAction chained = w -> w.pushAction(new ExplosionAction(1, true, positions));

		chained.execute(world);

		assertEquals(1, world.pushedActions.size());
		assertInstanceOf(ExplosionAction.class, world.pushedActions.get(0));
	}
}
