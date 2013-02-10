package br.com.arbo.steamside.vdf;

public class Apps {

	private final Region content;

	public Apps(Region content) {
		this.content = content;
	}

	public App app(String id) {
		Region rapp =
				content.region(id);
		return new App(rapp);
	}

	public void accept(final Visitor visitor) {
		content.accept(new KeyValueVisitor() {

			@Override
			public void onSubRegion(String k, Region r) throws Finished {
				visitor.each(k);
			}

			@Override
			public void onKeyValue(String k, String v) throws Finished {
				// 
			}
		});
	}

	public interface Visitor {

		void each(String appid);
	}
}
