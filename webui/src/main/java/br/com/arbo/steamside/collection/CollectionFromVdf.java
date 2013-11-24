package br.com.arbo.steamside.collection;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.Apps;
import br.com.arbo.steamside.apps.Apps.AppVisitor;
import br.com.arbo.steamside.apps.Filter;
import br.com.arbo.steamside.container.SharedConfigConsume;

public class CollectionFromVdf {

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

	private final SharedConfigConsume sharedconfig;

	@Inject
	public CollectionFromVdf(
			final SharedConfigConsume sharedconfig) {
		this.sharedconfig = sharedconfig;
	}

}