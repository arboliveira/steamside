package br.com.arbo.steamside.webui.wicket.session.json;

import org.apache.wicket.request.resource.IResource.Attributes;

import br.com.arbo.steamside.opersys.username.Username;
import br.com.arbo.steamside.webui.wicket.json.JsonResource;

class SessionJsonResource implements JsonResource.Needs<SessionDTO> {

	private final Username username;

	SessionJsonResource(final Username username) {
		this.username = username;
	}

	@Override
	public SessionDTO fetchValue(final Attributes a) {
		return new SessionDTO(username.username());
	}
}