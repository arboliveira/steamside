package br.com.arbo.steamside.app.launch;

import br.com.arbo.steamside.app.port.Port;
import br.com.arbo.steamside.exit.Exit;

public class LaunchSequence {

	public static void launch(Port port, Exit exit, Runnable callback)
	{
		Instructions instructions = new Instructions();
		instructions.starting();
		callback.run();
		waitForUserToPressEnterAndExit(exit);
		instructions.started(port);
	}

	private static void waitForUserToPressEnterAndExit(Exit exit)
	{
		new WaitForUserToPressEnterThenExit(exit,
			"Press Enter to exit SteamSide")
				.go();
	}
}
