package br.com.arbo.steamside.api.session;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

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
