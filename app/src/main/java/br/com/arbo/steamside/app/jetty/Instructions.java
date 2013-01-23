package br.com.arbo.steamside.app.jetty;

import org.apache.log4j.Logger;

final class Instructions {

	Logger getLogger() {
		return Logger.getLogger(this.getClass().getSimpleName());
	}

	static void starting() {
		instructions
				.info(">>> STARTING EMBEDDED JETTY SERVER, PRESS ENTER TO STOP");
	}

	private static final Logger instructions = new Instructions().getLogger();

	public static void started(int PORT) {
		instructions
				.info(">>> STARTED EMBEDDED JETTY SERVER, PRESS ENTER TO STOP");
		instructions.info(">>> TO USE THE WEBSITE, NAVIGATE TO: "
				+ "http://localhost:" + PORT);
	}

}
