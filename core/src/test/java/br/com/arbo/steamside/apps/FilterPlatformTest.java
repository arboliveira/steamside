package br.com.arbo.steamside.apps;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppImpl;
import br.com.arbo.steamside.steam.client.apps.FilterPlatform;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;

public class FilterPlatformTest
{

	@Test
	public void notAvailable__mustReject()
	{
		App app = new AppImpl.Builder()
			.appid("142857")
			.notAvailableOnThisPlatform(new NotAvailableOnThisPlatform())
			.make();
		assertFalse(subject.test(app));
	}

	private final FilterPlatform subject = new FilterPlatform();
}
