package br.com.arbo.steamside.app.jetty;

import br.com.arbo.steamside.app.injection.ContainerWeb;

public interface WebApplicationContextTweak {

	void tweak(ContainerWeb cx);

}
