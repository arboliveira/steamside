package br.com.arbo.steamside.vdf;

public class Apps {

	private final Region content;

	public Apps(final Region content) {
		this.content = content;
	}

	public App app(final String id) throws NotFound {
		final Region rapp = content.region(id);
		return new App(rapp);
	}

	public void accept(final Visitor visitor) {
		content.accept(new KeyValueVisitor() {

			@Override
			public void onSubRegion(final String k, final Region r)
					throws Finished {
				visitor.each(k);
			}

			@Override
			public void onKeyValue(final String k, final String v)
					throws Finished {
				// 
			}
		});
	}

	public interface Visitor {

		void each(String appid);
	}
}
