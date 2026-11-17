package pvz.control.command;

import pvz.view.Messages;
import pvz.logic.GameModel;
import pvz.view.GameView;

public class UnknownCommand extends Command {
	
	@Override
    public void execute(GameModel game, GameView view) {
        view.showError(Messages.UNKNOWN_COMMAND);
    }
}
