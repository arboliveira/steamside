package br.com.arbo.steamside.api.session;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.kids.KidsMode;

@JsonAutoDetect
public class SessionDTO {

	public SessionDTO() {
		super();
	}

	public SessionDTO(
			final User username, final KidsMode kidsmode,
			final String gamesOwned) {
		this.username = username.username();
		this.kidsmode = kidsmode.isKidsModeOn();
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
