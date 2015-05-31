package br.com.arbo.steamside.data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

import org.springframework.util.ReflectionUtils;

import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.CollectionsDataGraft;
import br.com.arbo.steamside.collections.CollectionsWrites;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.collections.TagsDataGraft;
import br.com.arbo.steamside.collections.TagsWrites;
import br.com.arbo.steamside.kids.KidsData;
import br.com.arbo.steamside.kids.KidsDataGraft;
import br.com.arbo.steamside.kids.KidsWrites;

public class ObservableSteamsideData implements SteamsideData
{
	public ObservableSteamsideData(SteamsideData steamside)
	{
		collections = splice(steamside.collections());
		kids = splice(steamside.kids());
		tags = splice(steamside.tags(), collections);
	}

	public void addObserver(ChangeObserver listener)
	{
		observers.add(listener);
	}

	@Override
	public CollectionsData collections()
	{
		return collections;
	}

	@Override
	public KidsData kids()
	{
		return kids;
	}

	@Override
	public TagsData tags()
	{
		return tags;
	}

	<T> T changed(T value)
	{
		observers.forEach(ChangeObserver::onChange);
		return value;
	}

	@SuppressWarnings("unchecked")
	private <T> T newProxyInstance(Class<T> intf, final InvocationHandler h)
	{
		return (T) Proxy.newProxyInstance(
			this.getClass().getClassLoader(),
			new Class[] { intf },
			h);
	}

	private CollectionsData splice(CollectionsData target)
	{
		return new CollectionsDataGraft(
			target, spy(CollectionsWrites.class, target));
	}

	private KidsData splice(KidsData target)
	{
		return new KidsDataGraft(
			target, spy(KidsWrites.class, target));
	}

	private TagsData splice(TagsData target, CollectionsData collections)
	{
		return new TagsDataGraft(
			target, spy(TagsWrites.class, target), collections);
	}

	private <T> T spy(Class<T> intf, T target)
	{
		class ChangeAware implements InvocationHandler
		{
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable
			{
				return changed(ReflectionUtils.invokeMethod(
					method, target, args));
			}
		}

		return newProxyInstance(intf, new ChangeAware());
	}

	public interface ChangeObserver
	{
		void onChange();
	}

	private final CollectionsData collections;
	private final KidsData kids;
	private final ArrayList<ChangeObserver> observers = new ArrayList<>(1);
	private final TagsData tags;
}
