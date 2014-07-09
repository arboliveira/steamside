package br.com.arbo.steamside.steam.client.types;

import org.eclipse.jdt.annotation.NonNull;

public class AppId {

	@NonNull
	public final String appid;

	public AppId(@NonNull final String appid) {
		this.appid = appid;
	}

	@Override
	public String toString() {
		return appid;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof AppId && equalsAppId((AppId) obj);
	}

	@Override
	public int hashCode() {
		return appid.hashCode();
	}

	@SuppressWarnings("null")
	@NonNull
	public String appid() {
		return appid;
	}

	public boolean equalsAppId(final AppId other) {
		return other.appid.equals(this.appid);
	}

}