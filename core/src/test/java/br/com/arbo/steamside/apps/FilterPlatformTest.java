package br.com.arbo.steamside.apps;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppImpl;
import br.com.arbo.steamside.steam.client.apps.AppImpl.Builder;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;

public class FilterPlatformTest {

	@Test
	public void notAvailable__mustReject() {
		final Builder builder = new AppImpl.Builder();
		builder.appid("142857");
		builder.notAvailableOnThisPlatform(new NotAvailableOnThisPlatform());
		final App app = builder.make();

		assertFalse(subject.test(app));
	}

	private final FilterPlatform subject = new FilterPlatform();
}
