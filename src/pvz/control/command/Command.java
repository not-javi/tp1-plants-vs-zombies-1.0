package pvz.control.command;

import java.util.List;
import java.util.Arrays;
import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;

public abstract class Command {
	private static final List<Command> AVAILABLE_COMMANDS = Arrays.asList(new AddPlantCommand(), new AddZombieCommand(),
			new AddPlantCheatCommand(), new CatchCommand(), new ListPlantsCommand(), new ListZombiesCommand(),
			new ResetCommand(), new HelpCommand(), new ExitCommand(), new UpdateCommand());

	protected String name = "";
	protected String shortcut = "";
	protected String details = "";
	protected String help = "";

	public static Command parse(String[] commandWords) {
		for (Command command : AVAILABLE_COMMANDS) {
			if (command.matchCommand(commandWords[0])) {
				return command.create(commandWords);
			}
		}
		return new UnknownCommand();
	}

	protected boolean matchCommand(String com) {
		return (com.equalsIgnoreCase(name) || com.equalsIgnoreCase(shortcut));
	}

	public Command create(String[] words) {
		if (words.length == 1) {
			return this;
		}
		return new ErrorCommand(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
	}

	protected static Command checkWords(String[] words, int expected) {
		if (words.length < expected)
			return new ErrorCommand(Messages.COMMAND_PARAMETERS_MISSING);
		if (words.length > expected)
			return new ErrorCommand(Messages.INVALID_COMMAND);
		return null;
	}

	public abstract void execute(GameModel game, GameView view);

	protected static String buildHelpText() {
		StringBuilder text = new StringBuilder();
		text.append(Messages.HELP_AVAILABLE_COMMANDS).append(System.lineSeparator());
		for (Command command : AVAILABLE_COMMANDS) {
			text.append(command.details);
			text.append(": ");
			text.append(command.help);
			text.append(System.lineSeparator());
		}
		return text.toString();
	}

	protected String getName() {
		return name;
	}

	protected String getShortcut() {
		return shortcut;
	}

	public String getDetails() {
		return details;
	}

	public String getHelp() {
		return help;
	}
}
