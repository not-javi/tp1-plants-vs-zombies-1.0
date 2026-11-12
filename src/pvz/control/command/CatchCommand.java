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
		if (words.length == 3) {
			try {
				int col = Integer.parseInt(words[1]);
				int row = Integer.parseInt(words[2]);
				Position pos = new Position(row, col);
				return new CatchCommand(pos);
			} catch (NumberFormatException nfe) {
				return null;
			}
		}
		return null;
	}

	@Override
	public void execute(GameModel game, GameView view) {
		int error = game.catchCommand(pos);
		switch (error) {
		case 0:
			view.showGame();
			break;
		case 1:
			view.showError(Messages.INVALID_POSITION);
			break;
		case 2:
			view.showError(Messages.NO_CATCHABLE_IN_POSITION + " " + pos.positionToString());
			break;
		case 3:
			view.showError(Messages.ALREADY_CAUGHT_SUN);
			break;
		}
	}

}
