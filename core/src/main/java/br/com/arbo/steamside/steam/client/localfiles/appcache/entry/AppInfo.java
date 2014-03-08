package br.com.arbo.steamside.steam.client.localfiles.appcache.entry;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.types.AppName;

public class AppInfo {

	public AppInfo(@NonNull final AppName appName) {
		this.name = appName;
	}

	@NonNull
	public AppName name() {
		return name;
	}

	public void executable(@NonNull final String executable) {
		this.executable = executable;
	}

	@NonNull
	public String executable() {
		final String _executable = executable;
		if (_executable == null) throw new NotAvailableOnThisPlatform();
		return _executable;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SIMPLE_STYLE);
	}

	@NonNull
	private final AppName name;
	@Nullable
	private String executable;

}
