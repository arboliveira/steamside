package br.com.arbo.steamside.data;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.CollectionsQueries.WithCount;
import br.com.arbo.steamside.collections.CollectionsWrites;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.collections.TagsWrites;
import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.kids.Kid;
import br.com.arbo.steamside.kids.KidsData;
import br.com.arbo.steamside.kids.KidsWrites;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class ObservableSteamsideData implements SteamsideData {

	public ObservableSteamsideData(SteamsideData data)
	{
		steamside = data;
	}

	public void addObserver(Observer listener)
	{
		listeners.add(listener);
	}

	public void changed()
	{
		listeners.stream().forEach(l -> l.onChange());
	}

	@Override
	public CollectionsData collections()
	{
		return autoSaveCollections;
	}

	@Override
	public KidsData kids()
	{
		return new AutoSaveKids();
	}

	@Override
	public TagsData tags()
	{
		return new AutoSaveTags();
	}

	CollectionsData realCollections()
	{
		return steamside.collections();
	}

	KidsData realKids()
	{
		return steamside.kids();
	}

	TagsData realTags()
	{
		return steamside.tags();
	}

	public interface Observer {

		void onChange();

	}

	class AutoSaveCollections
			extends CollectionsWritesSpy
			implements CollectionsData {

		@Override
		public Stream< ? extends CollectionI> all()
		{
			return realCollections().all();
		}

		@Override
		@NonNull
		public CollectionI find(CollectionName name) throws NotFound
		{
			return realCollections().find(name);
		}

	}

	class AutoSaveKids
			extends KidsWritesSpy
			implements KidsData {

		@Override
		public Stream<Kid> all()
		{
			return realKids().all();
		}

		@Override
		public Kid find(User user) throws br.com.arbo.steamside.kids.NotFound
		{
			return realKids().find(user);
		}

	}

	class AutoSaveTags
			extends TagsWritesSpy
			implements TagsData {

		@Override
		public Stream< ? extends WithCount> allWithCount(AppCriteria criteria)
		{
			return realTags().allWithCount(criteria);
		}

		@Override
		public Stream< ? extends Tag> apps(CollectionI collection)
		{
			return realTags().apps(collection);
		}

		@Override
		public CollectionsData collections()
		{
			return autoSaveCollections;
		}

		@Override
		public boolean isCollected(AppId appid)
		{
			return realTags().isCollected(appid);
		}

		@Override
		public Stream< ? extends WithCount> recent()
		{
			return realTags().recent();
		}

		@Override
		public Stream< ? extends CollectionI> tags(AppId app)
		{
			return realTags().tags(app);
		}
	}

	class CollectionsWritesSpy implements CollectionsWrites {

		@Override
		public void add(@NonNull CollectionI in) throws Duplicate
		{
			realCollections().add(in);
			changed();
		}

	}

	class KidsWritesSpy implements KidsWrites {

		@Override
		public void add(Kid kid) throws br.com.arbo.steamside.kids.Duplicate
		{
			realKids().add(kid);
			changed();
		}

	}

	class TagsWritesSpy implements TagsWrites {

		@Deprecated
		@Override
		public void tag(@NonNull CollectionI c, @NonNull AppId appid)
				throws NotFound
		{
			realTags().tag(c, appid);
			changed();
		}

		@Override
		public void tag(CollectionI c, Stream<AppId> apps) throws NotFound
		{
			realTags().tag(c, apps);
			changed();
		}

		@Override
		public void tagRemember(@NonNull CollectionI c, @NonNull AppId appid)
				throws NotFound
		{
			realTags().tagRemember(c, appid);
			changed();
		}

	}

	final AutoSaveCollections autoSaveCollections = new AutoSaveCollections();

	private final ArrayList<Observer> listeners = new ArrayList<>(1);

	private final SteamsideData steamside;
}
