package br.com.arbo.steamside.kids;

import java.util.HashMap;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.opersys.username.User;

public class InMemoryKids implements KidsData {

	@Override
	public void add(Kid kid) throws Duplicate
	{
		String username = kid.getUser().username();
		if (iUser.containsKey(username)) throw new Duplicate();
		iUser.put(username, KidImpl.clone(kid));
	}

	@Override
	public Stream<Kid> all()
	{
		return iUser.values().stream();
	}

	@Override
	@NonNull
	public Kid find(User user) throws NotFound
	{
		String username = user.username();
		Kid kid = iUser.get(username);
		if (kid == null) throw NotFound.user(username);
		return kid;
	}

	HashMap<String, Kid> iUser = new HashMap<>();
}
