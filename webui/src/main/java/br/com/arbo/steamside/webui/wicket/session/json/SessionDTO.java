package br.com.arbo.steamside.webui.wicket.session.json;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.opersys.username.User;

@JsonAutoDetect
public class SessionDTO {

	public SessionDTO() {
		super();
	}

	public SessionDTO(final User username, final KidsMode kidsmode) {
		this.username = username.username();
		this.kidsmode = kidsmode.isKidsModeOn();
	}

	@JsonProperty
	public String username;

	@JsonProperty
	public boolean kidsmode;
}
