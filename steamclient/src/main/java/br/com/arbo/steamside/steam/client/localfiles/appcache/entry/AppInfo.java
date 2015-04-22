package br.com.arbo.steamside.steam.client.localfiles.appcache.entry;

import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import br.com.arbo.steamside.steam.client.types.AppName;
import br.com.arbo.steamside.steam.client.types.AppType;

public class AppInfo {

	public AppInfo(AppName appName, AppType appType)
	{
		this.name = appName;
		this.type = appType;
	}

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
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SIMPLE_STYLE);
	}

	public AppType type()
	{
		return type;
	}

	private Optional<String> executable = Optional.empty();

	private final AppName name;

	private final AppType type;

}
