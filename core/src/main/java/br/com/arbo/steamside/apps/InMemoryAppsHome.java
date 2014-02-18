package br.com.arbo.steamside.apps;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.Filter.Reject;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.Category;

import com.google.common.collect.ArrayListMultimap;

public class InMemoryAppsHome implements AppsHome {

	public void add(final App app) {
		apps.put(app.appid().appid, app);
		app.accept(new Category.Visitor() {

			@Override
			public void visit(final Category each) {
				categories.put(each.category, app);
			}
		});
	}

	@Override
	public App app(final AppId appid) throws NotFound {
		final App app = apps.get(appid.appid);
		if (app != null) return app;
		throw NotFound.appid(appid.appid);
	}

	@Override
	public int count() {
		return apps.size();
	}

	public void accept(final AppId.Visitor visitor) {
		for (final App app : apps.values())
			visitor.each(app.appid());
	}

	@Override
	public void accept(final App.Visitor visitor) {
		for (final App app : apps.values())
			visitor.each(app);
	}

	@Override
	public void accept(
			@NonNull final Filter filter, @NonNull final App.Visitor visitor) {
		for (final App app : apps.values())
			consider(app, filter, visitor);
	}

	private static void consider(
			final App app, final Filter filter,
			final @NonNull App.Visitor visitor) {
		try {
			filter.consider(app);
		} catch (final Reject e) {
			return;
		}
		visitor.each(app);
	}

	@Override
	public void accept(final CategoryWithAppsVisitor visitor) {
		for (final String each : categories.keySet()) {
			final AppsCollection itsApps =
					AppsCollection.Utils.adapt(categories.get(each));

			visitor.visit(new Category(each), itsApps);
		}
	}

	private final Map<String, App> apps = new HashMap<String, App>();
	final ArrayListMultimap<String, App> categories =
			ArrayListMultimap.<String, App> create();

}
