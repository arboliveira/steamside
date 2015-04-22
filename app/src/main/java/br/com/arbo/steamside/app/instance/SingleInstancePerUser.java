package br.com.arbo.steamside.app.instance;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.context.Lifecycle;

import br.com.arbo.steamside.app.browser.WebBrowser;
import br.com.arbo.steamside.app.instance.DetectSteamside.Situation;
import br.com.arbo.steamside.app.jetty.LocalWebserver;
import br.com.arbo.steamside.app.port.PortAlreadyInUse;

public class SingleInstancePerUser implements Lifecycle {

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

	@Override
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
		this.running = true;
	}

	@Override
	public void stop()
	{
		this.webserver.stop();
		this.running = false;
	}

	@Override
	public boolean isRunning()
	{
		return running;
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

		private void sweepPortCandidates() throws SteamsideUpAndRunning
		{
			int from = RANGE_BEGIN;
			int to = from + rangesize.size - 1;

			for (int p = from; p <= to; p++)
				consider(p);
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

		private void freefound(final int p)
		{
			if (!this.firstfreefound.isPresent())
				this.firstfreefound = Optional.of(p);
		}

		private Optional<Integer> firstfreefound = Optional.empty();
	}

	private boolean running;

	final DetectSteamside detect;
	final LimitPossiblePorts rangesize;
	final WebBrowser browser;
	final LocalWebserver webserver;

	private static final int RANGE_BEGIN = 42424;

}