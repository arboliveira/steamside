package br.com.arbo.steamside.api.session;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;

@Controller
@RequestMapping(br.com.arbo.steamside.mapping.Session.session)
public class SessionController {

	@RequestMapping(br.com.arbo.steamside.mapping.Session.session_json)
	@ResponseBody
	public SessionDTO session()
	{
		final int gamesOwned = library.count(
				new AppCriteria() {

					{
						gamesOnly = true;
					}
				});
		return new SessionDTO(
				username, kidsmode,
				String.valueOf(gamesOwned));
	}

	@Inject
	private User username;
	@Inject
	private KidsMode kidsmode;
	@Inject
	private Library library;
}
