package br.com.arbo.steamside.search;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonAutoDetect
public class ResultDTO {

	@JsonProperty
	String appid;
	@JsonProperty
	String name;
}
