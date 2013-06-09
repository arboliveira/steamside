package br.com.arbo.steamside.api.steamclient;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.spring.SteamsideApplicationContext;
import br.com.arbo.steamside.steam.client.protocol.LiteralCommand;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;

@Controller
@RequestMapping("steamclient")
public class SteamClientController implements ApplicationContextAware {

	@RequestMapping("{command}")
	public void launch(@PathVariable final String command) {
		launchCommand(command);
	}

	@RequestMapping("{command}/{argument}")
	public void launch(
			@PathVariable final String command,
			@PathVariable final String argument) {
		launchCommand(command + "/" + argument);
	}

	@Override
	public void setApplicationContext(
			final ApplicationContext applicationContext)
			throws BeansException {
		final MutablePicoContainerX container =
				((SteamsideApplicationContext) applicationContext)
						.getContainer();
		this.steam = container.getComponent(SteamBrowserProtocol.class);
	}

	private void launchCommand(final String command) {
		steam.launch(new LiteralCommand(command));
	}

	private SteamBrowserProtocol steam;

}
