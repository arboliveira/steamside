package br.com.arbo.steamside.collections;

import java.util.stream.Stream;

import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public interface TagsWrites
{

	void tag(
		CollectionI c,
		Stream<AppId> apps) throws NotFound;

	void tagn(
		Stream<WithApps> withApps) throws NotFound;

	void tagRemember(
		CollectionI c,
		AppId appid) throws NotFound;

	void untag(
		CollectionI c,
		AppId appid) throws NotFound;

	public interface WithApps
	{
		Stream<AppId> apps();

		CollectionName collection();
	}
}
