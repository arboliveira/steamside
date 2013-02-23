package br.com.arbo.steamside.web;


public class AppNameFromSteamStore implements AppNameFactory {

	@Override
	public AppName nameOf(final String app) {
		return new AppPage(app).name();
	}
}
