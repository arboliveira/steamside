package br.com.arbo.steamside.data.autowire;

import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.data.SteamsideData;

public class AutowireSteamsideData implements SteamsideData {

	@Override
	public CollectionsData collections() {
		return data.collections();
	}

	public SteamsideData get() {
		return data;
	}

	public void set(SteamsideData data) {
		this.data = data;
	}

	private SteamsideData data;
}
