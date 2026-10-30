package pvz.control.command;

import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;


public class HelpCommand extends Command{
	
	protected HelpCommand() {
		this.name = Messages.COMMAND_HELP_NAME;
		this.shortcut = Messages.COMMAND_HELP_SHORTCUT;
		this.details = Messages.COMMAND_HELP_DETAILS;
		this.help = Messages.COMMAND_HELP_HELP;
	}
	
	@Override
	public void execute(GameModel game, GameView view) {
		view.showMessage(buildHelpText());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
