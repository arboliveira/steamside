package br.com.arbo.steamside.types;

import org.eclipse.jdt.annotation.NonNull;

public class AppId {

	public final String appid;

	public AppId(@NonNull final String appid) {
		this.appid = appid;
	}

	@Override
	public String toString() {
		return appid;
	}
}
