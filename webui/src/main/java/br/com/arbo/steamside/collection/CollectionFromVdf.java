package br.com.arbo.steamside.collection;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.Apps;
import br.com.arbo.steamside.apps.Apps.AppVisitor;
import br.com.arbo.steamside.apps.Filter;
import br.com.arbo.steamside.webui.wicket.SharedConfigConsume;

public class CollectionFromVdf {

	private final SharedConfigConsume sharedconfig;

	public CollectionFromVdf(
			final SharedConfigConsume sharedconfig) {
		this.sharedconfig = sharedconfig;
	}

	public List<App> query(@NonNull final Filter filter) {
		final Apps apps = sharedconfig.data().apps();
		final List<App> list = new ArrayList<App>(apps.count());

		final AppVisitor add = new AppVisitor() {

			@Override
			public void each(final App app) {
				list.add(app);
			}

		};

		apps.accept(filter, add);

		return list;
	}
}