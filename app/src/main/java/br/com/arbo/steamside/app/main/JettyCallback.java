package br.com.arbo.steamside.app.main;

import br.com.arbo.steamside.app.browser.Browser;
import br.com.arbo.steamside.app.jetty.Callback;

class JettyCallback implements Callback {

	@Override
	public void started(final int port) {
		Browser.landing(port);
	}

}