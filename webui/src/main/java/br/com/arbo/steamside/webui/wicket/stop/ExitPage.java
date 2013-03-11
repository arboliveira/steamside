package br.com.arbo.steamside.webui.wicket.stop;

import br.com.arbo.org.apache.wicket.markup.html.pages.EmptyPage;
import br.com.arbo.steamside.webui.wicket.WicketApplication;

public class ExitPage extends EmptyPage {

	public ExitPage() {
		super();
		final WicketApplication app = WicketApplication.get();
		new Thread(new Runnable() {

			@Override
			public void run() {
				app.exit();
			}

		}, "Exit SteamSide").start();
	}

}
