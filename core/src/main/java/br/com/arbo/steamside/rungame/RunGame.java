package br.com.arbo.steamside.rungame;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;

import br.com.arbo.processes.seek.Criteria;
import br.com.arbo.processes.seek.ProcessSeeker;
import br.com.arbo.processes.seek.ProcessSeekerFactory;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.NotFound;
import br.com.arbo.steamside.steam.client.protocol.C_rungameid;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;
import br.com.arbo.steamside.types.AppId;

public class RunGame {

	@Inject
	public RunGame(
			final SteamBrowserProtocol steam,
			final InMemory_appinfo_vdf appinfo_vdf) {
		this.steam = steam;
		this.appinfo_vdf = appinfo_vdf;
	}

	public boolean askSteamToRunGameAndWaitUntilItsUp(
			final AppId appid) {
		askSteamToRunGame(appid, steam);
		final String exe;
		try {
			exe = findExecutableName(appid);
		} catch (final NotAvailableOnThisPlatform e) {
			return false;
		}
		final Semaphore s = new Semaphore(0);
		final Thread t = seekInAnotherThread(exe, s);
		return waitUntilItsUp(s, t);
	}

	private static void askSteamToRunGame(final AppId appid,
			final SteamBrowserProtocol steam) {
		steam.launch(new C_rungameid(appid));
	}

	private String findExecutableName(final AppId appid)
			throws NotAvailableOnThisPlatform {
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
	private final InMemory_appinfo_vdf appinfo_vdf;

}
