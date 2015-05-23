package br.com.arbo.steamside.container;

import javax.inject.Inject;

import br.com.arbo.steamside.steam.client.localfiles.monitoring.ChangeListener;

public class DigestOnChange implements ChangeListener {

	@Override
	public void fileChanged()
	{
		appsHome.submit();
	}

	@Inject
	private ParallelAppsHomeFactory appsHome;

}
