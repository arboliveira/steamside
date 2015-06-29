package br.com.arbo.steamside.kids;

import java.util.stream.Stream;

import br.com.arbo.opersys.username.User;

public class KidsDataGraft implements KidsData
{

	public KidsDataGraft(Kids reads, KidsWrites writes)
	{
		this.reads = reads;
		this.writes = writes;
	}

	@Override
	public void add(KidCheck kid) throws DuplicateName, DuplicateUser
	{
		writes.add(kid);
	}

	@Override
	public Stream<Kid> all()
	{
		return reads.all();
	}

	@Override
	public void delete(Kid kid) throws NotFound
	{
		writes.delete(kid);
	}

	@Override
	public Kid find(KidName kidName) throws NotFound
	{
		return reads.find(kidName);
	}

	@Override
	public Kid find(User user) throws br.com.arbo.steamside.kids.NotFound
	{
		return reads.find(user);
	}

	@Override
	public void update(Kid target, KidCheck with) throws NotFound
	{
		writes.update(target, with);
	}

	private final Kids reads;

	private final KidsWrites writes;

}