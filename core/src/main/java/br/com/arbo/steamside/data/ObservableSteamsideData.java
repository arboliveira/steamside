package br.com.arbo.steamside.data;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.CollectionsWrites;
import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class ObservableSteamsideData implements SteamsideData {

	public ObservableSteamsideData(SteamsideData data) {
		steamside = data;
	}

	public void addObserver(Observer listener) {
		listeners.add(listener);
	}

	public void changed() {
		listeners.stream().forEach(l -> l.onChange());
	}

	@Override
	public CollectionsData collections() {
		return new AutoSaveCollections();
	}

	CollectionsData realCollections() {
		return steamside.collections();
	}

	public interface Observer {

		void onChange();

	}

	class AutoSaveCollections
			extends CollectionsWritesSpy
			implements CollectionsData {

		@Override
		public Stream< ? extends CollectionI> all() {
			return realCollections().all();
		}

		@Override
		@NonNull
		public CollectionI find(CollectionName name) throws NotFound {
			return realCollections().find(name);
		}

	}

	class CollectionsWritesSpy implements CollectionsWrites {

		@Override
		public void add(@NonNull CollectionI in) throws Duplicate {
			realCollections().add(in);
			changed();
		}

		@Override
		public void tag(@NonNull CollectionI c, @NonNull AppId appid)
				throws NotFound {
			realCollections().tag(c, appid);
			changed();
		}

	}

	private final ArrayList<Observer> listeners = new ArrayList<>(1);

	private final SteamsideData steamside;
}
