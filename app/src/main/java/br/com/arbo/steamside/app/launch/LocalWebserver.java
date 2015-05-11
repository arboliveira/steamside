package br.com.arbo.steamside.app.launch;

import br.com.arbo.steamside.app.port.PortAlreadyInUse;

public interface LocalWebserver {

	/**
	 * Has to return after the web server is already listening,
	 * so the caller can open the web browser pointing to the given port. 
	 */
	void launch(int port) throws PortAlreadyInUse;

	void stop();
}
