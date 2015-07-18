package br.com.arbo.steamside.collections.system;

import java.util.stream.Stream;

import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagImpl;
import br.com.arbo.steamside.collections.TagsQueries.WithCount;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.types.CollectionName;

public class Everything
{

	public Everything(Library library)
	{
		this.library = library;
	}

	public Stream< ? extends Tag> apps()
	{
		AppCriteria criteria = AppCriteria.OWNED;

		return library.allApps(criteria).map(app -> app.appid())
			.map(TagImpl::new);
	}

	public WithCount withCount()
	{
		return new WithCount()
		{

			@Override
			public CollectionName collection()
			{
				return new CollectionName("Games owned");
			}

			@Override
			public int count()
			{
				AppCriteria criteria = AppCriteria.OWNED;

				return library.count(criteria);
			}

		};
	}

	final Library library;
}
