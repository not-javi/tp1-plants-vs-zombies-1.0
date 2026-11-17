package pvz.control.command;

import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;
import pvz.logic.factories.PlantFactory;
import utils.Position;

public class AddPlantCommand extends Command {

	private Position pos;

	private String plantName;

	public AddPlantCommand() {
		this.name = Messages.COMMAND_ADD_PLANT_NAME;
		this.shortcut = Messages.COMMAND_ADD_PLANT_SHORTCUT;
		this.details = Messages.COMMAND_ADD_PLANT_DETAILS;
		this.help = Messages.COMMAND_ADD_PLANT_HELP;
	}

	protected AddPlantCommand(String plantName, Position pos) {
		this();
		this.plantName = plantName;
		this.pos = pos;
	}

	@Override
	public Command create(String[] words) {
	    Command error = checkWords(words, 4);
	    if (error != null) return error;
	    try {
	        int col = Integer.parseInt(words[2]);
	        int row = Integer.parseInt(words[3]);
	        return new AddPlantCommand(words[1], new Position(row, col));
	    } catch (NumberFormatException nfe) {
	        return new ErrorCommand(Messages.INVALID_COMMAND);
	    }
	}

	@Override
	public void execute(GameModel game, GameView view) {
		if (!game.isValidPosition(pos)) {
			view.showError(Messages.INVALID_POSITION);
		} else if (PlantFactory.spawnPlant(plantName, null, pos) == null) {
			view.showError(Messages.INVALID_GAME_OBJECT);
		} else if (!game.addGameObject(plantName, pos)) {
			view.showError(Messages.NOT_ENOUGH_COINS);
		} else {
			game.update();
			view.showGame();
		}
	}

}
