package br.com.arbo.steamside.app.launch;

import java.util.function.Function;

import br.com.arbo.steamside.app.port.Port;
import br.com.arbo.steamside.exit.Exit;

public class LaunchSequence {

	public static Running launch(
		Port port, Exit exit, Function<Port, Running> launch)
	{
		Instructions instructions = new Instructions();
		instructions.starting();
		Running running = launch.apply(port);
		waitForUserToPressEnterAndExit(exit);
		instructions.started(port);
		return running;
	}

	private static void waitForUserToPressEnterAndExit(Exit exit)
	{
		new WaitForUserToPressEnterThenExit(exit,
			"Press Enter to exit SteamSide")
				.go();
	}
}
