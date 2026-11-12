package pvz.control.command;

import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;
import utils.Position;

public class AddZombieCommand extends Command {

	private Position pos;

	private String zombieName;

	public AddZombieCommand() {
		this.name = Messages.COMMAND_ADD_ZOMBIE_NAME;
		this.shortcut = Messages.COMMAND_ADD_ZOMBIE_SHORTCUT;
		this.details = Messages.COMMAND_ADD_ZOMBIE_DETAILS;
		this.help = Messages.COMMAND_ADD_ZOMBIE_HELP;
	}

	protected AddZombieCommand(String zombieName, Position pos) {
		this();
		this.zombieName = zombieName;
		this.pos = pos;
	}

	@Override
	public Command create(String[] words) {
		if (words.length == 4) {
			try {
				String zombie = words[1];
				int col = Integer.parseInt(words[2]);
				int row = Integer.parseInt(words[3]);
				Position pos = new Position(row, col);
				return new AddZombieCommand(zombie, pos);
			} catch (NumberFormatException nfe) {
				return null;
			}
		}
		return null;
	}

	@Override
	public void execute(GameModel game, GameView view) {
		int error = game.addZombie(zombieName, pos);
		switch (error) {
		case 0:
			game.update();
			view.showGame();
			break;
		case 1:
			view.showError(Messages.INVALID_POSITION);
			break;
		case 2:
			view.showError(Messages.INVALID_GAME_OBJECT);
			break;
		}
	}

}
