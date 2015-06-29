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
	public void add(KidCheck kid) throws DuplicateName, DuplicateUser
	{
		actual().add(kid);
	}

	@Override
	public Stream<Kid> all()
	{
		return actual().all();
	}

	@Override
	public void delete(Kid kid) throws NotFound
	{
		actual().delete(kid);
	}

	@Override
	public Kid find(KidName kidName) throws NotFound
	{
		return actual().find(kidName);
	}

	@Override
	public Kid find(User user) throws NotFound
	{
		return actual().find(user);
	}

	@Override
	public void update(Kid target, KidCheck with) throws NotFound
	{
		actual().update(target, with);
	}

	private KidsData actual()
	{
		return steamside.kids();
	}

	private final SteamsideData steamside;

}
