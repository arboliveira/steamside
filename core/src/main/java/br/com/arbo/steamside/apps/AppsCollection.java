package br.com.arbo.steamside.apps;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface AppsCollection {

	Stream<App> stream();

	void forEach(Consumer<App> visitor);

	int count();

	public static class Utils {

		public static AppsCollection adapt(final Collection<App> adapted) {
			return new AppsCollection() {

				@Override
				public void forEach(final Consumer<App> visitor) {
					adapted.forEach(visitor);
				}

				@Override
				public int count() {
					return adapted.size();
				}

				@Override
				public Stream<App> stream() {
					return adapted.stream();
				}
			};
		}
	}
}