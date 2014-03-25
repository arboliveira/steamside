package br.com.arbo.steamside.data.autowire;

import java.util.stream.Stream;

import javax.inject.Inject;

import br.com.arbo.steamside.kids.Duplicate;
import br.com.arbo.steamside.kids.Kid;
import br.com.arbo.steamside.kids.KidsData;
import br.com.arbo.steamside.kids.NotFound;
import br.com.arbo.steamside.opersys.username.User;

public class AutowireKidsData implements KidsData {

	@Inject
	public AutowireKidsData(AutowireSteamsideData steamside) {
		this.steamside = steamside;
	}

	@Override
	public void add(Kid kid) throws Duplicate
	{
		steamside.kids().add(kid);
	}

	@Override
	public Stream<Kid> all()
	{
		return steamside.kids().all();
	}

	@Override
	public Kid find(User user) throws NotFound
	{
		return steamside.kids().find(user);
	}

	private final AutowireSteamsideData steamside;

}
