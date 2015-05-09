package br.com.arbo.steamside.app.instance;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import br.com.arbo.steamside.app.browser.WebBrowser;
import br.com.arbo.steamside.app.instance.DetectSteamside.Situation;
import br.com.arbo.steamside.app.jetty.LocalWebserver;
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
			final Situation situation = detect.detect(p);
			switch (situation) {
			case NotHere:
				freefound(p);
				return;
			case AlreadyRunningForThisUser:
				browser.landing(p);
				throw new SteamsideUpAndRunning();
			case RunningOnDifferentUser:
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

	private static final int RANGE_BEGIN = 42424;
	final DetectSteamside detect;
	final LimitPossiblePorts rangesize;
	final WebBrowser browser;

	final LocalWebserver webserver;

}