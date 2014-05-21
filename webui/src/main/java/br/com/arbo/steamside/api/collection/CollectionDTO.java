package br.com.arbo.steamside.api.collection;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsQueries.WithCount;

@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class CollectionDTO {

	static CollectionDTO valueOf(WithCount w)
	{
		CollectionI c = w.collection();
		return new CollectionDTO(c.name().value, c.isSystem(), w.count());
	}

	CollectionDTO(String name, CollectionI.IsSystem system, int count)
	{
		this.name = name;
		this.system = system.toDTOString();
		this.count = String.valueOf(count);
	}

	@JsonProperty
	final String name;

	@JsonProperty
	final String system;

	@JsonProperty
	final String count;

}
