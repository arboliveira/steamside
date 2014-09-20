package br.com.arbo.steamside.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.collections.InMemoryCollectionsHome.DeleteListener;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class InMemoryTagsHome implements TagsData, DeleteListener {

	static WithTags withTags(
			Map.Entry<CollectionImpl, Map<AppId, TagImpl>> e)
	{
		return new WithTags() {

			@Override
			public CollectionI collection()
			{
				return e.getKey();
			}

			@Override
			public Stream<TagImpl> tags()
			{
				final Map<AppId, TagImpl> tags = e.getValue();
				return tags.values().stream();
			}

		};
	}

	{
		appsByCollection = new AppsByCollection();
		collectionsByApp = new CollectionsByApp();
		recent = new Recent();
		tagsByCollection = new TagsByCollection();
	}

	public InMemoryTagsHome(InMemoryCollectionsHome collections)
	{
		collections.addListener(this);
		this.collections = collections;
	}

	@Override
	public Stream< ? extends WithTags> allWithTags()
	{
		return tagsByCollection.map.entrySet().stream().map(
				e -> withTags(e));
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
	public boolean isTagged(AppId appid, CollectionI collection)
	{
		final CollectionImpl stored = stored(collection);
		return appsByCollection.isTagged(appid, stored);
	}

	@Override
	public void onDelete(CollectionImpl c)
	{
		appsByCollection.onDelete(c);
		collectionsByApp.onDelete(c);
		recent.onDelete(c);
		tagsByCollection.onDelete(c);
	}

	@Override
	public Stream< ? extends WithCount> recent()
	{
		return recent.values().stream().map(this::withCount);
	}

	public void rememberRecentTag(CollectionName collectionName)
	{
		try
		{
			CollectionI c = collections().find(collectionName);
			CollectionImpl stored = stored(c);
			doRememberRecentTag(stored);
		}
		catch (NotFound e)
		{
			// Collection was deleted but name is still in recent tags
			return;
		}
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
		doRememberRecentTag(stored);
	}

	@Override
	public Stream< ? extends CollectionI> tags(AppId app)
	{
		return collectionsByApp.collections(app);
	}

	@Override
	public void untag(@NonNull CollectionI c, @NonNull AppId appid)
			throws NotFound
	{
		CollectionImpl stored = stored(c);
		doUntag(stored, appid);
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

	void doUntag(CollectionImpl c, @NonNull final AppId appid)
	{
		appsByCollection.untag(c, appid);
		collectionsByApp.untag(c, appid);
		tagsByCollection.untag(c, appid);
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

	private void doRememberRecentTag(CollectionImpl stored)
	{
		recent.tagged(stored);
	}

	private CollectionImpl stored(CollectionI c)
	{
		return collections.stored(c);
	}

	static class AppsByCollection {

		Stream<AppId> apps(CollectionImpl c)
		{
			return getOptional(c)
					.map(Collection::stream)
					.orElse(Stream.empty());
		}

		boolean isTagged(AppId a, CollectionImpl c)
		{
			return getOptional(c)
					.map(v -> v.contains(a))
					.orElse(false);
		}

		void onDelete(CollectionImpl c)
		{
			map.remove(c);
		}

		void tag(CollectionImpl c, AppId a)
		{
			map
					.computeIfAbsent(c, k -> new HashSet<AppId>())
					.add(a);
		}

		void untag(CollectionImpl c, AppId a)
		{
			getOptional(c)
					.ifPresent(v -> v.remove(a));
		}

		private Optional<Collection<AppId>> getOptional(CollectionImpl c)
		{
			return Optional.ofNullable(map.get(c));
		}

		Map<CollectionImpl, Collection<AppId>> map = new IdentityHashMap<>();
	}

	static class CollectionsByApp {

		Stream<CollectionImpl> collections(AppId a)
		{
			return getOptional(a)
					.map(v -> v.stream())
					.orElse(Stream.empty());
		}

		boolean isCollected(AppId appid)
		{
			return map.containsKey(appid);
		}

		void onDelete(CollectionImpl c)
		{
			map.forEach((appid, collections) -> collections.remove(c));
		}

		void tag(CollectionImpl c, AppId a)
		{
			map
					.computeIfAbsent(a, k -> new HashSet<CollectionImpl>())
					.add(c);
		}

		void untag(CollectionImpl c, AppId a)
		{
			getOptional(a)
					.ifPresent(v -> v.remove(c));
		}

		private Optional<Collection<CollectionImpl>> getOptional(AppId a)
		{
			return Optional.ofNullable(map.get(a));
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

		void onDelete(CollectionImpl c)
		{
			this.remove(c.name().value);
		}

		void tagged(CollectionImpl c)
		{
			this.put(c.name().value, c);
		}

	}

	static class TagsByCollection {

		void onDelete(CollectionImpl c)
		{
			map.remove(c);
		}

		void tag(CollectionImpl c, @NonNull AppId a)
		{
			Objects.requireNonNull(a);
			map
					.computeIfAbsent(c, k -> new HashMap<AppId, TagImpl>())
					.computeIfAbsent(a, TagImpl::new);
		}

		Stream<TagImpl> tags(CollectionImpl c)
		{
			final Optional<Map<AppId, TagImpl>> optional = getOptional(c);
			return optional
					.map(v -> v.values().stream())
					.orElse(Stream.empty());
		}

		void untag(CollectionImpl c, AppId a)
		{
			getOptional(c)
					.ifPresent(v -> v.remove(a));
		}

		private Optional<Map<AppId, TagImpl>> getOptional(CollectionImpl c)
		{
			return Optional.ofNullable(map.get(c));
		}

		Map<CollectionImpl, Map<AppId, TagImpl>> map = new IdentityHashMap<>();
	}

	final AppsByCollection appsByCollection;

	final CollectionsByApp collectionsByApp;

	final TagsByCollection tagsByCollection;

	private final InMemoryCollectionsHome collections;

	private final Recent recent;

}