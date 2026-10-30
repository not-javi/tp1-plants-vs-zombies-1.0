package pvz.logic;
import utils.Position;

public interface GameStatus {
	int getCycles();
	int getCoins();
	int getRemainingZombies();
	String positionToString(Position pos);
	boolean playerQuits();
	boolean isPlayerWinner();
	boolean isZombiesWinner();
	boolean hasGameFinished();
}
