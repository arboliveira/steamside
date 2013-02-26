package br.com.arbo.steamside.vdf;

public class AppsImpl implements Apps {

	private final RegionImpl content;

	public AppsImpl(final RegionImpl content) {
		this.content = content;
	}

	@Override
	public App app(final String id) throws NotFound {
		final RegionImpl rapp = content.region(id);
		return new AppImpl(rapp);
	}

	@Override
	public void accept(final Visitor visitor) {
		content.accept(new KeyValueVisitor() {

			@Override
			public void onSubRegion(final String k, final RegionImpl r)
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
}
