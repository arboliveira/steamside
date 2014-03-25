package br.com.arbo.steamside.data.autowire;

import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.kids.KidsData;

public class AutowireSteamsideData implements SteamsideData {

	@Override
	public CollectionsData collections()
	{
		return data.collections();
	}

	public SteamsideData get()
	{
		return data;
	}

	@Override
	public KidsData kids()
	{
		return data.kids();
	}

	public void set(SteamsideData data)
	{
		this.data = data;
	}

	private SteamsideData data;
}
