package br.com.arbo.steamside.webui.wicket.session.json;

import org.apache.wicket.request.resource.IResource.Attributes;

import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.webui.wicket.json.Factory;

class SessionJsonResource implements Factory<SessionDTO> {

	private final User username;
	private final KidsMode kidsmode;

	SessionJsonResource(final User username, final KidsMode kidsmode) {
		this.username = username;
		this.kidsmode = kidsmode;
	}

	@Override
	public SessionDTO produce(final Attributes a) {
		return new SessionDTO(username, kidsmode);
	}
}