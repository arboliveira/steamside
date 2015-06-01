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
	public final String appid;
	@JsonProperty
	public final String name;
	@JsonProperty
	public final String link;
	@JsonProperty
	public final String image;
	@JsonProperty
	public final String store;
	@JsonProperty
	public final List<AppTagDTO> tags;
	@JsonProperty
	public String unavailable;
}
