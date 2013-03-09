package br.com.arbo.steamside.webui.wicket.session.json;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import br.com.arbo.steamside.opersys.username.Username;
import br.com.arbo.steamside.webui.wicket.json.JsonResource;

public class SessionJson extends ResourceReference {

	private final Username username;

	public SessionJson(final Username username) {
		super(SessionJson.class, "session-json");
		this.username = username;
	}

	@Override
	public IResource getResource() {
		return new JsonResource(new SessionJsonResource(username));
	}
}