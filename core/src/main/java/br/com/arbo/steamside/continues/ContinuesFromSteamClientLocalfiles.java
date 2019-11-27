package br.com.arbo.steamside.continues;

import java.util.Optional;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.kids.KidsModeDetector;
import br.com.arbo.steamside.settings.Settings;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.home.AppCriteria;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.platform.PlatformFactory;

public class ContinuesFromSteamClientLocalfiles implements ContinuesRooster
{

	@Override
	public Stream<App> continues()
	{
		// TODO Prioritize games launched by current user

		return steamClientHome.apps()
			.find(
				new AppCriteria()
					.gamesOnly(true)
					.lastPlayedDescending(true)
					.platform(
						currentPlatformOnly
							? Optional.of(platformFactory.current())
							: Optional.empty()))
			.filter(continues);
	}

	@Inject
	public ContinuesFromSteamClientLocalfiles(
		KidsModeDetector kidsModeDetector,
		SteamClientHome steamClientHome,
		PlatformFactory platformFactory,
		Settings settings)
	{
		this.steamClientHome = steamClientHome;
		this.platformFactory = platformFactory;
		this.continues = new FilterContinues(kidsModeDetector);
		this.currentPlatformOnly = settings.currentPlatformOnly();
	}

	@NonNull
	private final FilterContinues continues;

	private final boolean currentPlatformOnly;

	private final PlatformFactory platformFactory;

	private final SteamClientHome steamClientHome;

}
