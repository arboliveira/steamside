package br.com.arbo.steamside.steam.client.internal.apps.home;

import java.util.function.Predicate;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.NotAvailableOnThisPlatform;

public class FilterPlatform implements Predicate<App> {

	@Override
	public boolean test(App app) {
		try {
			app.executable();
			return true;
		}
		catch (final NotAvailableOnThisPlatform e) {
			return false;
		}
		catch (final MissingFrom_appinfo_vdf e) {
			return false;
		}
	}

}
