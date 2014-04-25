package br.com.arbo.steamside.api.collection;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import br.com.arbo.steamside.collections.CollectionI;

@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class CollectionDTO {

	static CollectionDTO valueOf(CollectionI c)
	{
		return new CollectionDTO(c.name().value, c.isSystem());
	}

	CollectionDTO(String name, CollectionI.IsSystem system) {
		this.name = name;
		this.system = system.toDTOString();
	}

	@JsonProperty
	final String name;

	@JsonProperty
	final String system;

}
