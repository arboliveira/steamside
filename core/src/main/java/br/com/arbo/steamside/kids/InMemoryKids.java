package br.com.arbo.steamside.kids;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.opersys.username.User;

public class InMemoryKids implements KidsData
{

	@Override
	public void add(Kid kid) throws Duplicate
	{
		String username = kid.getUser().username();
		if (iUser.containsKey(username)) throw new Duplicate();

		String name = kid.getName().name;
		if (iName.containsKey(name)) throw new Duplicate();

		KidImpl in = KidImpl.clone(kid);
		iUser.put(username, in);
		iName.put(name, in);
	}

	@Override
	public Stream<Kid> all()
	{
		return iUser.values().stream();
	}

	@Override
	public Kid find(User user) throws NotFound
	{
		String username = user.username();
		Kid kid = iUser.get(username);
		return Optional.ofNullable(kid)
			.orElseThrow(() -> NotFound.user(username));
	}

	HashMap<String, Kid> iName = new HashMap<>();
	HashMap<String, Kid> iUser = new HashMap<>();
}
