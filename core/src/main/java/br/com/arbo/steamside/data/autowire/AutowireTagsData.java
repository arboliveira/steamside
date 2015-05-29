package br.com.arbo.steamside.data.autowire;

import java.util.stream.Stream;

import javax.inject.Inject;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.steam.client.types.AppId;

public class AutowireTagsData implements TagsData
{

	@Inject
	public AutowireTagsData(AutowireSteamsideData steamside)
	{
		this.steamside = steamside;
	}

	@Override
	public Stream<? extends WithTags> allWithTags()
	{
		return reloadable().allWithTags();
	}

	@Override
	public Stream<? extends Tag> apps(CollectionI collection)
	{
		return reloadable().apps(collection);
	}

	@Override
	public CollectionsData collections()
	{
		return reloadable().collections();
	}

	@Override
	public boolean isCollected(AppId appid)
	{
		return reloadable().isCollected(appid);
	}

	@Override
	public boolean isTagged(AppId appid, CollectionI collection)
	{
		return reloadable().isTagged(appid, collection);
	}

	@Override
	public Stream<? extends WithCount> recent()
	{
		return reloadable().recent();
	}

	@Override
	public void tag(CollectionI c, Stream<AppId> apps) throws NotFound
	{
		reloadable().tag(c, apps);
	}

	@Override
	public void tagn(Stream<WithApps> withApps) throws NotFound
	{
		reloadable().tagn(withApps);
	}

	@Override
	public void tagRemember(CollectionI c, AppId appid)
		throws NotFound
	{
		reloadable().tagRemember(c, appid);
	}

	@Override
	public Stream<? extends CollectionI> tags(AppId app)
	{
		return reloadable().tags(app);
	}

	@Override
	public void untag(CollectionI c, AppId appid)
		throws NotFound
	{
		reloadable().untag(c, appid);
	}

	private TagsData reloadable()
	{
		return steamside.tags();
	}

	private final AutowireSteamsideData steamside;

}
