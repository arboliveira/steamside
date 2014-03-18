package br.com.arbo.steamside.steam.client.localfiles.monitoring;

import javax.inject.Inject;

import br.com.arbo.java.util.concurrent.Parallel;
import br.com.arbo.steamside.apps.AppsHome;
import br.com.arbo.steamside.apps.AppsHomeFactory;
import br.com.arbo.steamside.steam.client.localfiles.digest.Digester;

public class ParallelAppsHomeFactory extends Parallel<AppsHome>
		implements AppsHomeFactory {

	@Inject
	public ParallelAppsHomeFactory(Digester digester) {
		super(digester::digest);
	}

	public void start() {
		this.submit();
	}

}
