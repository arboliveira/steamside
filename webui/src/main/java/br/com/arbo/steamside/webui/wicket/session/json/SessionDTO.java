package br.com.arbo.steamside.webui.wicket.session.json;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonAutoDetect
public class SessionDTO {

	public SessionDTO() {
		super();
	}

	public SessionDTO(final String username) {
		this.username = username;
	}

	@JsonProperty
	public String username;
}
