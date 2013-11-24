package br.com.arbo.steamside.app.main;

import br.com.arbo.steamside.app.injection.Container;
import br.com.arbo.steamside.exit.Exit;

public class ContainerStop implements Exit {

	Container c;

	ContainerStop(final Container c) {
		this.c = c;
	}

	@Override
	public void exit() {
		c.stop();
	}

}