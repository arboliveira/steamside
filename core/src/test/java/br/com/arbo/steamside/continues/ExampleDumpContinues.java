package br.com.arbo.steamside.continues;

import org.mockito.Mockito;

import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.settings.SettingsImpl;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.library.Libraries;
import br.com.arbo.steamside.steam.client.library.Library;

public class ExampleDumpContinues {

	public static void main(final String[] args)
	{
		new ExampleDumpContinues().dumpContinues();
	}

	private void dumpContinues()
	{
		KidsMode nop = Mockito.mock(KidsMode.class);

		final FilterContinues continues = new FilterContinues(nop);

		Library library = Libraries.fromSteamPhysicalFiles();

		new ContinuesFromSteamClientLocalfiles(
				continues, library, new SettingsImpl())
				.continues().forEach(
						this::printApp
				);
	}

	private void printApp(App app)
	{
		System.out.println(
				app.lastPlayed()
						+ " :: " + app.name()
						+ " :: " + app.appid()
				);
	}
}
