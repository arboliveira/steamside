package br.com.arbo.steamside.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.collections.CollectionsQueries.WithCount;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.types.AppId;

public class InMemoryTagsHome implements TagsData {

	{
		appsByCollection = new AppsByCollection();
		collectionsByApp = new CollectionsByApp();
		tagsByCollection = new TagsByCollection();
	}

	public InMemoryTagsHome(InMemoryCollectionsHome collections)
	{
		this.collections = collections;
		this.recent = new Recent();
	}

	@Override
	public Stream< ? extends WithCount> allWithCount(AppCriteria criteria)
	{
		return appsByCollection.map.entrySet().stream().map(this::withCount);
	}

	@Override
	public Stream< ? extends Tag> apps(CollectionI c)
	{
		final CollectionImpl stored = stored(c);
		return appsIn(stored);
	}

	@Override
	public CollectionsData collections()
	{
		return collections;
	}

	@Override
	public boolean isCollected(AppId appid)
	{
		return collectionsByApp.isCollected(appid);
	}

	@Override
	public Stream< ? extends WithCount> recent()
	{
		return recent.values().stream().map(this::withCount);
	}

	@Override
	public void tag(
			@NonNull final CollectionI c,
			@NonNull final AppId appid) throws NotFound
	{
		CollectionImpl stored = stored(c);
		doTag(stored, appid);
	}

	@Override
	public void tag(CollectionI c, Stream<AppId> apps) throws NotFound
	{
		CollectionImpl stored = stored(c);
		apps.forEach(appid -> doTag(stored, appid));
	}

	@Override
	public void tagRemember(
			@NonNull final CollectionI c,
			@NonNull final AppId appid) throws NotFound
	{
		CollectionImpl stored = stored(c);
		doTag(stored, appid);
		recent.tagged(stored);
	}

	@Override
	public Stream< ? extends CollectionI> tags(AppId app)
	{
		return collectionsByApp.collections(app);
	}

	Stream<TagImpl> appsIn(final CollectionImpl stored)
	{
		return tagsByCollection.tags(stored);
	}

	void doTag(CollectionImpl c, @NonNull final AppId appid)
	{
		appsByCollection.tag(c, appid);
		collectionsByApp.tag(c, appid);
		tagsByCollection.tag(c, appid);
	}

	WithCount withCount(CollectionI c)
	{
		return new WithCount() {

			@Override
			public CollectionI collection()
			{
				return c;
			}

			@Override
			public int count()
			{
				return 0;
			}

		};
	}

	WithCount withCount(final Map.Entry<CollectionImpl, Collection<AppId>> e)
	{
		return new WithCount() {

			@Override
			public CollectionI collection()
			{
				return e.getKey();
			}

			@Override
			public int count()
			{
				return e.getValue().size();
			}

		};
	}

	private CollectionImpl stored(CollectionI c)
	{
		return collections.stored(c);
	}

	static class AppsByCollection {

		Stream<AppId> apps(CollectionImpl c)
		{
			Collection<AppId> v = map.get(c);
			if (v == null) return Stream.empty();
			return v.stream();
		}

		void tag(CollectionImpl c, AppId a)
		{
			map.computeIfAbsent(c, k -> new HashSet<AppId>()).add(a);
		}

		Map<CollectionImpl, Collection<AppId>> map = new IdentityHashMap<>();
	}

	static class CollectionsByApp {

		public boolean isCollected(AppId appid)
		{
			return map.containsKey(appid);
		}

		Stream<CollectionImpl> collections(AppId a)
		{
			Collection<CollectionImpl> v = map.get(a);
			if (v == null) return Stream.empty();
			return v.stream();
		}

		void tag(CollectionImpl c, AppId a)
		{
			map.computeIfAbsent(a, k -> new HashSet<CollectionImpl>()).add(c);
		}

		Map<AppId, Collection<CollectionImpl>> map = new HashMap<>();

	}

	static class Recent extends LinkedHashMap<String, CollectionImpl> {

		@Override
		protected boolean removeEldestEntry(
				java.util.Map.Entry<String, CollectionImpl> eldest)
		{
			return size() > 6;
		}

		void tagged(CollectionImpl c)
		{
			this.put(c.name().value, c);
		}

	}

	static class TagsByCollection {

		void tag(CollectionImpl c, @NonNull AppId a)
		{
			if (a == null) throw new NullPointerException();
			map.computeIfAbsent(c, k -> new HashMap<AppId, TagImpl>())
					.computeIfAbsent(a, TagImpl::new);
		}

		Stream<TagImpl> tags(CollectionImpl c)
		{
			Map<AppId, TagImpl> v = map.get(c);
			if (v == null) return Stream.empty();
			return v.values().stream();
		}

		Map<CollectionImpl, Map<AppId, TagImpl>> map = new IdentityHashMap<>();
	}

	final AppsByCollection appsByCollection;

	final CollectionsByApp collectionsByApp;

	final TagsByCollection tagsByCollection;

	private final InMemoryCollectionsHome collections;

	private final Recent recent;

}