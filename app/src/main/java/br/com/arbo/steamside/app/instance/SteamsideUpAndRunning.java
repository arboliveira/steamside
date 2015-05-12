package br.com.arbo.steamside.app.instance;

import br.com.arbo.steamside.app.launch.Running;

class SteamsideUpAndRunning extends Exception {

	public SteamsideUpAndRunning(Running running)
	{
		this.running = running;
	}

	public final Running running;

}
