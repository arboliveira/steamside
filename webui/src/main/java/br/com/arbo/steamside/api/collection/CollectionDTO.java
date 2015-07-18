package br.com.arbo.steamside.api.collection;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
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
		return new CollectionDTO(w.collection().value, w.count());
	}

	CollectionDTO(String name, int count)
	{
		this.name = name;
		this.count = String.valueOf(count);
	}

	@JsonProperty
	public final String name;

	@JsonProperty
	public final String count;

}
