package br.com.arbo.steamside.apps;

import static org.junit.Assert.fail;

import org.junit.Test;

import br.com.arbo.steamside.apps.AppImpl.Builder;
import br.com.arbo.steamside.apps.Filter.Reject;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;

public class FilterPlatformTest {

	@Test
	public void notAvailable__mustReject() {
		final Builder builder = new AppImpl.Builder();
		builder.appid("142857");
		builder.notAvailableOnThisPlatform(new NotAvailableOnThisPlatform());
		final App app = builder.make();

		try {
			subject.consider(app);
			fail();
		} catch (Reject ex) {
			// all right!
		}
	}

	private final FilterPlatform subject = new FilterPlatform();
}
