package br.com.arbo.steamside.data.autowire;

import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.kids.KidsData;

public class AutowireSteamsideData implements SteamsideData {

	@Override
	public CollectionsData collections()
	{
		return reloadable.collections();
	}

	public SteamsideData get()
	{
		return reloadable;
	}

	@Override
	public KidsData kids()
	{
		return reloadable.kids();
	}

	public void set(SteamsideData reloaded)
	{
		this.reloadable = reloaded;
	}

	@Override
	public TagsData tags()
	{
		return reloadable.tags();
	}

	private SteamsideData reloadable;
}
