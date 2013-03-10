package br.com.arbo.steamside.steam.client.protocol;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.SystemUtils;

import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.steam.client.executable.KillSteamIfAlreadyRunningInADifferentUserSession;

public class SteamBrowserProtocol {

	private final KillSteamIfAlreadyRunningInADifferentUserSession clearance;

	public SteamBrowserProtocol(final User username) {
		this.clearance =
				new KillSteamIfAlreadyRunningInADifferentUserSession(username);
	}

	public void launch(final Command command) {
		clearance.confirm();
		executeURL(command.command());
	}

	private static void executeURL(final String command) {
		final String str = "steam://" + command;
		try {
			Desktop.getDesktop().browse(new URI(str));
		} catch (final IOException e) {
			if (isCandidateForLinuxAlternative(str, e))
				attemptLinuxAlternative(str);
			else
				throw new RuntimeException(e);
		} catch (final URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean isCandidateForLinuxAlternative(
			final String str, final IOException e) {
		if (!e.getMessage().equals("Failed to show URI:" + str)) return false;
		if (!SystemUtils.IS_OS_LINUX) return false;
		return true;
	}

	private static void attemptLinuxAlternative(final String str) {
		try {
			new ProcessBuilder("xdg-open", str).start();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
}
