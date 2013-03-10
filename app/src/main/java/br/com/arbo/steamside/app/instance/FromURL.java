package br.com.arbo.steamside.app.instance;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import br.com.arbo.steamside.opersys.username.User;

public class FromURL implements DetectSteamside {

	private final User username;

	public FromURL(final User username) {
		this.username = username;
	}

	@Override
	public Situation detect(final int p)
	{
		try {
			final String username_txt = fetch_username_txt(proof(p));
			final boolean itsme =
					username.username().equals(
							ExtractUsername.from(username_txt));
			return itsme ?
					Situation.AlreadyRunningForThisUser
					: Situation.RunningOnDifferentUser;
		} catch (final SteamsideNotRunningInThisPort e) {
			return Situation.NotHere;
		}
	}

	private static String fetch_username_txt(final URL proof)
			throws SteamsideNotRunningInThisPort {
		try {
			return IOUtils.toString(proof.openStream(), "UTF-8");
		} catch (final IOException e) {
			throw new SteamsideNotRunningInThisPort(e);
		}
	}

	static class SteamsideNotRunningInThisPort extends Exception {

		SteamsideNotRunningInThisPort(final Throwable e) {
			super(e);
		}
	}

	private static URL proof(final int p) {
		try {
			return new URL("http://localhost:" + p + "/session.json");
		} catch (final MalformedURLException e1) {
			throw new RuntimeException(e1);
		}
	}

}
