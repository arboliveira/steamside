package br.com.arbo.steamside.kids;

public interface KidsWrites
{

	void add(KidCheck kid) throws DuplicateName, DuplicateUser;

	void delete(Kid kid) throws NotFound;

	void update(Kid target, KidCheck with)
		throws NotFound, DuplicateName, DuplicateUser;

}
