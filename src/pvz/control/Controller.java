package pvz.control;

import pvz.logic.Game;
import pvz.view.GamePrinter;
import pvz.view.GameView;
import pvz.view.Messages;
import utils.Position;

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

	private final Game game;
	private final GameView view;

	public Controller(Game game) {
		this.game = game;
		this.view = new GamePrinter(game);
	}

	/**
	 * Runs the game logic.
	 */
	public void run() {
		view.showGame();
		while (!game.hasGameFinished()) {
			String words[] = view.getPrompt();
			executeCommand(words);
		}
		view.showEndMessage();

	}
	
	public void executeCommand(String[] words) {
		switch (words[0]) {
		case Messages.ADD:
		case Messages.SHORT_ADD:
			executeAddCommand(words);
			break;
			
		case Messages._LIST:
		case Messages.SHORT_LIST:
			view.showMessage(game.getPlantsList());
			break;
			
		case Messages.RESET:
		case Messages.SHORT_RESET:
			game.reset();
			view.showGame();
			break;
			
		case Messages._HELP:
		case Messages.SHORT_HELP:
			for (int i = 0; i < Messages.HELP_LINES.length; i++) {
			view.showMessage(Messages.HELP_LINES[i]);
			}
			break;
			
		case Messages.EXIT:
		case Messages.SHORT_EXIT:
			game.quit();
			view.showEndMessage();
			break;
			
		case Messages.NONE:
		case Messages.SHORT_NONE:
		case Messages.NO_STRING:
			game.update();
			view.showGame();
			break;
		
		default:
			view.showError(Messages.UNKNOWN_COMMAND);
			break;
		}
	}
	
	private void executeAddCommand(String[] words) {
		if (words.length != 4) {
			view.showError(Messages.COMMAND_PARAMETERS_MISSING);
			return;
		}
		try {
			String plantName = words[1];
			int col = Integer.parseInt(words[2]);
			int row = Integer.parseInt(words[3]);
			Position pos = new Position(row,col);
			 switch (game.addGameObject(plantName, pos)) {
			 case 0:
				 game.update();
				 view.showGame();
				 break;
			 case 1:
				 view.showError(Messages.INVALID_POSITION);
				 break;
			 case 2:
				 view.showError(Messages.NOT_ENOUGH_COINS);
				 break;
			 case 3:
				 view.showError(Messages.INVALID_GAME_OBJECT);
				 break;
			 }
		} catch (NumberFormatException nfe) {
			view.showError(Messages.INVALID_POSITION);
		}
	}
	
	
	
	
}