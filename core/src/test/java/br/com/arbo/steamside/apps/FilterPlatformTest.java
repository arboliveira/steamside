package br.com.arbo.steamside.apps;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import br.com.arbo.steamside.apps.AppImpl.Builder;
import br.com.arbo.steamside.apps.Filter.Reject;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.types.AppId;

public class FilterPlatformTest {

	@Ignore("Need a new Executable state: Undetermined")
	@Test
	public void notFoundIn_appinfo_vdf__mustBeAccepted() throws Reject {
		final Builder builder = new AppImpl.Builder();
		builder.appid("142857");
		final App app = builder.make();

		context.checking(new Expectations()/*@formatter:off*/{{/*@formatter:on*/
				final AppId appid = new AppId("142857");
				oneOf(appinfo).get(appid);
				will(throwException(MissingFrom_appinfo_vdf.appid(appid)));
			}
		});

		new FilterPlatform().consider(app);
	}

	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	final Data_appinfo_vdf appinfo = context.mock(Data_appinfo_vdf.class);
}
