package br.com.arbo.steamside.apps;

import java.util.Collection;

public interface AppsCollection {

	void accept(App.Visitor visitor);

	int count();

	public static class Utils {

		public static AppsCollection adapt(final Collection<App> adapted) {
			return new AppsCollection() {

				@Override
				public void accept(final App.Visitor visitor) {
					for (final App app : adapted)
						visitor.each(app);
				}

				@Override
				public int count() {
					return adapted.size();
				}
			};
		}
	}
}