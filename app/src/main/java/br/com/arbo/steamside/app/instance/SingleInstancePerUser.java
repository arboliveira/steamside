package br.com.arbo.steamside.app.instance;

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
			final WebBrowser browser) {
		this.webserver = webserver;
		this.browser = browser;
		this.detect = detect;
		this.rangesize = rangesize;
	}

	@Override
	public void start() {
		try {
			attemptRepeatedly();
		} catch (final SteamsideUpAndRunning e) {
			// all right!
		}
		this.running = true;
	}

	@Override
	public void stop() {
		this.webserver.stop();
		this.running = false;
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	private void attemptRepeatedly() throws SteamsideUpAndRunning {
		while (true)
			attempt();
	}

	private void attempt() throws SteamsideUpAndRunning {
		this.firstfreefound = null;
		sweepPortCandidates();
		if (this.firstfreefound == null) throw new AllPortsTaken();
		notRunningSteamsideTryHere(this.firstfreefound);
	}

	private void sweepPortCandidates() throws SteamsideUpAndRunning {
		for (int p = RANGE_BEGIN; p <= RANGE_BEGIN + rangesize.size - 1; p++)
			consider(p);
	}

	private void consider(final int p) throws SteamsideUpAndRunning {
		final Situation situation = detect.detect(p);
		switch (situation) {
			case NotHere:
				freefound(p);
				return;
			case AlreadyRunningForThisUser:
				this.browser.landing(p);
				throw new SteamsideUpAndRunning();
			case RunningOnDifferentUser:
				return;
			default:
				throw new IllegalStateException();
		}
	}

	private void notRunningSteamsideTryHere(final Integer p)
			throws SteamsideUpAndRunning {
		final int port = p.intValue();

		try {
			this.webserver.launch(port);
		} catch (final PortAlreadyInUse e) {
			// do nothing... come back to attempt one more sweep
			return;
		}

		browser.landing(port);

		throw new SteamsideUpAndRunning();
	}

	private void freefound(final int p) {
		if (this.firstfreefound == null)
			this.firstfreefound = Integer.valueOf(p);
	}

	private boolean running;
	private Integer firstfreefound;

	private final DetectSteamside detect;
	private final LimitPossiblePorts rangesize;
	private final WebBrowser browser;
	private final LocalWebserver webserver;

	private static final int RANGE_BEGIN = 42424;

}