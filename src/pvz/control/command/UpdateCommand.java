package pvz.control.command;

import pvz.view.Messages;
import pvz.view.GameView;
import pvz.logic.GameModel;

public class UpdateCommand extends Command{
	
	protected UpdateCommand() {
		this.name = Messages.COMMAND_UPDATE_NAME;
		this.shortcut = Messages.COMMAND_UPDATE_SHORTCUT;
		this.details = Messages.COMMAND_UPDATE_DETAILS;
		this.help = Messages.COMMAND_UPDATE_HELP;
	}
	
	@Override
	protected boolean matchCommand(String com) {
		return (super.matchCommand(com) || com.equals(""));
	}
	
	@Override
	public void execute (GameModel game, GameView view) {
		game.update();
		view.showGame();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
