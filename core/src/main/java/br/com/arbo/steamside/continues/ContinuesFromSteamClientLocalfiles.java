package br.com.arbo.steamside.continues;

import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.AppCriteria;
import br.com.arbo.steamside.apps.FilterPlatform;
import br.com.arbo.steamside.apps.LastPlayedDescending;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.settings.Settings;

public class ContinuesFromSteamClientLocalfiles implements ContinuesRooster {

	@Inject
	public ContinuesFromSteamClientLocalfiles(
			final @NonNull FilterContinues continues,
			final Library library,
			Settings settings)
	{
		this.library = library;
		this.continues = continues;
		this.currentPlatformOnly = settings.currentPlatformOnly();
	}

	@Override
	public Stream<App> continues()
	{
		// TODO Prioritize games launched by current user

		@SuppressWarnings("resource")
		final Stream<App> cont = library.allApps(new AppCriteria() {

			{
				gamesOnly = true;
			}
		}).filter(continues);
		final Stream<App> end = currentPlatformOnly ?
				cont.filter(new FilterPlatform()) : cont;
		return end.sorted(new LastPlayedDescending());
	}

	@NonNull
	private final FilterContinues continues;

	private final boolean currentPlatformOnly;

	private final Library library;

}
