package br.com.arbo.steamside.api.session;

import javax.inject.Inject;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.kids.KidsMode.NotInKidsMode;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.library.Library;

public class SessionController_session_json implements
	SessionController_session
{

	private static boolean isKidsModeOn(KidsMode kidsmode)
	{
		try
		{
			kidsmode.kid();
		}
		catch (NotInKidsMode e)
		{
			return false;
		}
		return true;
	}

	@Inject
	public SessionController_session_json(
		User username,
		KidsMode kidsmode,
		Library library)
	{
		this.username = username;
		this.kidsmode = kidsmode;
		this.library = library;
	}

	@Override
	public SessionDTO jsonable()
	{
		SessionDTO dto = new SessionDTO();
		dto.userName = username.username();
		dto.kidsMode = isKidsModeOn(kidsmode);
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
					gamesOnly = true;
				}
			});
	}

	private final Library library;
	private final User username;
	private final KidsMode kidsmode;

}
