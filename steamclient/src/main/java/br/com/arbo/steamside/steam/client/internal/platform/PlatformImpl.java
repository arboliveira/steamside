package br.com.arbo.steamside.steam.client.internal.platform;

import br.com.arbo.steamside.steam.client.apps.Platform;

public class PlatformImpl implements Platform
{

	@Override
	public String os()
	{
		return os;
	}

	public PlatformImpl(String os)
	{
		this.os = os;
	}

	private final String os;

}
