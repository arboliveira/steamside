package br.com.arbo.steamside.app.launch;

import java.io.IOException;

import br.com.arbo.steamside.exit.Exit;

class WaitForUserToPressEnterThenExit {

	private static void waitForUserToPressEnter()
	{
		try
		{
			System.in.read();
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	WaitForUserToPressEnterThenExit(Exit exit, String threadName)
	{
		this.exit = exit;
		this.threadName = threadName;
	}

	void go()
	{
		Thread thread = new Thread(this::goXT, threadName);
		thread.setDaemon(true);
		thread.start();
	}

	private void goXT()
	{
		waitForUserToPressEnter();
		exit.exit();
	}

	private final String threadName;
	private final Exit exit;
}