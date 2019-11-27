package br.com.arbo.steamside.api.app;

import br.com.arbo.steamside.types.CollectionName;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class AppCardTagDTO
{

	public AppCardTagDTO(final CollectionName name)
	{
		this.name = name.value;
	}

	@JsonProperty
	public final String name;

}
