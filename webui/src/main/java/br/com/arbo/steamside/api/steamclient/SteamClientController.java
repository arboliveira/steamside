package br.com.arbo.steamside.api.steamclient;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.mapping.SteamClient;
import br.com.arbo.steamside.steam.client.protocol.LiteralCommand;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;

@RestController
@RequestMapping(SteamClient.steamclient)
public class SteamClientController {

	@RequestMapping("{command}")
	public void launch(
		@PathVariable String command)
	{
		launchCommand(command);
	}

	@RequestMapping("{command}/{argument}")
	public void launch(
		@PathVariable String command,
		@PathVariable String argument)
	{
		launchCommand(command + "/" + argument);
	}

	@RequestMapping(SteamClient.status_json)
	public StatusDTO status()
	{
		return new StatusDTOBuilder(user).build();
	}

	private void launchCommand(final String command)
	{
		steam.launch(new LiteralCommand(command));
	}

	@Inject
	private SteamBrowserProtocol steam;

	@Inject
	private User user;

}
