package br.com.arbo.steamside.steam.client.localfiles.appcache;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import br.com.arbo.steamside.types.AppName;

public class AppInfo {

	public AppName name() {
		return name;
	}

	public String executable() throws NotAvailableOnThisPlatform {
		if (executable == null) throw new NotAvailableOnThisPlatform();
		return executable;
	}

	public static class NotAvailableOnThisPlatform extends Exception {
		//
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SIMPLE_STYLE);
	}

	AppName name;
	String executable;

}
