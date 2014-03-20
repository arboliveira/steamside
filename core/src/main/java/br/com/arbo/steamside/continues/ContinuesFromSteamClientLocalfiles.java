package br.com.arbo.steamside.continues;

import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.LastPlayedDescending;
import br.com.arbo.steamside.library.Library;

public class ContinuesFromSteamClientLocalfiles implements ContinuesRooster {

	@Inject
	public ContinuesFromSteamClientLocalfiles(
			final @NonNull FilterContinues continues,
			final Library library) {
		this.library = library;
		this.continues = continues;
	}

	@Override
	public Stream<App> continues()
	{
		// TODO Prioritize games launched by current user
		return library.allApps()
				.filter(continues)
				.sorted(new LastPlayedDescending());
	}

	@NonNull
	private final FilterContinues continues;

	private final Library library;

}
