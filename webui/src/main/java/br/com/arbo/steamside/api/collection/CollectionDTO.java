package br.com.arbo.steamside.api.collection;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.arbo.steamside.collections.TagsQueries.WithCount;

@JsonAutoDetect
@JsonInclude(NON_NULL)
public class CollectionDTO
{

	public static CollectionDTO valueOf(WithCount w)
	{
		return new CollectionDTO(w.collection().value, w.count());
	}

	CollectionDTO(String name, int count)
	{
		this.name = name;
		this.count = count;
	}

	@JsonProperty
	public final String name;

	@JsonProperty
	public final int count;

}
