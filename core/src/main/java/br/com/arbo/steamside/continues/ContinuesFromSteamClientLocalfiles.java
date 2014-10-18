package br.com.arbo.steamside.continues;

import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.LastPlayedDescending;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.settings.Settings;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.apps.FilterPlatform;
import br.com.arbo.steamside.steam.client.library.Library;

public class ContinuesFromSteamClientLocalfiles implements ContinuesRooster {

	@Inject
	public ContinuesFromSteamClientLocalfiles(
			@NonNull KidsMode kidsmode,
			Library library,
			Settings settings)
	{
		this.library = library;
		this.continues = new FilterContinues(kidsmode);
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
