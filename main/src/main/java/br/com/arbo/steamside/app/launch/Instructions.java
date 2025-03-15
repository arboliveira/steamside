package br.com.arbo.steamside.app.launch;

import br.com.arbo.steamside.app.port.Port;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

	private Log getLogger()
	{
		return LogFactory.getLog(this.getClass());
	}

}
