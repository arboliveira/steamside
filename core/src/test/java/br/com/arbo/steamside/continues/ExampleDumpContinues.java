package br.com.arbo.steamside.continues;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.kids.FromUsername;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.library.Library_ForExamples;
import br.com.arbo.steamside.opersys.username.FromJava;

public class ExampleDumpContinues {

	public static void main(final String[] args) {
		final FilterContinues continues =
				new FilterContinues(new FromUsername(new FromJava()));

		Library library = Library_ForExamples.fromSteamPhysicalFiles();

		new ContinuesFromSteamClientLocalfiles(continues, library)
				.accept(/* @formatter:off */new App.Visitor() { 
					@Override
					public void each /* @formatter:on */
							(final App app) {
						System.out.println(
								app.lastPlayed()
										+ " :: " + app.name()
										+ " :: " + app.appid());
					}
				});

	}
}
