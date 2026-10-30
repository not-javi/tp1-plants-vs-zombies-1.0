package pvz.control;

import pvz.logic.GameModel;
import pvz.logic.GameStatus;
import pvz.view.GamePrinter;
import pvz.view.GameView;
import pvz.view.Messages;
import pvz.control.command.Command;

/**
 * Input/output coordinator of the game (the C in MVC).
 *
 * <p>Owns the game loop: reads a line from stdin, parses it into a
 * command, validates parameters (plant type, position), delegates
 * state changes to {@link Game}, and triggers a board reprint via
 * {@link tp1.pvz.view.GameView>} when the cycle advances. It holds no game
 * state of its own; the source of truth is always {@link Game}.
 */
public class Controller {

	private final GameModel gameModel;
	private final GameStatus gameStatus;
	private final GameView view;

	public Controller(GameModel gameModel, GameStatus gameStatus) {
		this.gameModel = gameModel;
		this.gameStatus = gameStatus;
		this.view = new GamePrinter(gameStatus);
	}

	/**
	 * Runs the game logic.
	 */
	public void run() {
		view.showGame();
		while (!gameStatus.hasGameFinished() && !gameStatus.playerQuits()) {
			String words[] = view.getPrompt();
			Command command = Command.parse(words);
			if (command != null) {
				command.execute(gameModel, view);
			} 
			else {
				view.showError(Messages.UNKNOWN_COMMAND);
			}
			
		}
		view.showEndMessage();
	}
	
	
	
	
	
	
}