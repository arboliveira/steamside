package br.com.arbo.steamside.data.autowire;

import java.util.stream.Stream;

import javax.inject.Inject;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.kids.Duplicate;
import br.com.arbo.steamside.kids.Kid;
import br.com.arbo.steamside.kids.KidsData;
import br.com.arbo.steamside.kids.NotFound;

public class AutowireKidsData implements KidsData {

	@Inject
	public AutowireKidsData(AutowireSteamsideData steamside)
	{
		this.steamside = steamside;
	}

	@Override
	public void add(Kid kid) throws Duplicate
	{
		reloadable().add(kid);
	}

	@Override
	public Stream<Kid> all()
	{
		return reloadable().all();
	}

	@Override
	public Kid find(User user) throws NotFound
	{
		return reloadable().find(user);
	}

	private KidsData reloadable()
	{
		return steamside.kids();
	}

	private final AutowireSteamsideData steamside;

}
