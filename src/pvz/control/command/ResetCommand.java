package pvz.control.command;

import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;
import pvz.control.Level;

public class ResetCommand extends Command {

	private Level level;

	private long seed;

	protected ResetCommand() {
		this.name = Messages.COMMAND_RESET_NAME;
		this.shortcut = Messages.COMMAND_RESET_SHORTCUT;
		this.details = Messages.COMMAND_RESET_DETAILS;
		this.help = Messages.COMMAND_RESET_HELP;
		level = null;
	}

	protected ResetCommand(Level level, long seed) {
		this();
		this.level = level;
		this.seed = seed;
	}

	@Override
	public void execute(GameModel game, GameView view) {
		if (level == null)
			game.reset();
		else
			game.reset(level, seed);
		view.showGame();
	}

	@Override
	public Command create(String[] words) {
		if (words.length == 1)
			return this;
		if (words.length == 3) {
			Level level = Level.valueOfIgnoreCase(words[1]);
			if (level == null)
				return null;
			try {
				return new ResetCommand(level, Long.parseLong(words[2]));
			} catch (NumberFormatException nfe) {
				return null;
			}
		}
		return null;
	}

}
