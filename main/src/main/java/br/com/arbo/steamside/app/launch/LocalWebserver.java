package br.com.arbo.steamside.app.launch;

import br.com.arbo.steamside.app.port.PortAlreadyInUse;

public interface LocalWebserver
{

	Running launch(int port) throws PortAlreadyInUse;

}
