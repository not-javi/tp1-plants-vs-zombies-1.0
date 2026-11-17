package pvz.control.command;

import pvz.logic.GameModel;
import pvz.logic.factories.ZombieFactory;
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
	    Command error = checkWords(words, 4);
	    if (error != null) return error;
	    try {
	        int col = Integer.parseInt(words[2]);
	        int row = Integer.parseInt(words[3]);
	        return new AddZombieCommand(words[1], new Position(row, col));
	    } catch (NumberFormatException nfe) {
	        return new ErrorCommand(Messages.INVALID_COMMAND);
	    }
	}

	@Override
	public void execute(GameModel game, GameView view) {
		if (!game.isValidPosition(pos)) {
			view.showError(Messages.INVALID_POSITION);
		} else if (ZombieFactory.spawnZombie(zombieName, null, pos) == null) {
			view.showError(Messages.INVALID_GAME_OBJECT);
		} else if (!game.addGameObject(zombieName, pos)) {
			view.showError(Messages.INVALID_POSITION);
		} else {
			game.update();
			view.showGame();
		}
	}

}
