package br.com.arbo.steamside.vdf;

import br.com.arbo.steamside.vdf.Region.KeyValueVisitor;

public class App {

	private final Region content;

	public App(Region content) {
		this.content = content;
	}

	public void category(String category) {
		class CategoryChange implements KeyValueVisitor {

			@Override
			public void onKeyValue(String k, String v) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSubRegion(String k, Region r) {
				// TODO Auto-generated method stub

			}

		}
		Region tags = content.region("tags");
		KeyValueVisitor categoryChange = new CategoryChange();
		tags.accept(categoryChange);

	}
}