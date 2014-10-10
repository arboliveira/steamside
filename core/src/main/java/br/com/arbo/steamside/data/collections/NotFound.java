package br.com.arbo.steamside.data.collections;

import br.com.arbo.steamside.types.CollectionName;

public class NotFound extends RuntimeException {

	public NotFound(CollectionName name)
	{
		super(name.toString());
	}

}
