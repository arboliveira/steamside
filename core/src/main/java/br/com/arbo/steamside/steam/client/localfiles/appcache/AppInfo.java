package br.com.arbo.steamside.steam.client.localfiles.appcache;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import br.com.arbo.steamside.types.AppName;

public class AppInfo {

	AppName name;
	String executable;

	public AppName name() {
		return name;
	}

	public String executable() {
		return executable;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SIMPLE_STYLE);
	}
}
