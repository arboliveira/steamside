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
	public void add(Kid kid) throws br.com.arbo.steamside.kids.Duplicate
	{
		writes.add(kid);
	}

	@Override
	public Stream<Kid> all()
	{
		return reads.all();
	}

	@Override
	public Kid find(User user) throws br.com.arbo.steamside.kids.NotFound
	{
		return reads.find(user);
	}

	private final Kids reads;

	private final KidsWrites writes;

}