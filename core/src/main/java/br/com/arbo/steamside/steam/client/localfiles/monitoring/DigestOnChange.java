package br.com.arbo.steamside.steam.client.localfiles.monitoring;

import javax.inject.Inject;

public class DigestOnChange implements ChangeListener {

	@Inject
	private ParallelAppsHomeFactory appsHome;

	@Override
	public void fileChanged() {
		appsHome.submit();
	}

}
