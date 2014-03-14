package br.com.arbo.steamside.steam.client.localfiles.monitoring;

import javax.inject.Inject;

public class DigestOnChange implements ChangeListener {

	@Inject
	private AutoreloadingAppsHomeFactory appsHome;

	@Override
	public void fileChanged() {
		appsHome.submit();
	}

}
