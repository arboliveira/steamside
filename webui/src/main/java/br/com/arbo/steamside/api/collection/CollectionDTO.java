package br.com.arbo.steamside.api.collection;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.TagsQueries.WithCount;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
@JsonInclude(NON_NULL)
public class CollectionDTO
{

	static CollectionDTO valueOf(WithCount w)
	{
		CollectionI c = w.collection();
		return new CollectionDTO(c.name().value, c.isSystem(), w.count());
	}

	CollectionDTO(String name, CollectionI.IsSystem system, int count)
	{
		this.name = name;
		this.count = String.valueOf(count);

		system.toDTOString().ifPresent(v -> this.system = v);
	}

	@JsonProperty
	public final String name;

	@JsonProperty
	public String system;

	@JsonProperty
	public final String count;

}
