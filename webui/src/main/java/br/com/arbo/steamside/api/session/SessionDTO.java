package br.com.arbo.steamside.api.session;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.kids.KidsMode.NotInKidsMode;

@JsonAutoDetect
public class SessionDTO {

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

	public SessionDTO()
	{
		super();
	}

	public SessionDTO(
			final User username, final KidsMode kidsmode,
			final String gamesOwned)
	{
		this.username = username.username();
		this.kidsmode = isKidsModeOn(kidsmode);
		this.versionOfSteamside = new MavenBuild().readVersion();
		this.gamesOwned = gamesOwned;
	}

	@JsonProperty
	public String username;

	@JsonProperty
	public boolean kidsmode;

	public String versionOfSteamside;

	public String gamesOwned;
}
