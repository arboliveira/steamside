package br.com.arbo.steamside.data.collections;

import java.util.HashSet;

import br.com.arbo.steamside.apps.Apps.AppIdVisitor;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class CollectionData {

	public interface OnCollection {

		void accept(AppIdVisitor visitor);
	}

	public void add(final CollectionName name, final AppId appid) {
		appids.add(appid.appid);
	}

	public OnCollection on(final CollectionName name) {
		return new OnCollection() {

			@Override
			@SuppressWarnings("null")
			public void accept(final AppIdVisitor visitor) {
				for (final String appid : appids)
					visitor.each(new AppId(appid));
			}
		};
	}

	final HashSet<String> appids = new HashSet<String>();
}
