package br.com.arbo.steamside.kids;

import java.util.stream.Stream;

import javax.inject.Inject;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.data.SteamsideData;

public class KidsDataSingleton implements KidsData
{

	@Inject
	public KidsDataSingleton(SteamsideData steamside)
	{
		this.steamside = steamside;
	}

	@Override
	public void add(Kid kid) throws Duplicate
	{
		actual().add(kid);
	}

	@Override
	public Stream<Kid> all()
	{
		return actual().all();
	}

	@Override
	public Kid find(User user) throws NotFound
	{
		return actual().find(user);
	}

	private KidsData actual()
	{
		return steamside.kids();
	}

	private final SteamsideData steamside;

}
