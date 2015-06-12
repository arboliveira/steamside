package br.com.arbo.steamside.api.session;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(br.com.arbo.steamside.mapping.Session.session)
public class SessionController
{

	@RequestMapping(br.com.arbo.steamside.mapping.Session.session_json)
	public SessionDTO session()
	{
		return session.jsonable();
	}

	@Inject
	public SessionController_session session;

}
