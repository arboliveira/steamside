package br.com.arbo.steamside.api.collection;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import br.com.arbo.steamside.collections.CollectionI;

@JsonAutoDetect
public class CollectionDTO {

	static CollectionDTO valueOf(CollectionI c)
	{
		return new CollectionDTO(c.name().value);
	}

	CollectionDTO(String name) {
		this.name = name;
	}

	@JsonProperty
	final String name;

}
