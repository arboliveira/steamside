package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import br.com.arbo.steamside.steam.client.types.AppName;
import br.com.arbo.steamside.steam.client.types.AppType;

public class AppInfo
{

	public Map<String, String> executables()
	{
		return executables;
	}

	public void executables(Map<String, String> executables)
	{
		this.executables = executables;
	}

	public AppName name()
	{
		return name;
	}

	public boolean isPublicOnly()
	{
		return public_only != null;
	}

	@Override
	public String toString()
	{
		if (public_only != null)
		{
			return "public_only[" + public_only + "]";
		}
		return type + "," + name + "," + toString_executables();
	}

	public AppType type()
	{
		return type;
	}

	AppInfo()
	{
	}

	AppInfo(AppInfo o)
	{
		executables = new HashMap<>(o.executables);
		name = o.name != null ? o.name : noName();
		public_only = o.public_only;
		type = o.type != null ? o.type : noType();
	}

	private static AppName noName()
	{
		return new AppName("(noname)");
	}

	private static AppType noType()
	{
		return AppType.valueOf("(notype)");
	}

	private String toString_executables()
	{
		return executables.isEmpty() ? "(noexecutable)"
			: executables.toString();
	}

	Map<String, String> executables = new HashMap<>();

	AppName name;

	AppType type;

	String public_only;

	public static class Builder
	{

		public AppInfo build()
		{
			return new AppInfo(prototype);
		}

		public Builder executable(String os, Optional<String> executable)
		{
			executable.ifPresent(e -> prototype.executables.put(os, e));

			return this;
		}

		public boolean executable_missing(String os)
		{
			return prototype.executables.get(os) == null;
		}

		public Builder name(AppName appName)
		{
			if (prototype.name != null) throw new TwoNames();
			prototype.name = appName;

			return this;
		}

		public Builder type(AppType appType)
		{
			if (prototype.type != null) throw new TwoNames();
			prototype.type = appType;

			return this;
		}

		public Builder public_only(String v)
		{
			prototype.public_only = v;

			return this;
		}

		private final AppInfo prototype = new AppInfo();

	}

}
