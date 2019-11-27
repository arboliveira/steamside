package br.com.arbo.steamside.steam.client.apps;

public interface Platform
{

	public static final String linux = "linux";
	public static final String macos = "macos";
	public static final String macosx = "macosx";
	public static final String windows = "windows";

	public String os();
}
