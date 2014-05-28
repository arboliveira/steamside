package br.com.arbo.steamside.steam.client.localfiles.appcache.entry;

import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.steam.client.types.AppName;
import br.com.arbo.steamside.steam.client.types.AppType;

public class Builder {

	public AppInfo build()
	{
		final AppInfo entry = newEntry();
		executable_set(entry);
		return entry;
	}

	public void executable(final String executable)
	{
		this.executable = executable;
	}

	public boolean executable_missing()
	{
		return executable == null;
	}

	public void name(final AppName appName)
	{
		if (this.appName != null) throw new TwoNames();
		this.appName = appName;
	}

	public void type(AppType appType)
	{
		if (this.appType != null) throw new TwoNames();
		this.appType = appType;
	}

	private void executable_set(final AppInfo entry)
	{
		if (executable != null) entry.executable(executable);
	}

	private AppInfo newEntry()
	{
		if (appName == null)
			throw new InternalStructureChanged_appinfo_vdf();
		if (appType == null)
			throw new InternalStructureChanged_appinfo_vdf();
		final AppInfo entry = new AppInfo(appName, appType);
		return entry;
	}

	@Nullable
	private AppName appName;
	@Nullable
	private AppType appType;
	@Nullable
	private String executable;
}
