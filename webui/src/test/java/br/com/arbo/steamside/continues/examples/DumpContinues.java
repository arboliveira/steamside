package br.com.arbo.steamside.continues.examples;

import br.com.arbo.org.codehaus.jackson.map.JsonUtils;
import br.com.arbo.steamside.continues.Continue;
import br.com.arbo.steamside.steam.client.localfiles.appcache.AppNameFromLocalFiles;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.webui.wicket.ContinueNeedsImpl;
import br.com.arbo.steamside.webui.wicket.SharedConfigConsume;

public class DumpContinues {

	public static void main(final String[] args) {
		final AppNameFromLocalFiles appnames = new AppNameFromLocalFiles(
				new InMemory_appinfo_vdf());
		final SharedConfigConsume sharedconfig = new SharedConfigConsume();
		final Continue.Needs continueNeeds = new ContinueNeedsImpl(
				appnames, sharedconfig);
		System.out.println(
				JsonUtils.asString(new Continue(continueNeeds).fetch()));
	}

}
