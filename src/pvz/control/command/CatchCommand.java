package pvz.control.command;

import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;
import utils.Position;

public class CatchCommand extends Command {

	private Position pos;

	public CatchCommand() {
		this.name = Messages.COMMAND_CATCH_NAME;
		this.shortcut = Messages.COMMAND_CATCH_SHORTCUT;
		this.details = Messages.COMMAND_CATCH_DETAILS;
		this.help = Messages.COMMAND_CATCH_HELP;
	}

	protected CatchCommand(Position pos) {
		this();
		this.pos = pos;
	}

	@Override
	public Command create(String[] words) {
		Command error = checkWords(words, 3);
		if (error != null)
			return error;
		try {
			int col = Integer.parseInt(words[1]);
			int row = Integer.parseInt(words[2]);
			return new CatchCommand(new Position(row, col));
		} catch (NumberFormatException nfe) {
			return new ErrorCommand(Messages.INVALID_COMMAND);
		}
	}

	@Override
	public void execute(GameModel game, GameView view) {
		if (!game.isInsideBoard(pos)) {
			view.showError(Messages.INVALID_POSITION);
		} else if (game.isSunAlreadyCaught()) {
			view.showError(Messages.ALREADY_CAUGHT_SUN);
		} else if (!game.catchObject(pos)) {
			view.showError(Messages.NO_CATCHABLE_IN_POSITION.formatted(pos.column(), pos.row()));
		} else {
			view.showGame();
		}
	}

}
