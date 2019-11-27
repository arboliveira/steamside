package br.com.arbo.steamside.steam.client.platform;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.Platform;

public class PlatformCheck
{

	public PlatformCheck app(App app)
	{
		this.app = app;
		return this;
	}

	public boolean isAvailable()
	{
		return app.executable(platform).isPresent();
	}

	public PlatformCheck platform(Platform platform)
	{
		this.platform = platform;
		return this;
	}

	private App app;
	private Platform platform;
}
