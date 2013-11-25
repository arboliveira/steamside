package br.com.arbo.steamside.api.steamclient;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.arbo.steamside.steam.client.protocol.LiteralCommand;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;

@Controller
@RequestMapping("steamclient")
public class SteamClientController {

	@RequestMapping("{command}/{argument}")
	public void launch(
			@PathVariable final String command,
			@PathVariable final String argument) {
		launchCommand(command + "/" + argument);
	}

	@RequestMapping("{command}")
	public void launch(@PathVariable final String command) {
		launchCommand(command);
	}

	private void launchCommand(final String command) {
		steam.launch(new LiteralCommand(command));
	}

	@Inject
	private SteamBrowserProtocol steam;

}
