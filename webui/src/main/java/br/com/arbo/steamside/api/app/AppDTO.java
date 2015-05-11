package br.com.arbo.steamside.api.app;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class AppDTO {

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
