package br.com.arbo.steamside.app.instance;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.arbo.steamside.app.browser.WebBrowser;
import br.com.arbo.steamside.app.instance.DetectSteamside.Situation;
import br.com.arbo.steamside.app.launch.LocalWebserver;
import br.com.arbo.steamside.app.port.PortAlreadyInUse;

public class SingleInstancePerUser {

	@Inject
	public SingleInstancePerUser(
		final DetectSteamside detect,
		final LimitPossiblePorts rangesize,
		final LocalWebserver webserver,
		final WebBrowser browser)
	{
		this.webserver = webserver;
		this.browser = browser;
		this.detect = detect;
		this.rangesize = rangesize;
	}

	@PostConstruct
	public void start() throws AllPortsTaken
	{
		try
		{
			attemptRepeatedly();
		}
		catch (SteamsideUpAndRunning e)
		{
			// all right!
		}
	}

	@PreDestroy
	public void stop()
	{
		this.webserver.stop();
	}

	private void attemptRepeatedly() throws SteamsideUpAndRunning, AllPortsTaken
	{
		while (true)
			new Attempt().attempt();
	}

	class Attempt {

		void attempt() throws SteamsideUpAndRunning, AllPortsTaken
		{
			sweepPortCandidates();
			notRunningSteamsideTryOnFirstFreeFound();
		}

		private void consider(final int p) throws SteamsideUpAndRunning
		{
			getLogger().info("Looking for a free port on " + p + "...");
			Situation situation = detect.detect(p);
			switch (situation) {
			case NotHere:
				getLogger().info("Found a free port on " + p + ".");
				freefound(p);
				return;
			case AlreadyRunningForThisUser:
				getLogger()
					.info("Found Steamside already running on " + p + ".");
				browser.landing(p);
				throw new SteamsideUpAndRunning();
			case RunningOnDifferentUser:
				getLogger()
					.info("Another user running Steamside on " + p + ".");
				return;
			default:
				throw new IllegalStateException();
			}
		}

		private void freefound(final int p)
		{
			if (!this.firstfreefound.isPresent())
				this.firstfreefound = Optional.of(p);
		}

		private Logger getLogger()
		{
			return Logger.getLogger(this.getClass());
		}

		private void notRunningSteamsideTryOnFirstFreeFound()
			throws SteamsideUpAndRunning, AllPortsTaken
		{
			final int port = firstfreefound.orElseThrow(AllPortsTaken::new);

			try
			{
				webserver.launch(port);
			}
			catch (final PortAlreadyInUse e)
			{
				// do nothing... come back to attempt one more sweep
				return;
			}

			browser.landing(port);

			throw new SteamsideUpAndRunning();
		}

		private void sweepPortCandidates() throws SteamsideUpAndRunning
		{
			int from = RANGE_BEGIN;
			int to = from + rangesize.size - 1;

			for (int p = from; p <= to; p++)
				consider(p);
		}

		private Optional<Integer> firstfreefound = Optional.empty();
	}

	final DetectSteamside detect;
	final LimitPossiblePorts rangesize;
	final WebBrowser browser;
	final LocalWebserver webserver;

	private static final int RANGE_BEGIN = 42424;

}