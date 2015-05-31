package br.com.arbo.steamside.api.session;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.kids.KidsMode.NotInKidsMode;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.library.Library;

@RestController
@RequestMapping(br.com.arbo.steamside.mapping.Session.session)
public class SessionController
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

	@RequestMapping(br.com.arbo.steamside.mapping.Session.session_json)
	public SessionDTO session()
	{
		SessionDTO dto = new SessionDTO();
		dto.userName = username.username();
		dto.kidsMode = isKidsModeOn(kidsmode);
		dto.versionOfSteamside = new MavenBuild().readVersion();
		dto.gamesOwned = String.valueOf(gamesOwned());
		dto.executable = executable();
		return dto;
	}

	private String executable()
	{
		return StringUtils.substringBetween(
			this.getClass().getResource(
				this.getClass().getSimpleName() + ".class").getPath(),
			"file:", "!");
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

	@Inject
	private User username;
	@Inject
	private KidsMode kidsmode;
	@Inject
	private Library library;
}
