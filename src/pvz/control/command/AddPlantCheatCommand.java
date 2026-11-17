package pvz.control.command;

import pvz.logic.GameModel;
import pvz.logic.factories.PlantFactory;
import pvz.view.GameView;
import pvz.view.Messages;
import utils.Position;

public class AddPlantCheatCommand extends Command {

	private Position pos;

	private String plantName;

	public AddPlantCheatCommand() {
		this.name = Messages.COMMAND_CHEAT_PLANT_NAME;
		this.shortcut = Messages.COMMAND_CHEAT_PLANT_SHORTCUT;
		this.details = Messages.COMMAND_CHEAT_PLANT_DETAILS;
		this.help = Messages.COMMAND_CHEAT_PLANT_HELP;
	}

	protected AddPlantCheatCommand(String plantName, Position pos) {
		this();
		this.plantName = plantName;
		this.pos = pos;
	}

	@Override
	public Command create(String[] words) {
		Command error = checkWords(words,4);
		if (error != null) return error;
			try {
				String plant = words[1];
				int col = Integer.parseInt(words[2]);
				int row = Integer.parseInt(words[3]);
				return new AddPlantCheatCommand(plant, new Position(row, col));
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
		} else {
			game.addGameObjectForFree(plantName, pos);
			game.update();
			view.showGame();
		}
	}

}
