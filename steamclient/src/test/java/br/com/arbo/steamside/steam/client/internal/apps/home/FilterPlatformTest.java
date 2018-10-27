package br.com.arbo.steamside.steam.client.internal.apps.home;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppImpl;
import br.com.arbo.steamside.steam.client.internal.apps.home.FilterPlatform;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.NotAvailableOnThisPlatform;

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
