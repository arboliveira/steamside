package br.com.arbo.steamside.webui.wicket.session.json;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.apache.wicket.request.resource.ResourceReference;

import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.webui.wicket.json.Factory;
import br.com.arbo.steamside.webui.wicket.json.JsonResource;

public class SessionJson extends ResourceReference {

	private final Factory<SessionDTO> fetch;

	public SessionJson(final User username, final KidsMode kidsmode) {
		super(SessionJson.class, "session-json");

		class SessionJsonResource implements Factory<SessionDTO> {

			@Override
			public SessionDTO produce(final Attributes a) {
				return new SessionDTO(username, kidsmode);
			}
		}

		this.fetch = new SessionJsonResource();
	}

	@Override
	public IResource getResource() {
		return new JsonResource(fetch);
	}
}