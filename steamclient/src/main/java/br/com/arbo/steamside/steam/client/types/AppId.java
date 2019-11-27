package br.com.arbo.steamside.steam.client.types;

import java.util.Objects;

public class AppId
{

	public String appid()
	{
		return appid;
	}

	@Override
	public boolean equals(final Object obj)
	{
		return obj instanceof AppId && equalsAppId((AppId) obj);
	}

	public boolean equalsAppId(final AppId other)
	{
		return other.appid.equals(this.appid);
	}

	@Override
	public int hashCode()
	{
		return appid.hashCode();
	}

	@Override
	public String toString()
	{
		return appid;
	}

	public AppId(String appid)
	{
		this.appid = Objects.requireNonNull(appid);
	}

	public final String appid;

}
