package br.com.arbo.steamside.api.session;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.DataFactory_sharedconfig_vdf;

@Controller
@RequestMapping("session")
public class SessionController {

	@RequestMapping("session.json")
	@ResponseBody
	public SessionDTO session() {
		final int gamesOwned = config.data().apps().count();
		return new SessionDTO(
				username, kidsmode,
				String.valueOf(gamesOwned));
	}

	@Inject
	private User username;
	@Inject
	private KidsMode kidsmode;
	@Inject
	private DataFactory_sharedconfig_vdf config;
}
