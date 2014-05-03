package br.com.arbo.steamside.data;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.CollectionsWrites;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.kids.Kid;
import br.com.arbo.steamside.kids.KidsData;
import br.com.arbo.steamside.kids.KidsWrites;
import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class ObservableSteamsideData implements SteamsideData {

	public ObservableSteamsideData(SteamsideData data) {
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
		return new AutoSaveCollections();
	}

	@Override
	public KidsData kids()
	{
		return new AutoSaveKids();
	}

	CollectionsData realCollections()
	{
		return steamside.collections();
	}

	KidsData realKids()
	{
		return steamside.kids();
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
		public Stream< ? extends Tag> apps(CollectionI collection)
		{
			return realCollections().apps(collection);
		}

		@Override
		@NonNull
		public CollectionI find(CollectionName name) throws NotFound
		{
			return realCollections().find(name);
		}

		@Override
		public boolean isCollected(AppId appid)
		{
			return realCollections().isCollected(appid);
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

	class CollectionsWritesSpy implements CollectionsWrites {

		@Override
		public void add(@NonNull CollectionI in) throws Duplicate
		{
			realCollections().add(in);
			changed();
		}

		@Override
		public void tag(@NonNull CollectionI c, @NonNull AppId appid)
				throws NotFound
		{
			realCollections().tag(c, appid);
			changed();
		}

		@Override
		public void tag(CollectionI c, Stream<AppId> apps) throws NotFound
		{
			realCollections().tag(c, apps);
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

	private final ArrayList<Observer> listeners = new ArrayList<>(1);

	private final SteamsideData steamside;
}
