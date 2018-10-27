package br.com.arbo.steamside.continues;

import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.LastPlayedDescending;
import br.com.arbo.steamside.kids.KidsModeDetector;
import br.com.arbo.steamside.settings.Settings;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.home.AppCriteria;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;

public class ContinuesFromSteamClientLocalfiles implements ContinuesRooster
{

	@Override
	public Stream<App> continues()
	{
		// TODO Prioritize games launched by current user

		@SuppressWarnings("resource")
		final Stream<App> cont = steamClientHome.apps()
			.stream(
				new AppCriteria().gamesOnly(true)
					.currentPlatformOnly(currentPlatformOnly))
			.filter(continues);
		return cont.sorted(new LastPlayedDescending());
	}

	@Inject
	public ContinuesFromSteamClientLocalfiles(
		KidsModeDetector kidsModeDetector,
		SteamClientHome steamClientHome,
		Settings settings)
	{
		this.steamClientHome = steamClientHome;
		this.continues = new FilterContinues(kidsModeDetector);
		this.currentPlatformOnly = settings.currentPlatformOnly();
	}

	@NonNull
	private final FilterContinues continues;

	private final boolean currentPlatformOnly;

	private final SteamClientHome steamClientHome;

}
