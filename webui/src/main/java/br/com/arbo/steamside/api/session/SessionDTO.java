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
		User username, KidsMode kidsmode, String gamesOwned)
	{
		this.userName = username.username();
		this.kidsMode = isKidsModeOn(kidsmode);
		this.versionOfSteamside = new MavenBuild().readVersion();
		this.gamesOwned = gamesOwned;
	}

	@JsonProperty
	public String userName;

	@JsonProperty
	public boolean kidsMode;

	public String versionOfSteamside;

	public String gamesOwned;
}
