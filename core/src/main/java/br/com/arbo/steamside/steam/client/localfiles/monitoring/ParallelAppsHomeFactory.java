package br.com.arbo.steamside.steam.client.localfiles.monitoring;

import javax.inject.Inject;

import br.com.arbo.java.util.concurrent.Parallel;
import br.com.arbo.steamside.apps.AppsHome;
import br.com.arbo.steamside.apps.AppsHomeFactory;

public class ParallelAppsHomeFactory extends Parallel<AppsHome>
		implements AppsHomeFactory {

	@Inject
	public ParallelAppsHomeFactory(Digester digester) {
		super(digester);
	}

}
