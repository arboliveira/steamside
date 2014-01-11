package br.com.arbo.steamside.app.main;

import br.com.arbo.steamside.app.injection.ContainerWeb;
import br.com.arbo.steamside.app.jetty.WebApplicationContextTweak;

public class NoTweak implements WebApplicationContextTweak {

	@Override
	public void tweak(ContainerWeb cx) {
		// Nothing special.
	}

}
