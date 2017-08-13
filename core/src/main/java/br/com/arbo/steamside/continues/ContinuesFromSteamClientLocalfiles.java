package br.com.arbo.steamside.continues;

import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.LastPlayedDescending;
import br.com.arbo.steamside.kids.KidsModeDetector;
import br.com.arbo.steamside.settings.Settings;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.apps.FilterPlatform;
import br.com.arbo.steamside.steam.client.library.Library;

public class ContinuesFromSteamClientLocalfiles implements ContinuesRooster
{

	@Override
	public Stream<App> continues()
	{
		// TODO Prioritize games launched by current user

		@SuppressWarnings("resource")
		final Stream<App> cont = library.allApps(new AppCriteria()
		{

			{
				gamesOnly = true;
			}
		}).filter(continues);
		final Stream<App> end = currentPlatformOnly
			? cont.filter(new FilterPlatform())
			: cont;
		return end.sorted(new LastPlayedDescending());
	}

	@Inject
	public ContinuesFromSteamClientLocalfiles(
		KidsModeDetector kidsModeDetector,
		Library library,
		Settings settings)
	{
		this.library = library;
		this.continues = new FilterContinues(kidsModeDetector);
		this.currentPlatformOnly = settings.currentPlatformOnly();
	}

	@NonNull
	private final FilterContinues continues;

	private final boolean currentPlatformOnly;

	private final Library library;

}
