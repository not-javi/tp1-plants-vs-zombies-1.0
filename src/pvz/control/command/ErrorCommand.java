package pvz.control.command;

import pvz.logic.GameModel;
import pvz.view.GameView;

public class ErrorCommand extends Command {
	
	private final String message;

	protected ErrorCommand(String message) {
		this.message = message;
	}

	@Override
	public void execute(GameModel game, GameView view) {
		view.showError(message);
	}
}
