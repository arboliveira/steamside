package br.com.arbo.steamside.kids;

import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.types.CollectionName;

public interface Kid {

	CollectionName getCollection();

	User getUser();
}
