package pvz.control.command;

import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;

public class ExitCommand extends Command{
	
	protected ExitCommand() {
		this.name = Messages.COMMAND_EXIT_NAME;
		this.shortcut = Messages.COMMAND_EXIT_SHORTCUT;
		this.details = Messages.COMMAND_EXIT_DETAILS;
		this.help = Messages.COMMAND_EXIT_HELP;
	}
	
	@Override
	public void execute(GameModel game, GameView view) {
		game.quit();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
