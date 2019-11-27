package br.com.arbo.steamside.api.app;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
@JsonInclude(NON_NULL)
public class AppCardDTO
{

	@JsonProperty
	public String appid;
	@JsonProperty
	public String image;
	@JsonProperty
	public String link;
	@JsonProperty
	public String name;
	@JsonProperty
	public String store;
	@JsonProperty
	public List<AppCardTagDTO> tags;
	@JsonProperty
	public String unavailable;
}
