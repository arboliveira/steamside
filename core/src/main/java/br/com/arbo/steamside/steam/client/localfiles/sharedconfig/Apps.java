package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.Map;

import br.com.arbo.steamside.types.AppId;

public class Apps {

	public App app(final AppId appid) throws NotFound {
		final App app = apps.get(appid.appid);
		if (app != null) return app;
		throw NotFound.appid(appid.appid);
	}

	public static class NotFound extends Exception {

		public static NotFound appid(final String appid) {
			return new NotFound("No app with id: " + appid);
		}

		private NotFound(final String message) {
			super(message);
		}
	}

	public void accept(final AppIdVisitor visitor) {
		for (final String id : apps.keySet())
			visitor.each(new AppId(id));
	}

	public void accept(final AppVisitor visitor) {
		for (final App app : apps.values())
			visitor.each(app);
	}

	public interface AppIdVisitor {

		void each(AppId appid);
	}

	public interface AppVisitor {

		void each(App app);
	}

	public Map<String, App> apps;

	public int count() {
		return apps.size();
	}

}
