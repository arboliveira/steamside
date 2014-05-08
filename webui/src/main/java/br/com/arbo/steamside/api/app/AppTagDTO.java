package br.com.arbo.steamside.api.app;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import br.com.arbo.steamside.types.CollectionName;

@JsonAutoDetect
public class AppTagDTO {

	public AppTagDTO(final CollectionName name)
	{
		this.name = name.value;
	}

	@JsonProperty
	final String name;

}
