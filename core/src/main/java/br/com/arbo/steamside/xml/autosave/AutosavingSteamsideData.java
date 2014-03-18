package br.com.arbo.steamside.xml.autosave;

import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.data.InMemorySteamsideDataBox;
import br.com.arbo.steamside.data.SteamsideData;

public class AutosavingSteamsideData implements SteamsideData {

	public AutosavingSteamsideData(InMemorySteamsideDataBox data) {
		this.data = data;
	}

	@Override
	public CollectionsData collections() {
		return data.getData().collections();
	}

	private final InMemorySteamsideDataBox data;

}
