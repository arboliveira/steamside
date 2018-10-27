package br.com.arbo.steamside.api.session;

import javax.inject.Inject;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.kids.KidsModeDetector;
import br.com.arbo.steamside.steam.client.apps.home.AppCriteria;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;

public class SessionController_session_json implements
	SessionController_session
{

	@Override
	public SessionDTO jsonable()
	{
		SessionDTO dto = new SessionDTO();
		dto.userName = username.username();
		dto.kidsMode = isKidsModeOn(kidsModeDetector);
		dto.versionOfSteamside = new MavenBuild().readVersion();
		dto.gamesOwned = String.valueOf(gamesOwned());
		dto.executable = new ExecutableDetector().executable();
		return dto;
	}

	@Inject
	public SessionController_session_json(
		User username,
		KidsModeDetector kidsModeDetector,
		SteamClientHome steamClientHome)
	{
		this.username = username;
		this.kidsModeDetector = kidsModeDetector;
		this.steamClientHome = steamClientHome;
	}

	private int gamesOwned()
	{
		return steamClientHome.apps().count(
			new AppCriteria().gamesOnly(false).owned(true));
	}

	private static boolean isKidsModeOn(KidsModeDetector kidsModeDetector)
	{
		return kidsModeDetector.kidsMode().isPresent();
	}

	private final KidsModeDetector kidsModeDetector;
	private final SteamClientHome steamClientHome;
	private final User username;

}
