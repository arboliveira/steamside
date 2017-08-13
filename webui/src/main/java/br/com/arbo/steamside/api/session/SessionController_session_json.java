package br.com.arbo.steamside.api.session;

import javax.inject.Inject;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.kids.KidsModeDetector;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.library.Library;

public class SessionController_session_json implements
	SessionController_session
{

	private static boolean isKidsModeOn(KidsModeDetector kidsModeDetector)
	{
		return kidsModeDetector.kidsMode().isPresent();
	}

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

	private int gamesOwned()
	{
		return library.count(
			new AppCriteria()
			{

				{
					gamesOnly = false;
					owned = true;
				}
			});
	}

	@Inject
	public SessionController_session_json(
		User username,
		KidsModeDetector kidsModeDetector,
		Library library)
	{
		this.username = username;
		this.kidsModeDetector = kidsModeDetector;
		this.library = library;
	}

	private final Library library;
	private final User username;
	private final KidsModeDetector kidsModeDetector;

}
