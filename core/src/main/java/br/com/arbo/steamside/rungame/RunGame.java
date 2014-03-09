package br.com.arbo.steamside.rungame;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;

import br.com.arbo.processes.seek.Criteria;
import br.com.arbo.processes.seek.ProcessSeeker;
import br.com.arbo.processes.seek.ProcessSeekerFactory;
import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.protocol.C_rungameid;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;
import br.com.arbo.steamside.types.AppId;

public class RunGame {

	@Inject
	public RunGame(
			final SteamBrowserProtocol steam,
			final Library library) {
		this.steam = steam;
		this.library = library;
	}

	public void askSteamToRunGameAndWaitUntilItsUp(final AppId appid)
			throws NotAvailableOnThisPlatform, NotFound, Timeout {
		askSteamToRunGame(appid, steam);
		final String exe = findExecutableName(appid);
		final Semaphore s = new Semaphore(0);
		final Thread t = seekInAnotherThread(exe, s);
		waitUntilItsUp(s, t);
	}

	private static void askSteamToRunGame(final AppId appid,
			final SteamBrowserProtocol steam) {
		steam.launch(new C_rungameid(appid));
	}

	private String findExecutableName(final AppId appid)
			throws NotAvailableOnThisPlatform, NotFound
	{
		return library.app(appid).executable();
	}

	private static void waitUntilItsUp(final Semaphore s, final Thread t)
			throws Timeout {
		try {
			if (!s.tryAcquire(2, TimeUnit.MINUTES)) throw new Timeout();
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			t.interrupt();
		}
	}

	private static Thread seekInAnotherThread(
			final String exe, final Semaphore seeking) {
		Validate.notNull(exe);

		final Criteria criteria = new Criteria();
		criteria.executable = exe;

		class AgainAndAgain implements Runnable {

			@Override
			public void run() {
				final boolean found =
						seekExecutableAgainAndAgain(criteria);
				if (found) seeking.release();
			}

		}
		final Thread t = new Thread(
				new AgainAndAgain(), "Seek executable: " + exe);
		t.setDaemon(true);
		t.start();
		return t;
	}

	static boolean seekExecutableAgainAndAgain(final Criteria criteria) {
		final ProcessSeeker seeker = ProcessSeekerFactory.build();
		while (true) {
			final boolean found = seeker.seek(criteria);
			if (found) return true;
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				return false;
			}
		}
	}

	private final SteamBrowserProtocol steam;
	private final Library library;

}
