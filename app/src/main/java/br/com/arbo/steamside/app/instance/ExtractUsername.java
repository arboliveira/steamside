package br.com.arbo.steamside.app.instance;

import br.com.arbo.org.codehaus.jackson.map.JsonUtils;
import br.com.arbo.steamside.api.session.SessionDTO;

class ExtractUsername {

	static String from(final String json) {
		final SessionDTO dto = JsonUtils.fromString(json, SessionDTO.class);
		return dto.username;
	}

}