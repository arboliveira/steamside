package br.com.arbo.steamside.webui.wicket.stop;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import br.com.arbo.org.apache.wicket.markup.html.pages.EmptyPage;
import br.com.arbo.steamside.webui.wicket.WicketApplication;

public class ExitPage extends EmptyPage {

	public ExitPage() {
		super();

		this.renderingFinished = new Semaphore(0);

		final WicketApplication app = WicketApplication.get();
		new Thread(new Runnable() {

			@Override
			public void run() {
				exit(app);
			}

		}, "Exit SteamSide").start();
	}

	@Override
	public void renderPage() {
		super.renderPage();
		renderingFinished.release();
	}

	void exit(final WicketApplication app) {
		giveTheUserAChanceToSeeThePageBeforeWeWreakHavocOnJetty();
		app.exit();
	}

	private void giveTheUserAChanceToSeeThePageBeforeWeWreakHavocOnJetty() {
		try {
			renderingFinished.tryAcquire(5, TimeUnit.SECONDS);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private final Semaphore renderingFinished;

}
