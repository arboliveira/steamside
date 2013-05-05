package br.com.arbo.steamside.apps;

import java.util.HashMap;
import java.util.Map;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.Category;

import com.google.common.collect.ArrayListMultimap;

public class Apps {

	public void add(final App app) {
		apps.put(app.appid().appid, app);
		app.accept(new Category.Visitor() {

			@Override
			public void visit(final Category each) {
				categories.put(each.category, app);
			}
		});
	}

	public App app(final AppId appid) throws NotFound {
		final App app = apps.get(appid.appid);
		if (app != null) return app;
		throw NotFound.appid(appid.appid);
	}

	public int count() {
		return apps.size();
	}

	public void accept(final AppIdVisitor visitor) {
		for (final App app : apps.values())
			visitor.each(app.appid());
	}

	public void accept(final AppVisitor visitor) {
		for (final App app : apps.values())
			visitor.each(app);
	}

	public void accept(final Category.Visitor visitor) {
		for (final String each : categories.keySet())
			visitor.visit(new Category(each));
	}

	public static class NotFound extends Exception {

		public static NotFound appid(final String appid) {
			return new NotFound("No app with id: " + appid);
		}

		private NotFound(final String message) {
			super(message);
		}
	}

	public interface AppIdVisitor {

		void each(AppId appid);
	}

	public interface AppVisitor {

		void each(App app);
	}

	private final Map<String, App> apps = new HashMap<String, App>();
	final ArrayListMultimap<String, App> categories =
			ArrayListMultimap.<String, App> create();

}
