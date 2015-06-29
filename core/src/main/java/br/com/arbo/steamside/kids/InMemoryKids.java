package br.com.arbo.steamside.kids;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import br.com.arbo.opersys.username.User;

public class InMemoryKids implements KidsData
{

	private static Kid find(Supplier<Kid> get, Supplier<NotFound> not)
	{
		return Optional.ofNullable(get.get()).orElseThrow(not);
	}

	@Override
	public void add(KidCheck kid) throws DuplicateName, DuplicateUser
	{
		KidImpl in = KidImpl.clone(requireUnique(kid.check()));
		put(in);
	}

	@Override
	public Stream<Kid> all()
	{
		return iUser.values().stream().map(KidImpl::clone);
	}

	@Override
	public void delete(Kid kid) throws NotFound
	{
		requireExists(kid);
		remove(kid);
	}

	@Override
	public Kid find(KidName kidName) throws NotFound
	{
		String name = kidName.name;
		return find(() -> iName.get(name), () -> NotFound.name(name));
	}

	@Override
	public Kid find(User user) throws NotFound
	{
		String username = user.username();
		return find(() -> iUser.get(username), () -> NotFound.user(username));
	}

	@Override
	public void update(Kid target, KidCheck with) throws NotFound
	{
		requireExists(target);
		KidImpl re = KidImpl.clone(requireUnique(with.check(), target));
		remove(target);
		put(re);
	}

	private void put(KidImpl kid)
	{
		iUser.put(kid.getUser().username(), kid);
		iName.put(kid.getName().name, kid);
	}

	private void remove(Kid kid)
	{
		iUser.remove(kid.getUser().username());
		iName.remove(kid.getName().name);
	}

	private void requireExists(Kid kid) throws NotFound
	{
		find(kid.getName());
		find(kid.getUser());
	}

	private Kid requireUnique(Kid kid) throws DuplicateName, DuplicateUser
	{
		String username = kid.getUser().username();
		if (iUser.containsKey(username)) throw new DuplicateUser();

		String name = kid.getName().name;
		if (iName.containsKey(name)) throw new DuplicateName();

		return kid;
	}

	private Kid requireUnique(Kid kid, Kid unless)
		throws DuplicateName, DuplicateUser
	{
		requireUniqueUser(kid, unless);
		requireUniqueName(kid, unless);
		return kid;
	}

	private void requireUniqueName(Kid kid, Kid unless) throws DuplicateName
	{
		KidImpl u = iName.get(kid.getName().name);
		if (Objects.isNull(u)) return;
		if (u == iName.get(unless.getName().name)) return;
		throw new DuplicateName();
	}

	private void requireUniqueUser(Kid kid, Kid unless) throws DuplicateUser
	{
		KidImpl u = iUser.get(kid.getUser().username());
		if (Objects.isNull(u)) return;
		if (u == iUser.get(unless.getUser().username())) return;
		throw new DuplicateUser();
	}

	HashMap<String, KidImpl> iName = new HashMap<>();
	HashMap<String, KidImpl> iUser = new HashMap<>();
}
