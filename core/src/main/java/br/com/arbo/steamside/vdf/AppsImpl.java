package br.com.arbo.steamside.vdf;

import br.com.arbo.steamside.types.AppId;

public class AppsImpl implements Apps {

	private final RegionImpl content;

	public AppsImpl(final RegionImpl content) {
		this.content = content;
	}

	@Override
	public App app(final AppId id) throws NotFound {
		final RegionImpl rapp = content.region(id.appid);
		return new AppImpl(rapp);
	}

	@Override
	public void accept(final AppIdVisitor visitor) {
		content.accept(new KeyValueVisitor() {

			@Override
			public void onSubRegion(final String k, final RegionImpl r)
					throws Finished {
				visitor.each(new AppId(k));
			}

			@Override
			public void onKeyValue(final String k, final String v)
					throws Finished {
				// 
			}
		});
	}
}
