package br.com.arbo.steamside.app.launch;

import org.apache.log4j.Logger;

import br.com.arbo.steamside.app.port.Port;

final class Instructions {

	void started(final Port PORT)
	{
		getLogger().info(
			">>> STARTED EMBEDDED WEB SERVER, PRESS ENTER TO STOP");
		getLogger().info(
			">>> APPLICATION READY TO USE: " + "http://localhost:" + PORT.port);
	}

	void starting()
	{
		getLogger()
			.info(">>> STARTING EMBEDDED WEB SERVER, PRESS ENTER TO STOP");
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass());
	}

}
