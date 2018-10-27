package br.com.arbo.steamside.collections.system;

import java.util.stream.Stream;

import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagImpl;
import br.com.arbo.steamside.collections.TagsQueries.WithCount;
import br.com.arbo.steamside.steam.client.apps.home.AppCriteria;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.types.CollectionName;

public class Everything
{

	public Stream< ? extends Tag> apps()
	{
		AppCriteria criteria = AppCriteria.OWNED;

		return steamClientHome.apps().stream(criteria).map(app -> app.appid())
			.map(TagImpl::new);
	}

	public WithCount withCount()
	{
		return new WithCount()
		{

			@Override
			public CollectionName collection()
			{
				return new CollectionName(NAME);
			}

			@Override
			public int count()
			{
				AppCriteria criteria = AppCriteria.OWNED;

				return steamClientHome.apps().count(criteria);
			}

		};
	}

	public Everything(SteamClientHome steamClientHome)
	{
		this.steamClientHome = steamClientHome;
	}

	final SteamClientHome steamClientHome;

	private static final String NAME = "Owned by you";
}
