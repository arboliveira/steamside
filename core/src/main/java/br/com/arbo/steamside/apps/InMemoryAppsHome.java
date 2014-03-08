package br.com.arbo.steamside.apps;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.Filter.Reject;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.Category;

import com.google.common.collect.ArrayListMultimap;

public class InMemoryAppsHome implements AppsHome {

	public void add(final App app) {
		apps.put(app.appid().appid, app);
		app.accept(each -> categories.put(each.category, app));
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

	public void forEachAppId(final Consumer<AppId> visitor) {
		apps.values().stream().map(app -> app.appid()).forEach(visitor);
	}

	@Override
	public void forEach(final Consumer<App> visitor) {
		apps.values().forEach(visitor);
	}

	@Override
	public void accept(
			@NonNull final Filter filter, @NonNull final Consumer<App> visitor) {
		for (final App app : apps.values())
			consider(app, filter, visitor);
	}

	private static void consider(
			final App app, final Filter filter,
			final @NonNull Consumer<App> visitor) {
		try {
			filter.consider(app);
		} catch (final Reject e) {
			return;
		}
		visitor.accept(app);
	}

	@Override
	public void accept(final CategoryWithAppsVisitor visitor) {
		for (final String each : categories.keySet()) {
			final AppsCollection itsApps =
					AppsCollection.Utils.adapt(categories.get(each));

			visitor.visit(new Category(each), itsApps);
		}
	}

	@Override
	public Stream<App> stream() {
		return apps.values().stream();
	}

	private final Map<String, App> apps = new HashMap<String, App>();

	final ArrayListMultimap<String, App> categories =
			ArrayListMultimap.<String, App> create();

}
