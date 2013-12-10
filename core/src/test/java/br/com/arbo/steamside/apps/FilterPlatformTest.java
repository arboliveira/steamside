package br.com.arbo.steamside.apps;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import br.com.arbo.steamside.apps.App.Builder;
import br.com.arbo.steamside.apps.Filter.Reject;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.I_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.NotFound;

public class FilterPlatformTest {

	@Test
	public void notFoundIn_appinfo_vdf__mustBeAccepted() throws Reject {
		final Builder builder = new App.Builder();
		builder.appid("142857");
		final App app = builder.make();

		context.checking(new Expectations()/*@formatter:off*/{{/*@formatter:on*/
				oneOf(appinfo).get("142857");
				will(throwException(NotFound.appid("142857")));
			}
		});

		new FilterPlatform(appinfo).consider(app);
	}

	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	final I_appinfo_vdf appinfo = context.mock(I_appinfo_vdf.class);
}
