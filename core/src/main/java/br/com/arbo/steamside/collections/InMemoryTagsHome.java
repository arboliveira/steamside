package br.com.arbo.steamside.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.types.AppId;

public class InMemoryTagsHome {

	{
		appsByCollection = new AppsByCollection();
		collectionsByApp = new CollectionsByApp();
		tagsByCollection = new TagsByCollection();
	}

	void doTag(CollectionImpl c, @NonNull final AppId appid)
	{
		appsByCollection.tag(c, appid);
		collectionsByApp.tag(c, appid);
		tagsByCollection.tag(c, appid);
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
}
