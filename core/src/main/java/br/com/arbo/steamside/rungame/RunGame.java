package br.com.arbo.steamside.rungame;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import br.com.arbo.processes.seek.ProcessSeeker;
import br.com.arbo.processes.seek.ProcessSeekerFactory;
import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf.NotFound;
import br.com.arbo.steamside.steam.client.protocol.C_rungameid;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;
import br.com.arbo.steamside.types.AppId;

public class RunGame {

	private final SteamBrowserProtocol steam;
	private final InMemory_appinfo_vdf appinfo_vdf;
	private final User user;

	public RunGame(
			final SteamBrowserProtocol steam,
			final InMemory_appinfo_vdf appinfo_vdf,
			final User user) {
		this.steam = steam;
		this.appinfo_vdf = appinfo_vdf;
		this.user = user;
	}

	public boolean askSteamToRunGameAndWaitUntilItsUp(
			final AppId appid) {
		askSteamToRunGame(appid, steam);
		final String exe = findExecutableName(appid);
		final Semaphore s = new Semaphore(0);
		final Thread t = seekInAnotherThread(exe, s);
		return waitUntilItsUp(s, t);
	}

	private static void askSteamToRunGame(final AppId appid,
			final SteamBrowserProtocol steam) {
		steam.launch(new C_rungameid(appid));
	}

	private String findExecutableName(final AppId appid) {
		try {
			return appinfo_vdf.get(appid.appid).executable();
		} catch (final NotFound e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean waitUntilItsUp(final Semaphore s, final Thread t) {
		try {
			final boolean found = s.tryAcquire(2, TimeUnit.MINUTES);
			return found;
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			t.interrupt();
		}
	}

	private Thread seekInAnotherThread(
			final String exe, final Semaphore seeking) {

		class AgainAndAgain implements Runnable {

			@Override
			public void run() {
				final boolean found =
						seekExecutableAgainAndAgain(exe);
				if (found) seeking.release();
			}

		}
		final Thread t = new Thread(
				new AgainAndAgain(), "Seek executable: " + exe);
		t.setDaemon(true);
		t.start();
		return t;
	}

	boolean seekExecutableAgainAndAgain(final String exe) {
		final ProcessSeeker seeker = ProcessSeekerFactory.build();
		while (true) {
			final boolean found = seeker.seek(exe, user.username());
			if (found) return true;
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				return false;
			}
		}
	}

}
