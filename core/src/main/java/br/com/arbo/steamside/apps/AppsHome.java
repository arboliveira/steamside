package br.com.arbo.steamside.apps;

import java.util.List;

public interface AppsHome {

	interface Visitor {

		void visit(App each);
	}

	void accept(AppsHome.Visitor visitor);

	public static class Utils {

		public static AppsHome adapt(final List<App> list) {
			return new AppsHome() {

				@Override
				public void accept(final AppsHome.Visitor visitor) {
					for (final App app : list)
						visitor.visit(app);
				}
			};
		}
	}
}