package pvz.logic;
import utils.Position;

public interface GameStatus {
	int getCycles();
	int getCoins();
	int getRemainingZombies();
	String positionToString(Position pos);
	boolean playerQuits();
	boolean playerWins();
	boolean doZombiesReachedTheHouse();
	boolean hasGameFinished();
	int getCaughtCoins();
	int getGeneratedCoins();
}
