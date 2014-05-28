package br.com.arbo.steamside.steam.client.localfiles.appcache.entry;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.steam.client.types.AppName;
import br.com.arbo.steamside.steam.client.types.AppType;

public class AppInfo {

	public AppInfo(
			@NonNull final AppName appName,
			@NonNull AppType appType) {
		this.name = appName;
		this.type = appType;
	}

	public String appnametype()
	{
		final String name = name().name;
		final String type = type().type;
		if ("game".equals(type)) return name;
		return name + " (" + type + ")";
	}

	@NonNull
	public String executable() throws NotAvailableOnThisPlatform
	{
		final String _executable = executable;
		if (_executable == null) throw new NotAvailableOnThisPlatform();
		return _executable;
	}

	public void executable(@NonNull final String executable)
	{
		this.executable = executable;
	}

	@NonNull
	public AppName name()
	{
		return name;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SIMPLE_STYLE);
	}

	@NonNull
	public AppType type()
	{
		return type;
	}

	@Nullable
	private String executable;

	@NonNull
	private final AppName name;

	@NonNull
	private final AppType type;

}
