package br.com.arbo.steamside.webui.wicket.collection;

public enum Command {
	JSON;

	public static Command of(final String command) {
		if (equalsCommand("apps.json", command))
			return Command.JSON;
		return null;
	}

	private static boolean equalsCommand(final String string,
			final String command) {
		return string.equals(command);
	}
}