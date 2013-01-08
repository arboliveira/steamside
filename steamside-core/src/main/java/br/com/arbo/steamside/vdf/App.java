package br.com.arbo.steamside.vdf;

public class App {

	private final Region content;

	public App(Region content) {
		this.content = content;
	}

	public void category(String category) {
		class CategoryChange implements Region.KeyValueVisitor {

			@Override
			public void visit(KeyValue kv) {
				// TODO Auto-generated method stub
			}

		}
		Region tags = content.region("tags");
		Region.KeyValueVisitor categoryChange = new CategoryChange();
		tags.accept(categoryChange);

	}
}