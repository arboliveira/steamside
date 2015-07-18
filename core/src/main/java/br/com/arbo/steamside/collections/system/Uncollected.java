package br.com.arbo.steamside.collections.system;

import java.util.stream.Stream;

import br.com.arbo.steamside.collections.TagImpl;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.collections.TagsQueries.WithCount;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class Uncollected
{

	public Uncollected(Library library, TagsQueries tags)
	{
		this.library = library;
		this.tags = tags;
	}

	public Stream<TagImpl> apps()
	{
		return uncollectedIds().map(TagImpl::new);
	}

	public WithCount withCount()
	{
		return new WithCount()
		{

			@Override
			public CollectionName collection()
			{
				return new CollectionName("Games never tagged");
			}

			@Override
			public int count()
			{
				return (int) uncollectedIds().count();
			}
		};
	}

	Stream<AppId> uncollectedIds()
	{
		AppCriteria criteria = AppCriteria.OWNED;
		return library.allApps(criteria).map(app -> app.appid())
			.filter(appid -> !tags.isCollected(appid));
	}

	private final Library library;
	private final TagsQueries tags;

}
