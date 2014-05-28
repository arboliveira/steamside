package br.com.arbo.steamside.data.autowire;

import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.AppCriteria;
import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class AutowireCollectionsData implements CollectionsData {

	@Inject
	public AutowireCollectionsData(AutowireSteamsideData steamside)
	{
		this.steamside = steamside;
	}

	@Override
	public void add(@NonNull CollectionI in) throws Duplicate
	{
		steamside.collections().add(in);
	}

	@Override
	public Stream< ? extends CollectionI> all()
	{
		return steamside.collections().all();
	}

	@Override
	public Stream< ? extends WithCount> allWithCount(AppCriteria criteria)
	{
		return steamside.collections().allWithCount(criteria);
	}

	@Override
	public Stream< ? extends Tag> apps(CollectionI collection)
	{
		return steamside.collections().apps(collection);
	}

	@Override
	@NonNull
	public CollectionI find(CollectionName name) throws NotFound
	{
		return steamside.collections().find(name);
	}

	@Override
	public boolean isCollected(AppId appid)
	{
		return steamside.collections().isCollected(appid);
	}

	@Override
	public void tag(@NonNull CollectionI c, @NonNull AppId appid)
			throws NotFound
	{
		steamside.collections().tag(c, appid);
	}

	@Override
	public void tag(CollectionI c, Stream<AppId> apps) throws NotFound
	{
		steamside.collections().tag(c, apps);
	}

	@Override
	public Stream< ? extends CollectionI> tags(AppId app)
	{
		return steamside.collections().tags(app);
	}

	private final AutowireSteamsideData steamside;

}
