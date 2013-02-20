package br.com.arbo.steamside.continues.examples;

import javax.inject.Inject;

import br.com.arbo.org.codehaus.jackson.map.JsonUtils;
import br.com.arbo.steamside.continues.Continue;
import br.com.arbo.steamside.webui.wicket.ContinueNeedsImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;

public class GuiceSpike {

	@Inject
	Continue.Needs continueNeeds;

	public static void main(final String[] args) {
		final GuiceSpike spike = new GuiceSpike();
		final Injector injector = Guice.createInjector(new GuiceSpikeModule());
		injector.injectMembers(spike);
		System.out.println(
				JsonUtils.asString(new Continue(spike.continueNeeds).fetch()));
	}

	static class GuiceSpikeModule extends AbstractModule {

		@Override
		protected void configure() {
			bind(Continue.Needs.class)
					.to(ContinueNeedsImpl.class)
					.in(Scopes.SINGLETON);
		}

	}
}
