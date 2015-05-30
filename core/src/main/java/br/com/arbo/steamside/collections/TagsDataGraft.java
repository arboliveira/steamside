package br.com.arbo.steamside.collections;

import java.util.stream.Stream;

import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.steam.client.types.AppId;

public class TagsDataGraft implements TagsData
{

	public TagsDataGraft(TagsQueries reads, TagsWrites writes,
		CollectionsData collections)
	{
		this.reads = reads;
		this.writes = writes;
		this.collections = collections;
	}

	@Override
	public Stream< ? extends WithTags> allWithTags()
	{
		return reads.allWithTags();
	}

	@Override
	public Stream< ? extends Tag> apps(CollectionI collection)
	{
		return reads.apps(collection);
	}

	@Override
	public CollectionsData collections()
	{
		return collections;
	}

	@Override
	public boolean isCollected(AppId appid)
	{
		return reads.isCollected(appid);
	}

	@Override
	public boolean isTagged(AppId appid, CollectionI collection)
	{
		return reads.isTagged(appid, collection);
	}

	@Override
	public Stream< ? extends WithCount> recent()
	{
		return reads.recent();
	}

	@Override
	public void tag(CollectionI c, Stream<AppId> apps)
	{
		writes.tag(c, apps);
	}

	@Override
	public void tagn(Stream<WithApps> withApps) throws NotFound
	{
		writes.tagn(withApps);
	}

	@Override
	public void tagRemember(CollectionI c, AppId appid)
	{
		writes.tagRemember(c, appid);
	}

	@Override
	public Stream< ? extends CollectionI> tags(AppId app)
	{
		return reads.tags(app);
	}

	@Override
	public void untag(CollectionI c, AppId appid)
	{
		writes.untag(c, appid);
	}

	private final CollectionsData collections;

	private final TagsQueries reads;

	private final TagsWrites writes;
}