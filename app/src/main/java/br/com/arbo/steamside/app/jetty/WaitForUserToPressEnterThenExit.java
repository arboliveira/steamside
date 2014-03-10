package br.com.arbo.steamside.app.jetty;

import java.io.IOException;

import br.com.arbo.steamside.exit.Exit;

class WaitForUserToPressEnterThenExit implements Runnable {

	@Override
	public void run() {
		waitForUserToPressEnter();
		exit.exit();
	}

	private static void waitForUserToPressEnter() {
		try {
			System.in.read();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	WaitForUserToPressEnterThenExit(Exit exit) {
		super();
		this.exit = exit;
	}

	private final Exit exit;
}