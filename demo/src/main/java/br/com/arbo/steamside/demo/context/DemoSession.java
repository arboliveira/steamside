package br.com.arbo.steamside.demo.context;

import br.com.arbo.steamside.api.session.ExecutableDetector;
import br.com.arbo.steamside.api.session.SessionController_session;
import br.com.arbo.steamside.api.session.SessionDTO;

public class DemoSession implements SessionController_session
{

	@Override
	public SessionDTO jsonable()
	{
		// @formatter:off
		return new SessionDTO() {{
			executable = new ExecutableDetector().executable();
			gamesOwned = "849";
			kidsMode = false;
			userName = "gaben";
			versionOfSteamside = "v1.0-pre-alpha";
		}};
		// @formatter:on
	}
}
