package br.com.arbo.steamside.continues;

import org.mockito.Mockito;

import br.com.arbo.steamside.kids.KidsModeDetector;
import br.com.arbo.steamside.settings.SettingsImpl;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.library.Libraries;

public class ExampleDumpContinues
{

	public static void main(final String[] args)
	{
		new ExampleDumpContinues().dumpContinues();
	}

	private void dumpContinues()
	{
		KidsModeDetector nop = Mockito.mock(KidsModeDetector.class);

		new ContinuesFromSteamClientLocalfiles(
			nop, Libraries.fromSteamPhysicalFiles(), new SettingsImpl())
				.continues().forEach(
					this::printApp);
	}

	private void printApp(App app)
	{
		System.out.println(
			app.lastPlayed()
				+ " :: " + app.name()
				+ " :: " + app.appid());
	}
}
