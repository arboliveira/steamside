package br.com.arbo.steamside.steam.client.localfiles.appcache.entry;

import java.util.Optional;

import br.com.arbo.steamside.steam.client.types.AppName;
import br.com.arbo.steamside.steam.client.types.AppType;

public class AppInfo
{

	public String executable() throws NotAvailableOnThisPlatform
	{
		return executable.orElseThrow(NotAvailableOnThisPlatform::new);
	}

	public void executable(String executable)
	{
		this.executable = Optional.of(executable);
	}

	public AppName name()
	{
		return name;
	}

	@Override
	public String toString()
	{
		return type + "," + executable.orElse("(noexecutable)") + "," + name;
	}

	public AppType type()
	{
		return type;
	}

	public AppInfo(AppName appName, AppType appType)
	{
		this.name = appName;
		this.type = appType;
	}

	private Optional<String> executable = Optional.empty();

	private final AppName name;

	private final AppType type;

}
