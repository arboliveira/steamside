package br.com.arbo.steamside.app.launch;

import br.com.arbo.steamside.app.port.PortAlreadyInUse;

public interface LocalWebserver {

	/**
	 * Must block until the web server is actually listening,
	 * so the caller can open the web browser pointing to the given port.
	 */
	Running launch(int port) throws PortAlreadyInUse;

}
