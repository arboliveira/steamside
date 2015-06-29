package br.com.arbo.steamside.kids;

import java.util.stream.Stream;

import br.com.arbo.opersys.username.User;

public interface Kids
{

	Stream<Kid> all();

	Kid find(KidName kidName) throws NotFound;

	Kid find(User user) throws NotFound;

}
