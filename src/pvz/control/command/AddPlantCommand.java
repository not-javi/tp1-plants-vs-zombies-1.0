package pvz.control.command;

import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;
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
		if (words.length == 4) {
			try {
				String plant = words[1];
				int col = Integer.parseInt(words[2]);
				int row = Integer.parseInt(words[3]);
				Position pos = new Position(row, col);
				return new AddPlantCommand(plant, pos);
			} catch (NumberFormatException nfe) {
				return null;
			}
		}
		return null;
	}

	@Override
	public void execute(GameModel game, GameView view) {
		int error = game.addPlant(plantName, pos);
		switch (error) {
		case 0:
			game.update();
			view.showGame();
			break;
		case 1:
			view.showError(Messages.INVALID_POSITION);
			break;
		case 2:
			view.showError(Messages.NOT_ENOUGH_COINS);
			break;
		case 3:
			view.showError(Messages.INVALID_GAME_OBJECT);
			break;
		case 4:
			break;
		}
	}

}
