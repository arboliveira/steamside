package br.com.arbo.steamside.api.app;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
@JsonInclude(NON_NULL)
public class AppDTO
{

	public AppDTO()
	{
		super();
	}

	public AppDTO(AppApi app, List<AppTagDTO> tags)
	{
		this.appid = app.appid().appid;
		this.name = app.name();
		this.link = app.run_api_link();
		this.image = app.image();
		this.store = app.store();
		this.tags = tags;
		if (app.unavailable())
			this.unavailable = "Y";
	}

	@JsonProperty
	public String appid;
	@JsonProperty
	public String name;
	@JsonProperty
	public String link;
	@JsonProperty
	public String image;
	@JsonProperty
	public String store;
	@JsonProperty
	public List<AppTagDTO> tags;
	@JsonProperty
	public String unavailable;
}
