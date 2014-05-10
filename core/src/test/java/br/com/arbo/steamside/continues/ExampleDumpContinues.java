package br.com.arbo.steamside.continues;

import org.mockito.Mockito;

import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.library.Library_ForExamples;
import br.com.arbo.steamside.settings.SettingsImpl;

public class ExampleDumpContinues {

	public static void main(final String[] args)
	{
		KidsMode nop = Mockito.mock(KidsMode.class);

		final FilterContinues continues = new FilterContinues(nop);

		Library library = Library_ForExamples.fromSteamPhysicalFiles();

		new ContinuesFromSteamClientLocalfiles(continues, library,
				new SettingsImpl())
				.continues().forEach(
						app ->
						System.out.println(
								app.lastPlayed()
										+ " :: " + app.name()
										+ " :: " + app.appid()
								)
				);

	}
}
