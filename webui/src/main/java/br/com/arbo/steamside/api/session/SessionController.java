package br.com.arbo.steamside.api.session;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.spring.SteamsideApplicationContext;
import br.com.arbo.steamside.webui.wicket.SharedConfigConsume;

@Controller
@RequestMapping("session")
public class SessionController implements ApplicationContextAware {

	@RequestMapping("session.json")
	@ResponseBody
	public SessionDTO session() {
		final int gamesOwned = config.data().apps().count();
		return new SessionDTO(
				username, kidsmode,
				String.valueOf(gamesOwned));
	}

	@Override
	public void setApplicationContext(
			final ApplicationContext applicationContext)
			throws BeansException {
		final MutablePicoContainerX container =
				((SteamsideApplicationContext) applicationContext)
						.getContainer();
		this.username = container.getComponent(User.class);
		this.kidsmode = container.getComponent(KidsMode.class);
		this.config =
				container
						.getComponent(SharedConfigConsume.class);
	}

	private User username;
	private KidsMode kidsmode;
	private SharedConfigConsume config;
}
