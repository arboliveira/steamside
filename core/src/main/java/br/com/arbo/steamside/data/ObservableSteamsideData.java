package br.com.arbo.steamside.data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.CollectionsQueries;
import br.com.arbo.steamside.collections.CollectionsWrites;
import br.com.arbo.steamside.collections.FavoriteNotSet;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.collections.TagsWrites;
import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.kids.Kid;
import br.com.arbo.steamside.kids.Kids;
import br.com.arbo.steamside.kids.KidsData;
import br.com.arbo.steamside.kids.KidsWrites;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class ObservableSteamsideData implements SteamsideData {

	static Object invokeMethod(Object obj, Method method, Object[] args)
			throws IllegalAccessException, InvocationTargetException
	{
		try
		{
			return method.invoke(obj, args);
		}
		catch (InvocationTargetException e)
		{
			Throwable cause = e.getCause();
			if (cause instanceof RuntimeException)
				throw (RuntimeException) cause;
			throw e;
		}
	}

	public ObservableSteamsideData(SteamsideData data)
	{
		steamside = data;
	}

	public void addObserver(ChangeObserver listener)
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
		return new ChangeAwareKidsData();
	}

	@Override
	public TagsData tags()
	{
		return new ChangeAwareTagsData();
	}

	<T> T newChangeAwareProxy(Class<T> intf, Supplier< ? extends T> target)
	{
		return newProxyInstance(intf, new ChangeAware<T>(target));
	}

	<T> T newImmediateProxy(Class<T> intf, Supplier< ? extends T> target)
	{
		return newProxyInstance(intf, new Immediate<T>(target));
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

	@SuppressWarnings("unchecked")
	private <T> T newProxyInstance(Class<T> intf, final InvocationHandler h)
	{
		return (T) Proxy.newProxyInstance(
				this.getClass().getClassLoader(),
				new Class[] { intf },
				h);
	}

	public interface ChangeObserver {

		void onChange();

	}

	class ChangeAware<T> implements InvocationHandler {

		ChangeAware(final Supplier< ? extends T> target)
		{
			this.target = target;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable
		{
			Object ret = invokeMethod(target.get(), method, args);
			changed();
			return ret;
		}

		private final Supplier< ? extends T> target;

	}

	class ChangeAwareCollectionsData implements CollectionsData {

		ChangeAwareCollectionsData()
		{
			Supplier<CollectionsData> target = () -> realCollections();
			reads = newImmediateProxy(CollectionsQueries.class, target);
			writes = newChangeAwareProxy(CollectionsWrites.class, target);
		}

		@Override
		public void add(@NonNull CollectionI in) throws Duplicate
		{
			writes.add(in);
		}

		@Override
		public Stream< ? extends CollectionI> all()
		{
			return reads.all();
		}

		@Override
		public void delete(CollectionI in)
		{
			writes.delete(in);
		}

		@Override
		@NonNull
		public CollectionI favorite() throws FavoriteNotSet
		{
			return reads.favorite();
		}

		@Override
		@NonNull
		public CollectionI find(CollectionName name) throws NotFound
		{
			return reads.find(name);
		}

		private final CollectionsQueries reads;
		private final CollectionsWrites writes;
	}

	class ChangeAwareKidsData implements KidsData
	{

		ChangeAwareKidsData()
		{
			Supplier<KidsData> target = () -> realKids();
			reads = newImmediateProxy(Kids.class, target);
			writes = newChangeAwareProxy(KidsWrites.class, target);
		}

		@Override
		public void add(Kid kid) throws br.com.arbo.steamside.kids.Duplicate
		{
			writes.add(kid);
		}

		@Override
		public Stream<Kid> all()
		{
			return reads.all();
		}

		@Override
		public Kid find(User user) throws br.com.arbo.steamside.kids.NotFound
		{
			return reads.find(user);
		}

		private final Kids reads;

		private final KidsWrites writes;

	}

	class ChangeAwareTagsData implements TagsData {

		ChangeAwareTagsData()
		{
			Supplier<TagsData> target = () -> realTags();
			reads = newImmediateProxy(TagsQueries.class, target);
			writes = newChangeAwareProxy(TagsWrites.class, target);
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
			return autoSaveCollections;
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
		public void tagRemember(@NonNull CollectionI c, @NonNull AppId appid)
		{
			writes.tagRemember(c, appid);
		}

		@Override
		public Stream< ? extends CollectionI> tags(AppId app)
		{
			return reads.tags(app);
		}

		@Override
		public void untag(@NonNull CollectionI c, @NonNull AppId appid)
		{
			writes.untag(c, appid);
		}

		private final TagsQueries reads;

		private final TagsWrites writes;
	}

	static final class Immediate<T> implements InvocationHandler {

		Immediate(final Supplier< ? extends T> target)
		{
			this.target = target;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable
		{
			return invokeMethod(target.get(), method, args);
		}

		private final Supplier< ? extends T> target;

	}

	final CollectionsData autoSaveCollections = new ChangeAwareCollectionsData();

	private final ArrayList<ChangeObserver> listeners = new ArrayList<>(1);

	private final SteamsideData steamside;
}
