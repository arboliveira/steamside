package br.com.arbo.steamside.api.session;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonAutoDetect
public class SessionDTO
{
	@JsonProperty
	public String userName;

	@JsonProperty
	public boolean kidsMode;

	public String versionOfSteamside;

	public String gamesOwned;

	public String executable;
}
