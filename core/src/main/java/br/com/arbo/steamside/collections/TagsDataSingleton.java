package br.com.arbo.steamside.collections;

import java.util.stream.Stream;

import javax.inject.Inject;

import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.steam.client.types.AppId;

public class TagsDataSingleton implements TagsData
{

	@Inject
	public TagsDataSingleton(SteamsideData steamside)
	{
		this.steamside = steamside;
	}

	@Override
	public Stream<? extends WithTags> allWithTags()
	{
		return actual().allWithTags();
	}

	@Override
	public Stream<? extends Tag> apps(CollectionI collection)
	{
		return actual().apps(collection);
	}

	@Override
	public CollectionsData collections()
	{
		return actual().collections();
	}

	@Override
	public boolean isCollected(AppId appid)
	{
		return actual().isCollected(appid);
	}

	@Override
	public boolean isTagged(AppId appid, CollectionI collection)
	{
		return actual().isTagged(appid, collection);
	}

	@Override
	public Stream<? extends WithCount> recent()
	{
		return actual().recent();
	}

	@Override
	public void tag(CollectionI c, Stream<AppId> apps) throws NotFound
	{
		actual().tag(c, apps);
	}

	@Override
	public void tagn(Stream<WithApps> withApps) throws NotFound
	{
		actual().tagn(withApps);
	}

	@Override
	public void tagRemember(CollectionI c, AppId appid)
		throws NotFound
	{
		actual().tagRemember(c, appid);
	}

	@Override
	public Stream<? extends CollectionI> tags(AppId app)
	{
		return actual().tags(app);
	}

	@Override
	public void untag(CollectionI c, AppId appid)
		throws NotFound
	{
		actual().untag(c, appid);
	}

	private TagsData actual()
	{
		return steamside.tags();
	}

	private final SteamsideData steamside;

}
