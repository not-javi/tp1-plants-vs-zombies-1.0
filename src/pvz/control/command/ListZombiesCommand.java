package pvz.control.command;

import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;

public class ListZombiesCommand extends Command{
	
	protected ListZombiesCommand() {
		this.name = Messages.COMMAND_LIST_ZOMBIES_NAME;
		this.shortcut = Messages.COMMAND_LIST_ZOMBIES_SHORTCUT;
		this.details = Messages.COMMAND_LIST_ZOMBIES_DETAILS;
		this.help = Messages.COMMAND_LIST_ZOMBIES_HELP;
	}
	
	@Override
	public void execute(GameModel game, GameView view) {
		view.showMessage(game.getZombiesList());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
