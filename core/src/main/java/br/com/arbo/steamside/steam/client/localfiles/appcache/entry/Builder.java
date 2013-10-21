package br.com.arbo.steamside.steam.client.localfiles.appcache.entry;

import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.types.AppName;

public class Builder {

	public void name(final AppName appName) {
		if (this.appName != null) throw new TwoNames();
		this.appName = appName;
	}

	public void executable(final String executable) {
		this.executable = executable;
	}

	public boolean executable_missing() {
		return executable == null;
	}

	public AppInfo build() {
		final AppInfo entry = newEntry();
		executable_set(entry);
		return entry;
	}

	private void executable_set(final AppInfo entry) {
		final String executable2 = executable;
		if (executable2 != null) entry.executable(executable2);
	}

	private AppInfo newEntry() {
		final AppName appName2 = appName;
		if (appName2 == null)
			throw new InternalStructureChanged_appinfo_vdf();
		final AppInfo entry = new AppInfo(appName2);
		return entry;
	}

	@Nullable
	private AppName appName;
	@Nullable
	private String executable;
}
