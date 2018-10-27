package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import java.util.Optional;

import br.com.arbo.steamside.steam.client.types.AppName;
import br.com.arbo.steamside.steam.client.types.AppType;

public class Builder
{

	public AppInfo build()
	{
		final AppInfo entry = newEntry();
		executable_set(entry);
		return entry;
	}

	public void executable(Optional<String> executable)
	{
		this.executable = executable;
	}

	public boolean executable_missing()
	{
		return !executable.isPresent();
	}

	public void name(final AppName appName)
	{
		if (this.appName.isPresent()) throw new TwoNames();
		this.appName = Optional.of(appName);
	}

	public void type(AppType appType)
	{
		if (this.appType.isPresent()) throw new TwoNames();
		this.appType = Optional.of(appType);
	}

	private void executable_set(final AppInfo entry)
	{
		executable.ifPresent(entry::executable);
	}

	private AppInfo newEntry()
	{
		return new AppInfo(
			appName.orElseGet(this::noName),
			appType.orElseGet(this::noType));
	}

	private AppName noName()
	{
		return new AppName("(noname)");
	}

	private AppType noType()
	{
		return AppType.valueOf("(notype)");
	}

	private Optional<AppName> appName = Optional.empty();

	private Optional<AppType> appType = Optional.empty();

	private Optional<String> executable = Optional.empty();
}
