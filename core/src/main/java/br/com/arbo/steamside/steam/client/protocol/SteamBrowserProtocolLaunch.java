package br.com.arbo.steamside.steam.client.protocol;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import br.com.arbo.steamside.steam.client.kill.KillSteam;

public class SteamBrowserProtocolLaunch {

	public static void launch(final Command command) {
		killSteamIfAlreadyRunningInADifferentUserSession();
		launchSteam(command);
	}

	private static void launchSteam(final Command command) {
		final String str = "steam://" + command.command();
		try {
			Desktop.getDesktop().browse(new URI(str));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		} catch (final URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private static void killSteamIfAlreadyRunningInADifferentUserSession() {
		KillSteam.kill();
	}
}
